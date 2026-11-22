# Behaviours

> Part of the [EnduranceTrio Style Guide](./index.md).

Behaviours are the JavaScript modules that make the static markup interactive. Each module is a
plain object with an `init()` method. `src/js/script.js` imports the modules and calls `init()`
once the DOM has loaded, so a page only needs the markup, not any script of its own.

This page covers the modules that are not documented in a component page. For tabs, tables, and
the navbar, see [Tabs](./tabs.md), [Tables](./tables.md), and [Navbar](./navbar.md).

| Module                       | Initialised by `script.js` | Documented here                                                                       |
|------------------------------|----------------------------|---------------------------------------------------------------------------------------|
| `bulma-loading.js`           | Yes                        | [Loading buttons](#loading-buttons)                                                   |
| `bulma-modal.js`             | Yes                        | [Modal](#modal)                                                                       |
| `bulma-navbar.js`            | Yes                        | [Navbar burger](#navbar-burger)                                                       |
| `bulma-tab.js`               | Yes                        | [Tabs](./tabs.md)                                                                     |
| `bulma-table-sort.js`        | Yes                        | [Tables](./tables.md#sortable-columns)                                                |
| `endurance-event-filters.js` | Yes                        | [Event filter dropdowns](#event-filter-dropdowns)                                     |
| `endurance-expand.js`        | Yes                        | [Boxes](./boxes-and-cards.md#expand-button) and [Tables](./tables.md#expandable-rows) |
| `endurance-scroll.js`        | Yes                        | [Scrollable Rows](./scroll.md#opening-at-the-end)                                     |
| `language-selector.js`       | Yes                        | [Language selector](#language-selector)                                               |
| `bulma-panel.js`             | **No**                     | Not active; see the note at the end                                                   |

## Modal

A modal is Bulma's [modal](https://bulma.io/documentation/components/modal/) component, opened by
a trigger button. The trigger carries `data-modal` with the `id` of the modal:

```html
<button type="button" class="button is-info is-light" data-modal="error-debug-modal">
  Show details
</button>

<div id="error-debug-modal" class="modal">
  <div class="modal-background"></div>
  <div class="modal-content">
    <div class="box">…</div>
  </div>
  <button type="button" class="modal-close is-large" aria-label="close"></button>
</div>
```

The modal is closed by:

- a click on `.modal-background` or `.modal-close` inside it,
- the **Escape** key.

Opening sets `is-active` on the modal. Keep the modal `id` unique on the page.

## Loading buttons

A button carrying `data-loading` gets Bulma's `is-loading` modifier when it is clicked,
showing a spinner while the action it triggers (typically a form submission or navigation)
completes:

```html
<button type="submit" class="button is-primary" data-loading>
  <span class="icon"><i class="et et-magnify"></i></span>
  <span>Search</span>
</button>
```

- The `is-loading` class is added on click. No other state is managed, and the button is
  not disabled, so the surrounding form still submits normally.
- Use it on submit buttons whose action navigates or reloads the page. Without JavaScript
  the button behaves as an ordinary button.
- The hook is the `data-loading` attribute; never select loading buttons through a styling
  class.

## Event filter dropdowns

The event directory's district select carries `data-event-filter-district` with the id of the
county select it controls. County options carry `data-district`, matching the district of their
optgroup:

```html
<select id="events-district" name="district" data-event-filter-district="events-county">…</select>

<select id="events-county" name="county">
  <option value="">Any county</option>
  <optgroup label="Aveiro" data-district="Aveiro">
    <option value="Aveiro" data-district="Aveiro">Aveiro</option>
  </optgroup>
</select>
```

On load and on every district change, the behaviour hides and disables county options (and their
optgroups) from other districts. An empty district shows them all. If the selected county no longer
belongs to the chosen district, the county select resets to its empty "Any county" option.

The server still renders and selects the correct options for the current URL, so the behaviour is
progressive enhancement. No navigation happens, which means a typed search term and focus are
preserved. Do not use styling classes as hooks: the only hooks are
`data-event-filter-district` and `data-district`.

## Navbar burger

The burger button toggles the navigation menu on small screens. It carries `data-target` with the
`id` of the menu it controls:

```html
<a role="button" class="navbar-burger" aria-label="menu" aria-expanded="false"
   data-target="main-navbar">
  <span aria-hidden="true"></span>
  <span aria-hidden="true"></span>
  <span aria-hidden="true"></span>
  <span aria-hidden="true"></span>
</a>

<div id="main-navbar" class="navbar-menu">…</div>
```

A click toggles `is-active` on both the burger and the menu. The module does not update
`aria-expanded`, so keep it in step in your markup if you change this behaviour.

## Language selector

The language selector is the `.switch.is-inner-label` control in the navbar, with the id
`language-selector` (see [Switch Control](./switch-control.md#inner-label-variant)). It is part of
the site's bilingual routing, and it behaves as follows:

- The choice is saved in `localStorage` under `endurancetrioLanguage` when storage is available;
  a storage failure (for example in private browsing) does not prevent navigation.
- Toggling the switch moves the page to the same path under the other language prefix (`/en/…` to
  `/pt/…`), keeping the query string.
- On each page load, a stored language that differs from the page's language redirects the
  visitor to the matching page. Error pages use the same mechanism: the browser keeps the original
  localized URL while only the server-side dispatch goes to `/error`.

Do not reuse the `language-selector` id for another control. The script looks it up by id when
the page loads.

## Scroll to end

An element carrying `data-scroll-to-end` is scrolled to its right end as soon as it has a layout
width, which shows the last item of a wide row (a total, for example) without pinning it:

```html
<div class="level is-mobile is-scrollable-x" data-scroll-to-end data-scroll-cue>…</div>
```

- The scroll is applied once per element. A row inside a collapsed region starts with no width,
  so the behaviour waits for the first reveal; re-opening the region later keeps the position the
  reader left.
- The jump is instant even when the row opts into smooth scrolling, so later scrolling keeps the
  animation and the first reveal does not.
- A row that does not overflow is left alone, and without JavaScript it opens at its start edge.
- When the row is also wrapped in an element carrying `data-scroll-region`, the wrapper receives
  the `is-overflow-start` / `is-overflow-end` state classes, kept in sync with the scroll
  position and removed when the row fits. The same wrapper hosts the edge shadow (a hard 4 px
  line in `var(--bulma-background)`, matching Bulma's navbar bottom shadow) and a visually
  hidden description.
- Once per element, and only when motion is allowed, the row nudges a few pixels toward its
  hidden content on first reveal; any pointer, keyboard, wheel or touch input cancels it.

See [Scrollable Rows](./scroll.md#affordance) for the markup and styling side.

## Not active: `bulmaPanel`

`src/js/bulma-panel.js` filters the blocks of a Bulma panel by tab (`data-panel-tabs`,
`data-panel-target`, `data-panel-value`). It is not imported by `script.js`, and its styles,
`components/endurance-panel.scss`, are not forwarded in `style.scss`. Panel filtering therefore does
nothing at present.

To enable it, add `bulmaPanel.init()` to `src/js/script.js`, uncomment the panel `@forward`, and
document the component here before using it in a template.
