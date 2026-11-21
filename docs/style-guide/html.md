# HTML

> Part of the [EnduranceTrio Style Guide](./index.md).

This page is the normative specification for the Thymeleaf templates in
`endurancetrio-app/src/main/resources/templates/`. It follows the HTML rules of
[Code Guide by @mdo](https://codeguide.co/#html) (v4.0.0), adapted to the project's Thymeleaf and
Bulma conventions. The CSS half of Code Guide is not repeated here: the custom CSS is covered by
the rest of this style guide, and Bulma supplies the framework defaults.

New and edited templates must follow these rules. Reviewers use the
[review checklist](#review-checklist) at the end.

## Golden rule

> Every line of code should appear to be written by a single person, no matter the number of
> contributors.

## Syntax

- Lowercase all tag and attribute names, including the doctype: `<!doctype html>`.
- Use two spaces for indentation. Nest child elements one level (two spaces) deeper than their
  parent.
- Always use double quotes around attribute values, never single quotes.
- Do not add a trailing slash to void elements: write `<meta charset="utf-8">`, not
  `<meta charset="utf-8"/>`.
- Do not omit optional closing tags (`</li>`, `</body>`).
- Keep one statement per line and trim trailing whitespace. End files with a newline.

<!-- prettier-ignore -->
```html
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Page title</title>
</head>
<body>
<img src="images/company-logo.png" alt="Company">
<h1 class="title">Hello, world!</h1>
</body>
</html>
```

## Document structure

- Start every full page with the lowercase HTML5 doctype, before the licence comment.
- Put `lang` on the root `<html>` element. The project uses `data-th-lang="${#locale.language}"`
  together with `lang="en"` as the static fallback.
- Declare UTF-8 explicitly with `<meta charset="utf-8">`. The declaration lives once, in
  `fragments/head.html`; full pages replace their `<head>` with that fragment, so they do not
  declare it again.
- Do not specify `type` on `<link rel="stylesheet">` or `<script>` when the default is correct.
- Keep inline `<script>` and `<style>` blocks out of templates. The existing exceptions are the
  Google Tag Manager bootstrap and JSON-LD in `fragments/head.html`, and the LiveReload client.
  Do not add new ones without a documented reason.
- Prefer semantic markup over extra `<div>` wrappers. A real `<button>` with `data-*` hooks beats
  a styled `<div>` with an inline click handler.

## Attribute order

HTML attributes are written in this order, from
[Code Guide §Attribute order](https://codeguide.co/#html-attribute-order):

1. `class`
2. `id`, `name`
3. `data-*` — Thymeleaf processors first, in precedence order, then other data hooks
4. `src`, `for`, `type`, `href`, `value`
5. `title`, `alt`
6. `role`, `aria-*`
7. `tabindex`
8. `style`
9. Everything else, in its existing relative order

Attributes used to identify an element come first, element-specific attributes next, and
accessibility and style attributes last. Thymeleaf's HTML5 dialect writes processors as
`data-th-*` attributes, so they sort in the `data-*` group — for example
`<html class="has-endurance-navbar-fixed-top" data-th-lang="${#locale.language}" lang="en">`.

```html
<a class="button is-primary" data-th-href="@{/events}" href="/events" title="Events">Events</a>
```

### Thymeleaf attribute precedence

Within the `data-*` group, processors are written in the order of
[Thymeleaf's attribute precedence](https://www.thymeleaf.org/doc/tutorials/2.1/usingthymeleaf.html#attribute-precedence).
This is a readability rule: Thymeleaf assigns each processor a numeric precedence and executes by
that number, regardless of the order in which the attributes are written.

| Order | Feature                         | Attributes                                                                                                                                                                                                                                |
| ----- | ------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 1     | Fragment inclusion              | `data-th-include`, `data-th-replace`, `data-th-insert`                                                                                                                                                                                    |
| 2     | Fragment iteration              | `data-th-each`                                                                                                                                                                                                                            |
| 3     | Conditional evaluation          | `data-th-if`, `data-th-unless`, `data-th-switch`, `data-th-case`                                                                                                                                                                          |
| 4     | Local variable definition       | `data-th-object`, `data-th-with`                                                                                                                                                                                                          |
| 5     | General attribute modification  | `data-th-attr`, `data-th-attrprepend`, `data-th-attrappend`                                                                                                                                                                               |
| 6     | Specific attribute modification | `data-th-value`, `data-th-href`, `data-th-src`, `data-th-classappend`, `data-th-content`, `data-th-lang`, `data-th-alt`, `data-th-id`, `data-th-placeholder`, `data-th-label`, `data-th-selected`, `data-th-checked`, `data-th-action`, … |
| 7     | Text (tag body modification)    | `data-th-text`, `data-th-utext`                                                                                                                                                                                                           |
| 8     | Fragment specification          | `data-th-fragment`                                                                                                                                                                                                                        |
| 9     | Fragment removal                | `data-th-remove`                                                                                                                                                                                                                          |

`data-th-insert` is a Thymeleaf 3 processor and postdates the 2.1 documentation; it is grouped with
the other fragment-inclusion processors. Unclassified `data-th-*` processors are placed after the
nine groups but before other `data-*` hooks, so future processors cannot disturb this order.

Boolean attributes take no value: write `<script defer>`, not `<script defer="defer">`.

## Thymeleaf templates

- Use the HTML5 `data-th-*` dialect, not the XML `th:*` dialect.
- Keep Thymeleaf expressions intact when reordering attributes: `${...}`, `#{...}`, `@{...}` and
  inline `[[${...}]]` are opaque to the HTML tooling.
- `fragments/` files are not standalone documents. They may omit the doctype, `lang` and the
  charset declaration; document-level rules are disabled for them.
- Start every template with the project's licence comment.
- JavaScript hooks are `data-*` attributes, never styling classes. Styling classes stay
  `endurance-{block}`, `is-*` and `has-*`; see [Naming](./naming.md).
- Accessibility rules (real buttons with `aria-expanded`, focusable scroll regions with a
  translated accessible name) are described in [Conventions](./index.md#conventions) and
  [Scrollable Rows](./scroll.md#accessibility).

## Tooling

HTML is linted by [html-eslint](https://html-eslint.org/) through ESLint's flat config in
`endurancetrio-app/src/main/resources/webpack/eslint.config.js`. Run the checks from the webpack
directory:

```shell
npm run format:html   # fix template style and attribute order
npm run format:check  # check Prettier and ESLint, including templates; does not rewrite files
```

The `attributeOrder` constant in `eslint.config.js` is the machine-readable form of both orderings
above. A catch-all pattern with `order: 'preserve'` keeps unlisted attributes in their source order
instead of sorting them alphabetically.

IntelliJ IDEA users are configured through the root `.editorconfig`: the `[*.html]` section disables
attribute alignment (`ij_html_align_attributes = false`) and sets a two-space continuation indent,
so the IDE formatter agrees with `html/indent`. If your IDE version uses different property names,
regenerate them with **Settings → Editor → Code Style → Show Scheme Actions → Export → EditorConfig
File** and copy the `ij_html_*` lines into that section.

| Rule                                                              | Enforced by                       |
| ----------------------------------------------------------------- | --------------------------------- |
| Attribute order                                                   | `html/sort-attrs`                 |
| Lowercase tag and attribute names                                 | `html/lowercase`                  |
| Double-quoted attribute values                                    | `html/quotes`                     |
| Two-space indentation                                             | `html/indent`                     |
| No trailing slash on void elements; optional closing tags kept    | `html/require-closing-tags`       |
| Valueless boolean attributes                                      | `html/no-restricted-attr-values`  |
| Doctype and `lang` on full pages                                  | `require-doctype`, `require-lang` |
| No duplicate attributes, no trailing spaces, no extra blank lines | corresponding `html/*` rules      |
| `img` has `alt` on full pages                                     | `html/require-img-alt`            |

Two syntax details are not covered by the linter and must be checked by hand: the lowercase
`doctype` keyword and the lowercase `utf-8` charset value. Neither tool reliably enforces their
case. Prettier deliberately does **not** format templates: it re-adds trailing slashes to void
elements, which would contradict the rule above.

## Review checklist

- [ ] Doctype and tag/attribute names are lowercase; `charset="utf-8"` is lowercase.
- [ ] Indentation is two spaces per level; no trailing whitespace; file ends with a newline.
- [ ] All attribute values use double quotes; attributes follow the order above, including
      Thymeleaf precedence.
- [ ] Void elements have no trailing slash; optional closing tags are present.
- [ ] Boolean attributes have no value.
- [ ] Thymeleaf processors use the `data-th-*` dialect and their expressions are unchanged.
- [ ] `npm run format:check` passes (from `endurancetrio-app/src/main/resources/webpack/`).
