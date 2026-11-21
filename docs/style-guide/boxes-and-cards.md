# Boxes and Cards

> Part of the [EnduranceTrio Style Guide](./index.md).

`.endurance-box` is EnduranceTrio's version of Bulma's
[box](https://bulma.io/documentation/elements/box/). It extends `.box`, so every Bulma modifier and
child element still works, and adds the branded colour stripe plus height presets for hero-like
blocks. It also gains light and outlined variants and square sizes, and those work on a plain `.box`
too.

Cards use plain Bulma [cards](https://bulma.io/documentation/components/card/) with the global
`.is-stretched` helper: the project adds no custom card element or default card style, so there is
no `.endurance-card` block.

Source: `src/scss/elements/endurance-box.scss` and `src/scss/helpers/endurance-stretched.scss`.

## Endurance Box

`.endurance-box` extends `.box`. Every endurance box has a tri-colour stripe along its bottom
edge: blue, yellow, and red in equal thirds. The stripe is drawn as a background gradient, so it
does not affect the box's content or padding. The default is already branded, so there is no
`is-branded` modifier; use a stripe variant to change the height.

```html
<div class="endurance-box">
  <h3 class="title is-5">Default box</h3>
  <p>Blue, yellow, and red stripe along the bottom edge.</p>
</div>
```

### Stripe variants

| Class                     | Stripe                                                                        | Height |
|---------------------------|-------------------------------------------------------------------------------|--------|
| (default)                 | Blue / yellow / red thirds                                                    | 3px    |
| `.is-branded-thin`        | Blue / yellow / red thirds                                                    | 1px    |
| `.is-branded-thick`       | Blue / yellow / red thirds                                                    | 6px    |
| `.is-branded-extra-thick` | Blue / yellow / red thirds                                                    | 9px    |
| `.is-branded-faded`       | Continuous gradient: blue at the left, yellow at the centre, red at the right | 3px    |

```html
<div class="endurance-box is-branded-thin">…</div>
<div class="endurance-box is-branded-faded">…</div>
```

Stripe heights are set by the Sass variables `$endurance-box-stripe-height`,
`$endurance-box-stripe-height-thin`, `$endurance-box-stripe-height-thick`, and
`$endurance-box-stripe-height-extra-thick`.

### Height presets

Height modifiers set a minimum height and centre the content vertically, which suits call-to-action
and hero-style blocks:

| Class                        | Minimum height                             |
|------------------------------|--------------------------------------------|
| `.is-small`                  | `6rem`                                     |
| `.is-medium`                 | `12rem`                                    |
| `.is-large`                  | `18rem`                                    |
| `.is-halfheight`             | `50vh`                                     |
| `.is-halfheight-with-navbar` | `calc(50vh - var(--bulma-navbar-height))`  |
| `.is-fullheight`             | `100vh`                                    |
| `.is-fullheight-with-navbar` | `calc(100vh - var(--bulma-navbar-height))` |

```html
<div class="endurance-box is-medium is-branded-thin has-text-centered">
  <p class="title is-4">Centred call to action</p>
</div>
```

Use `.is-content-top` to keep the content at the top instead of centring it. It is also a flex
column, so it accepts the same height presets.

```html
<div class="endurance-box is-branded-thin is-content-top">…</div>
```

### Light and outlined variants

These modifiers are defined on the Bulma `.box`, so they work on `.box` and, through the `@extend`,
on `.endurance-box` as well:

| Class                   | Fill                  | Border                                       | Shadow |
|-------------------------|-----------------------|----------------------------------------------|--------|
| `.is-light`             | `--bulma-light` (96%) | none                                         | kept   |
| `.is-outlined`          | transparent           | `max(1px, 0.0625em)` solid, outline colour   | none   |
| `.is-outlined.is-light` | `--bulma-light` (96%) | `max(1px, 0.0625em)` solid, outline colour   | none   |
| `.is-grey-outlined`     | transparent           | `max(1px, 0.0625em)` solid, grey text colour | none   |

`.is-light` uses the pale `light` palette colour with its dark invert for text, the same
pale-fill/dark-text idea as `.tag.is-light`. `.is-outlined` replaces the box shadow with a border
and makes the fill transparent. On an `a.box` the border thickens on hover and focus, like an
outlined button. The border colour comes from `--bulma-endurance-box-outline-color`, which
defaults to `--bulma-text`.

```html
<div class="box is-outlined is-64x64">3</div>
<div class="box is-light">Light box</div>
<div class="box is-outlined is-light">Filled outlined box</div>
```

### Square sizes

`.is-{N}x{N}` fixes a square box at N pixels and centres its content, using the same sizes as
Bulma's image element plus `256`: `16`, `24`, `32`, `48`, `64`, `96`, `128` and `256`. The list is
set by the `$endurance-box-dimensions` Sass variable.

```html
<div class="box is-outlined is-64x64 is-size-4 has-text-weight-bold">3</div>
```

A sized box has no padding, so keep its content short: a rank, a count, or an icon. The sizes are
a different axis from the height presets above. Do not combine `is-{N}x{N}` with `.is-small`,
`.is-medium`, `.is-large` or the viewport-height presets, because those set `min-height` and win.

## Stretched blocks

`.is-stretched` is a global helper for boxes and cards that must finish at the same height in a row
of columns. It sets `height: 100%` and a flex column layout on the block; Bulma's columns already
give each column the row's height.

```html
<div class="columns">
  <div class="column">
    <div class="endurance-box is-branded-thin is-stretched">…</div>
  </div>
  <div class="column">
    <div class="card is-stretched">…</div>
  </div>
</div>
```

The helper works on `.box`, `.endurance-box` and `.card`. It is not tied to any one of them.

Links live inside the stretched block in their normal containers (typically the media or header),
so only that part is clickable and the rest of the content remains selectable. Do not wrap the
block in an `<a>`: that wrapper breaks the equal-height layout, and nested links are invalid
HTML. The insights list card is an example, with a title link and a separate "read more" footer
link.

`is-clickable` is Bulma's pointer-cursor helper; add it to a link that should show a pointer.

## Cards

Use a plain Bulma `.card` for an image-and-text tile, such as an article or event teaser. Add
`.is-stretched` when cards in a column row must finish at the same height; `.card-content` then
grows to take the spare space.

```html
<div class="columns">
  <div class="column">
    <div class="card is-stretched">
      <a href="/insights/slug" class="is-clickable">
        <div class="card-image">
          <figure class="image">
            <img src="/img/endurancetrio-open-graph.png" alt="Article title">
          </figure>
        </div>
        <div class="card-content">
          <p class="title is-3">Article title</p>
          <p class="subtitle is-5">Author name</p>
        </div>
      </a>
    </div>
  </div>
</div>
```

All Bulma card parts (`.card-image`, `.card-content`, `.card-footer`) still work. There is no
`.endurance-card`: the only project rule for cards is the column height and link layout, which
`.is-stretched` provides on the Bulma block. See [Stretched blocks](#stretched-blocks) for the
linked-card pattern and the multi-link exception.

## Choosing between them

| Need                                                  | Use                 |
|-------------------------------------------------------|---------------------|
| A branded container for text, lists or a single block | `.endurance-box`    |
| An image on top with title and text, as a teaser      | `.card`             |
| A plain Bulma box without the brand stripe            | `.box`              |
| Equal heights in a row                                | add `.is-stretched` |

## Expand button

`.expand` and `.close` are shared toggle buttons used by the expand behaviour
(`js/endurance-expand.js`). They render a chevron-down (`.expand`) or chevron-up (`.close`) icon,
are round, and are floated to the top-right corner when placed directly inside a `.box`
or `.endurance-box`. They are static otherwise, e.g. inside a table cell.

```html
<div class="endurance-box">
  <button data-expand aria-expanded="false" class="expand is-medium"
          aria-label="Toggle details"></button>

  <div data-expand-content class="box is-grey-outlined is-light is-hidden">
    …
  </div>
</div>
```

Sizes: default `1.25rem`, `.is-medium` `1.875rem`, `.is-large` `2.5rem`.

The shared expand stylesheet (`src/scss/elements/endurance-expand.scss`) must be loaded for the
chevron icons to render; it is forwarded from `src/scss/style.scss`.
