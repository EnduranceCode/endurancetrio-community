#!/usr/bin/env python3
"""Check that every H2 Flyway DML script mirrors its PostgreSQL source.

The PostgreSQL script is the source of truth: after applying the documented dialect
equivalences, the two files must contain the same statements, in the same order. Any
remaining difference is an asymmetry and must be fixed in the H2 script, never in the
PostgreSQL one.

One nuance: a statement block that the PostgreSQL script repeats by mistake must NOT be
replicated in H2, because the repetition adds nothing and the PostgreSQL script can no
longer be corrected once it has run in production. Such cases are registered in
EXCEPTIONS below, and the checker only accepts them when the distinct statements still
match exactly (i.e. the sole difference is a repetition).

Usage:
    check-h2-parity.py [PATH ...]

With no PATH, every script under db/migration/dml/postgres is checked.
"""

from __future__ import annotations

import re
import sys
from collections import Counter
from pathlib import Path

REPO_ROOT = Path(__file__).resolve().parents[4]
DML_DIR = REPO_ROOT / "endurancetrio-data/src/main/resources/db/migration/dml"

SCHEMA = "endurancetrio_hub"

# PostgreSQL scripts that repeat a statement block by mistake and are already applied in
# production. The H2 twin keeps a single copy; the repetition is the only accepted
# difference for these files. Removing an entry here makes the checker report the file again.
EXCEPTIONS = {
    "V20260828.001__process-event-16-results-postgres.sql": (
        "Repeats the same 15 team_result_individual_result UPDATEs after the team_result "
        "inserts; the second run is a no-op. H2 keeps one copy."
    ),
}


def statements(path: Path) -> list[str]:
    text = path.read_text(encoding="utf-8")
    text = re.sub(r"--[^\n]*", " ", text)
    result = []
    for raw in text.split(";"):
        sql = " ".join(raw.split())
        if not sql:
            continue
        # Schema handling: PG uses search_path, H2 uses SET SCHEMA, legacy H2 files qualify
        # every object name with the schema instead.
        if re.fullmatch(r"SET search_path TO \w+", sql) or re.fullmatch(r"SET SCHEMA \w+", sql):
            continue
        sql = sql.replace(f"{SCHEMA}.", "")
        # Sequence alignment: SELECT setval(...) <=> ALTER SEQUENCE ... RESTART WITH ...
        match = re.fullmatch(r"SELECT setval\('(\w+)', (\d+), FALSE\)", sql)
        if match:
            sql = f"RESEQUENCE {match.group(1)} {match.group(2)}"
        match = re.fullmatch(rf"ALTER SEQUENCE (\w+) RESTART WITH (\d+)", sql)
        if match:
            sql = f"RESEQUENCE {match.group(1)} {match.group(2)}"
        # H2 has no ON CONFLICT; the clause carries no data for these idempotent seeds.
        sql = re.sub(r"\s+ON CONFLICT \([^)]*\) DO NOTHING$", "", sql)
        # H2 has no PostgreSQL DELETE ... USING. PostgreSQL removes the old athlete's relay
        # entry links that the kept athlete already has; the H2 twin expresses the same rows
        # with a relay_entry_id IN subquery. Both sides normalize to one token holding the
        # old and kept athlete IDs.
        sql = re.sub(
            r"DELETE FROM relay_entry_athlete old_link USING relay_entry_athlete kept_link "
            r"WHERE old_link\.athlete_id = (\d+) AND kept_link\.relay_entry_id = "
            r"old_link\.relay_entry_id AND kept_link\.athlete_id = (\d+)",
            r"DELETE_DUPLICATE_RELAY_ATHLETE \1 \2",
            sql,
        )
        sql = re.sub(
            r"DELETE FROM relay_entry_athlete WHERE athlete_id = (\d+) AND "
            r"relay_entry_id IN \(\s*SELECT relay_entry_id FROM relay_entry_athlete "
            r"WHERE athlete_id = (\d+)\s*\)",
            r"DELETE_DUPLICATE_RELAY_ATHLETE \1 \2",
            sql,
        )
        result.append(sql)
    return result


def report(path: Path) -> bool:
    h2 = path.parent.parent / "h2" / path.name.replace("-postgres.sql", "-h2.sql")
    pg_sql = statements(path)
    if not h2.exists():
        print(f"{'NO H2 TWIN':>14}  {path.name}")
        return True
    h2_sql = statements(h2)
    if pg_sql == h2_sql:
        print(f"{'OK':>14}  {path.name}")
        return True

    reason = EXCEPTIONS.get(path.name)
    if reason and set(pg_sql) == set(h2_sql):
        print(f"{'OK (DUPLICATES)':>14}  {path.name}")
        print(f"    {reason}")
        return True

    print(f"{'ASYMMETRY':>14}  {path.name}")
    # Count-based reporting: shows duplicated or missing statements as well as edits.
    pg_counts = Counter(pg_sql)
    h2_counts = Counter(h2_sql)
    for sql, count in pg_counts.items():
        delta = count - h2_counts.get(sql, 0)
        if delta > 0:
            print(f"    only PostgreSQL ({delta}x): {sql[:200]}")
    for sql, count in h2_counts.items():
        delta = count - pg_counts.get(sql, 0)
        if delta > 0:
            print(f"    only H2        ({delta}x): {sql[:200]}")
    if pg_counts == h2_counts:
        print("    same statements, different order")
    if reason:
        print("    registered duplication exception does not cover this difference")
    return False


def main(argv: list[str]) -> int:
    targets = [Path(a).resolve() for a in argv] or sorted((DML_DIR / "postgres").glob("*-postgres.sql"))
    files = []
    for target in targets:
        if target.is_dir():
            files.extend(sorted(target.glob("*-postgres.sql")))
        else:
            files.append(target)

    ok = True
    for path in files:
        ok = report(path) and ok
    print()
    print("H2 scripts match the PostgreSQL sources." if ok else "Asymmetries found: fix the H2 side.")
    return 0 if ok else 1


if __name__ == "__main__":
    sys.exit(main(sys.argv[1:]))
