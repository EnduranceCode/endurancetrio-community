#!/usr/bin/env bash
#
# Convert a PostgreSQL Flyway DML script into its H2 counterpart.
#
# Usage:
#   port-postgres-dml-to-h2.sh <path/to/V...__description-postgres.sql>
#
# The H2 file is written next to the source, in the sibling "h2" directory, with the same version
# and free description. Only the dialect-specific statements are rewritten:
#   SET search_path TO endurancetrio_hub;      ->  SET SCHEMA endurancetrio_hub;
#   SELECT setval('seq_name', 42, FALSE);      ->  ALTER SEQUENCE endurancetrio_hub.seq_name RESTART WITH 42;
#
set -euo pipefail

if [[ $# -ne 1 ]]; then
  echo "Usage: $0 <path/to/V...__description-postgres.sql>" >&2
  exit 2
fi

src=$1

if [[ ! -f $src ]]; then
  echo "File not found: $src" >&2
  exit 1
fi

if [[ $src != *-postgres.sql ]]; then
  echo "Expected a *-postgres.sql input, got: $src" >&2
  exit 1
fi

if [[ $src != */dml/postgres/* ]]; then
  echo "Expected a file under db/migration/dml/postgres, got: $src" >&2
  exit 1
fi

dst=${src/-postgres.sql/-h2.sql}
dst=${dst/\/dml\/postgres\//\/dml\/h2\/}

if [[ -e $dst ]]; then
  echo "Refusing to overwrite existing file: $dst" >&2
  echo "Delete it first if the port really has to be regenerated." >&2
  exit 1
fi

python3 - "$src" "$dst" <<'PY'
import pathlib
import re
import sys

src = pathlib.Path(sys.argv[1])
dst = pathlib.Path(sys.argv[2])

sql = src.read_text(encoding="utf-8")
sql = sql.replace("SET search_path TO endurancetrio_hub;", "SET SCHEMA endurancetrio_hub;")
if "SET SCHEMA endurancetrio_hub;" not in sql:
    sys.exit("No schema statement found in the source script; inspect it manually.")

sql, converted = re.subn(
    r"SELECT setval\('([a-z_]+)', (\d+), FALSE\);",
    r"ALTER SEQUENCE endurancetrio_hub.\1 RESTART WITH \2;",
    sql,
)

residual = re.search(r"\bsetval\b|search_path|::|ON CONFLICT|ILIKE", sql)
if residual:
    line = sql[: residual.start()].count("\n") + 1
    sys.exit(
        f"Residual PostgreSQL-only syntax near line {line}: {residual.group(0)!r}. "
        "Convert it manually before using the generated file."
    )

dst.write_text(sql, encoding="utf-8")
print(f"Wrote {dst}")
print(f"Converted {converted} sequence statement(s) to ALTER SEQUENCE.")
PY

echo
echo "Diff (H2 < > PostgreSQL):"
diff -u "$dst" "$src" || true

checker=$(dirname "$(readlink -f "$0")")/check-h2-parity.py
if [[ -x $checker ]]; then
  echo
  "$checker" "$src"
fi
