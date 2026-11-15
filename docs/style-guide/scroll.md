# Scrollable Rows

> Part of the [EnduranceTrio Style Guide](./index.md).

Rows that are wider than the viewport — time splits, tag strips, summary figures — scroll
sideways instead of being clipped or stacked. Two opt-in classes plus one optional behaviour
attribute implement it.

Sources:

| Feature            | Source                                             |
|--------------------|----------------------------------------------------|
| Horizontal scroll  | `src/scss/helpers/endurance-scroll.scss`           |
| Level tweaks       | `src/scss/layout/endurance-level.scss`             |
| Affordance shadow  | `src/scss/components/endurance-scroll-region.scss` |
| Opening at the end | `src/js/endurance-scroll.js`                       |

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

## Affordance

On touch screens the overlay scrollbar is invisible until the gesture starts, and a hard clip on
plain text reads as a truncated value rather than as "there is more". Wrapping the row in
`.endurance-scroll-region` adds two cues, both driven by the row's scroll position:

```html
<div class="endurance-scroll-region" data-scroll-region>
  <div class="level is-mobile is-scrollable-x"
       role="group" tabindex="0"
       data-scroll-to-end data-scroll-cue
       data-th-attr="aria-label=#{…}, aria-describedby=${'splits-hint-' + result.race().id}">
    …
  </div>
  <p data-th-id="${'splits-hint-' + result.race().id}"
     data-th-text="#{page.athlete.profile.results.hint.splits}"
     class="is-sr-only"></p>
</div>
```

| Hook                 | Kind      | Applies to     | Effect                                                          |
|----------------------|-----------|----------------|-----------------------------------------------------------------|
| `data-scroll-region` | Behaviour | The wrapper    | Hosts the `is-overflow-start` / `is-overflow-end` state classes |
| `data-scroll-cue`    | Behaviour | The scrollport | Opts the row into state tracking (and the one-shot nudge)       |

- **Edge shadow.** `::before` / `::after` on the wrapper paint a hard 4 px line in
  `var(--bulma-background)` (0.25 em, no blur) on the side that still hides content — the same
  shadow Bulma draws under its top fixed navbar. The shadow is the primary cue on first reveal,
  because the row opens at its end edge, so the clipped side is the left one.
- The native scrollbar is hidden for these rows (`scrollbar-width: none` plus
  `::-webkit-scrollbar { display: none }`) so it does not compete with the shadow.
- The state classes are set by `js/endurance-scroll.js` from `scrollLeft`, `scrollWidth` and
  `clientWidth`, with a 1 px tolerance, and are re-evaluated on scroll and on resize. Both are
  removed when the row fits the viewport, so a non-scrolling row renders no shadow at all — on
  desktop and for short races there is no chrome.
- A visually hidden description (`is-sr-only`) doubles as the group's `aria-describedby`
  target, so its wording must read sensibly to keyboard and screen-reader users; it is the
  only use of `page.athlete.profile.results.hint.splits`.
- Once per element, and only when `prefers-reduced-motion` is not `reduce`, the row nudges a
  few pixels toward its hidden content on first reveal. The nudge is cancelled by any pointer,
  keyboard, wheel or touch input, and is skipped when the row fits or is not resting at its
  end edge.

## Behaviour notes

- **Snap points.** `scroll-snap-type: x proximity` with `scroll-snap-align: start` on the direct
  children makes the row settle on a clean boundary instead of stopping mid-value.
- **Contained overscroll.** `overscroll-behavior-x: contain` keeps a horizontal swipe inside the
  row: the page does not bounce and iOS Safari does not read the gesture as back-navigation.
- **Thin scrollbar.** `scrollbar-width: thin` applies to Firefox and Chromium. Mobile Safari and
  Chrome use overlay scrollbars that cannot be styled, so the cut-off content alone is not a
  reliable affordance: wrap the row in `.endurance-scroll-region` (see
  [Affordance](#affordance)), which hides the native bar and supplies an edge shadow instead.
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
