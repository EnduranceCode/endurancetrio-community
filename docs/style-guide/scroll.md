# Scrollable Rows

> Part of the [EnduranceTrio Style Guide](./index.md).

Rows that are wider than the viewport — time splits, tag strips, summary figures — scroll
sideways instead of being clipped or stacked. Two opt-in classes plus one optional behaviour
attribute implement it.

Sources:

| Feature            | Source                                     |
|--------------------|--------------------------------------------|
| Horizontal scroll  | `src/scss/utilities/endurance-scroll.scss` |
| Level tweaks       | `src/scss/layout/endurance-level.scss`     |
| Opening at the end | `src/js/endurance-scroll.js`               |

## Usage

```html
<div class="level is-mobile is-scrollable-x"
     role="group"
     tabindex="0"
     data-th-attr="aria-label=#{page.athlete.profile.results.a11y.splits}"
     data-scroll-to-end>
  <div class="level-item has-text-centered">…</div>
  <div class="level-item has-text-centered">…</div>
  <div class="level-item has-text-centered">…</div>
</div>
```

| Hook                 | Kind      | Applies to     | Effect                                                                |
|----------------------|-----------|----------------|-----------------------------------------------------------------------|
| `is-scrollable-x`    | Helper    | Any block      | `overflow-x: auto`, scroll snap, contained overscroll, thin scrollbar |
| `data-scroll-to-end` | Behaviour | The same block | Opens the row scrolled to its end edge once it is visible             |

`is-scrollable-x` is deliberately axis-specific so an `is-scrollable-y` counterpart can be added
later without renaming it. The helper styles only; the markup must still make the scrollport
reachable (see [Accessibility](#accessibility)).

## Opening at the end

A row of splits ends with the value that matters most, so it is read right to left: the total
belongs on screen, the intermediate legs are reached by swiping back. `data-scroll-to-end` sets
that starting position.

It has to be scripted. The CSS property for it, `scroll-initial-target`, is not supported by
Safari or Firefox, and mobile Safari is the audience that needs it most, so the behaviour does it
instead:

- The scroll position is applied once, as soon as the row has a layout width, and never again.
  Rows usually sit inside a collapsed region (`data-expand-content`), where they have no width at
  all, so the behaviour waits for the first reveal instead of running on load. Re-opening the
  region later leaves the row where the reader left it.
- The jump is instant even though the row opts into smooth scrolling; only later scrolling is
  animated.
- If the row does not overflow — tablet, desktop, or a short race — it is left alone.
- **Without JavaScript the row opens at its start edge.** Nothing is hidden: the total is still
  reachable by scrolling, so this is a degraded starting position, not a broken page.

Do not use this behaviour to hide the beginning of a row. It changes where the reader starts
looking, not what is available.

## Behaviour notes

- **Snap points.** `scroll-snap-type: x proximity` with `scroll-snap-align: start` on the direct
  children makes the row settle on a clean boundary instead of stopping mid-value.
- **Contained overscroll.** `overscroll-behavior-x: contain` keeps a horizontal swipe inside the
  row: the page does not bounce and iOS Safari does not read the gesture as back-navigation.
- **Thin scrollbar.** `scrollbar-width: thin` applies to Firefox and Chromium. Mobile Safari and
  Chrome use overlay scrollbars that cannot be styled, so rely on the cut-off content as the
  affordance for swiping.
- **Reduced motion.** Smooth scrolling in `.level.is-scrollable-x` is wrapped in
  `prefers-reduced-motion: no-preference`, and the initial jump ignores smooth scrolling
  regardless.
- **Tables.** Do not use these classes for tabular data; wrap the table in `.table-container`
  instead (see [Tables](./tables.md)).

## Accessibility

A scroll container that is not focusable cannot be scrolled without a pointing device, and an
unnamed one gives a screen reader nothing to announce. When the content is expected to be read or
scrolled, always add:

- `tabindex="0"` so the row is a keyboard focus stop (arrow keys then scroll it);
- `role="group"` plus a translated `aria-label` so assistive technology announces what the group
  is. Store that text under the `a11y` namespace of the page
  (`page.athlete.profile.results.a11y.splits`), never as an English literal.

`role="region"` is a stronger alternative, but it creates a landmark per row, which is noise on a
page that repeats the component. Use it only when a row is a landmark in its own right.

Because the row can start at its end edge, do not make the last item the only place where
information is announced, and do not rely on visual order alone to convey sequence: the reading
order stays source order.
