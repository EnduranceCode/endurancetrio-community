# Media

> Part of the [EnduranceTrio Style Guide](./index.md).

`.endurance-media` is the project's media block. It extends Bulma's
[media object](https://bulma.io/documentation/layout/media-object/), so it reuses Bulma's
`.media-left` and `.media-content` element names and all of Bulma's media rules.

It exists because the project adds a custom element, `.endurance-media-footer`. That element needs
a project block to belong to; a `.endurance-media-footer` inside a plain `.media` would break the
naming rules. The block also owns the `.is-stacked` modifier and the `--bulma-endurance-media-*`
variables. Plain Bulma `.media` is still valid for a simple row with no footer and no stacked
layout.

By default the row is horizontal at every viewport. Add `.is-stacked` when the row should become a
card: the left column turns into a full-width header on mobile, and the two columns sit on grid
tracks on tablet and up.

Source: `src/scss/layout/endurance-media.scss`. Its Sass variables have SassDoc comments.

## Structure

| Class                     | Role                                                               |
|---------------------------|--------------------------------------------------------------------|
| `.endurance-media`        | Project media block; extends Bulma's `.media`                      |
| `.media-left`             | Left column: contains a `.box` or `.image` (Bulma element)         |
| `.media-content`          | Content column: titles, text and other blocks (Bulma element)      |
| `.endurance-media-footer` | Optional row at the bottom of the content column (project element) |
| `.is-stacked`             | Modifier: grid layout, stacked on mobile                           |

```html
<div class="endurance-media">
  <div class="media-left">
    <div class="box is-64x64 has-background-grey-300"></div>
  </div>
  <div class="media-content">
    <p class="title is-5">Athlete name</p>
    <p class="subtitle is-6">Club, country</p>
  </div>
</div>
```

## Naming

The block is a project block, but the parts that are still Bulma's keep their Bulma names — the
same pattern as `.endurance-navbar` with `.navbar-item`. `@extend .media` makes Bulma's descendant
rules match `.media-left` and `.media-content`, so renaming them would only duplicate CSS. Only the
footer, which Bulma does not have, is a new project element and takes the `endurance-media-`
prefix. That element is the reason for the block prefix; the stacked modifier alone would have been
enough to keep Bulma's `.media` name.

Modifiers and helpers stay unprefixed: `is-stacked`, `is-64x64`, `is-og-320x167`,
`has-text-wrapped`.

## Responsive behaviour

- **Default:** Bulma's horizontal flex row at every viewport. Left and content are separated by
  Bulma's `--bulma-media-spacing` (1rem).
- **`.is-stacked`:** CSS Grid, one column on mobile and `auto 1fr` on tablet and up. The left
  column takes the full width on mobile; on tablet+ it is sized by its child and the content column
  fills the remaining space.
- **Stacked spacing:** `--bulma-endurance-media-gap` (1.5rem) is the column gap and the tablet+
  row gap. On mobile the row gap is `--bulma-endurance-media-left-margin` (0.75rem); combined with
  the left column's bottom margin of the same size, the effective vertical spacing is 1.5rem.
  Bulma's adjacent-row border is suppressed in stacked mode.
- **Footer:** pinned to the bottom of the content column when one is present. On mobile it gains a
  thin top border and centred text.
- **Long text:** opt in with `.has-text-wrapped` on `.media-content`; see
  [Wrapping long text](#wrapping-long-text).

```html
<div class="endurance-media is-stacked">
  <div class="media-left">
    <figure class="image is-og-320x167">
      <img src="/img/article.jpg" alt="Article thumbnail">
    </figure>
  </div>
  <div class="media-content">
    <p class="title is-3">Article title</p>
    <p class="endurance-media-footer">Published date</p>
  </div>
</div>
```

## Wrapping long text

`.media-content` is a flex item. Its default `min-width: auto` stops it shrinking below its longest
unbreakable word, so a long URL can widen the row or create a horizontal scrollbar. Add the global
`.has-text-wrapped` helper to the content column when it can contain such text:

```html
<div class="media">
  <div class="media-left">…</div>
  <div class="media-content has-text-wrapped">…</div>
</div>
```

It sets `min-width: 0` and `overflow-wrap: anywhere`, following Bulma's `has-text-*` helper
pattern. It is defined in `helpers/endurance-text-wrapped.scss` and works on any text container,
not just media. Leave it off rows that only hold short text so plain `.media` stays plain.

## Sizing the left column

`.media-left` is a layout slot, not the thing being sized. It has no size or ratio modifiers; put
those on the child element instead:

- **Text badge or avatar:** use `.box.is-{N}x{N}`. The sizes are defined in
  `elements/endurance-box.scss` (see [Boxes and Cards](./boxes-and-cards.md)).
- **Image:** use `.image.is-{N}x{N}` or `.image.is-{W}by{H}`. Bulma supplies the square pixel sizes
  and its standard aspect ratios.
- **Thumbnail with social-share proportions:** use `.image.is-og-*`, described below.

This keeps sizing on the element that owns it and avoids a third, drifting list of sizes.

```html
<div class="media-left">
  <div class="box is-outlined is-64x64 is-size-5 has-text-weight-bold">3</div>
</div>

<div class="media-left">
  <figure class="image is-64x64"><img src="/img/avatar.png" alt="Avatar"></figure>
</div>
```

### Open Graph image sizes

`.image.is-og-1200x628`, `.image.is-og-600x314`, `.image.is-og-480x251` and
`.image.is-og-320x167` give the standard social-share proportions (~1.91:1). They are defined in
`elements/endurance-image.scss`. On mobile the image fills the available width; on tablet and up it
takes its fixed pixel size. The image fills the frame with `object-fit: cover`.

```html
<div class="media-left">
  <figure class="image is-og-320x167">
    <img src="/img/article.jpg" alt="Article thumbnail">
  </figure>
</div>
```

## Text badges

A text badge is a `.box` inside `.media-left`. Combine it with Bulma's colour helpers or the grey
background helpers (see [Colors](./colors.md#grey-background-helpers)):

```html
<div class="media-left">
  <div class="box has-background-grey-600 has-text-white has-text-centered">
    <p class="title is-4">Label</p>
  </div>
</div>
```

In the stacked variant the column centres its child and rounds/clips the outer edge. Use the box
for the badge's own colour, padding and typography.

## Choosing the variant

| Need                                                                   | Use                           |
|------------------------------------------------------------------------|-------------------------------|
| A simple badge, avatar or date beside short text                       | `.media`                      |
| A row that needs the footer or the project media variables             | `.endurance-media`            |
| Aligned badges in a stacked list, a cropped thumbnail, or a footer row | `.endurance-media.is-stacked` |

Choose between the default row and `.is-stacked`, not between competing media blocks. Plain
`.media` is the Bulma original and stays available; use it when none of the project additions are
needed. Do not put sizing modifiers on `.media-left`: size its `.box` or `.image` child. Add
`.has-text-wrapped` to `.media-content` when the row can hold long text.
