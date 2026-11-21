/*
 * Copyright (c) 2011-2026 Ricardo do Canto
 *
 * This file is part of the EnduranceTrio project.
 *
 * Licensed under the Functional Software License (FSL), Version 1.1, ALv2 Future License
 * (the "License");
 *
 * You may not use this file except in compliance with the License. You may obtain a copy
 * of the License at https://fsl.software/
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND WITHOUT WARRANTIES OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING WITHOUT LIMITATION WARRANTIES OF FITNESS FOR A PARTICULAR
 * PURPOSE, MERCHANTABILITY, TITLE OR NON-INFRINGEMENT.
 *
 * IN NO EVENT WILL WE HAVE ANY LIABILITY TO YOU ARISING OUT OF OR RELATED TO THE
 * SOFTWARE, INCLUDING INDIRECT, SPECIAL, INCIDENTAL OR CONSEQUENTIAL DAMAGES,
 * EVEN IF WE HAVE BEEN INFORMED OF THEIR POSSIBILITY IN ADVANCE.
 */

import js from '@eslint/js';
import prettierPlugin from 'eslint-plugin-prettier';
import prettierConfig from 'eslint-config-prettier';
import html from '@html-eslint/eslint-plugin';

// Code Guide (https://codeguide.co/#html-attribute-order) attribute order. Inside the
// data-* group, Thymeleaf processors follow their execution precedence
// (https://www.thymeleaf.org/doc/tutorials/2.1/usingthymeleaf.html#attribute-precedence),
// which is a readability rule; Thymeleaf itself does not depend on the written order.
// The last catch-all keeps unlisted attributes in source order.
const attributeOrder = [
  'class',
  { pattern: '^(id|name)$' },
  // Thymeleaf processors, by precedence (1-9), then a fallback for unclassified ones.
  { pattern: '^data-th-(include|replace|insert)$' },
  { pattern: '^data-th-each$' },
  { pattern: '^data-th-(if|unless|switch|case)$' },
  { pattern: '^data-th-(object|with)$' },
  { pattern: '^data-th-(attr|attrprepend|attrappend)$' },
  {
    pattern:
      '^data-th-(value|href|src|alt|class|classappend|id|lang|content|placeholder|label|selected|checked|action|style|type|name|for|rel|title)$',
  },
  { pattern: '^data-th-(text|utext)$' },
  { pattern: '^data-th-fragment$' },
  { pattern: '^data-th-remove$' },
  { pattern: '^data-th-' },
  { pattern: '^data-' },
  { pattern: '^(src|for|type|href|value)$' },
  { pattern: '^(title|alt)$' },
  { pattern: '^(role|aria-)' },
  'tabindex',
  'style',
  { pattern: '.*', order: 'preserve' },
];

export default [
  {
    files: ['**/*.js'],
    ...js.configs.recommended,
  },
  {
    files: ['**/*.js'],
    languageOptions: {
      ecmaVersion: 'latest',
      sourceType: 'module',
      globals: {
        window: 'readonly',
        document: 'readonly',
        console: 'readonly',
        process: 'readonly',
        module: 'readonly',
        __dirname: 'readonly',
        require: 'readonly',
        localStorage: 'readonly',
      },
    },
    linterOptions: {
      reportUnusedDisableDirectives: true,
    },
    plugins: {
      prettier: prettierPlugin,
    },
    rules: {
      indent: ['error', 2, { SwitchCase: 1 }],
      quotes: ['error', 'single', { avoidEscape: true }],
      semi: ['error', 'always'],
      'prettier/prettier': [
        'error',
        {
          printWidth: 120,
          singleQuote: true,
          trailingComma: 'es5',
          bracketSpacing: true,
          arrowParens: 'always',
          endOfLine: 'auto',
        },
      ],
    },
  },
  {
    files: ['**/*.html'],
    language: 'html/html',
    plugins: {
      html,
    },
    rules: {
      'html/indent': ['error', 2],
      'html/lowercase': 'error',
      'html/no-duplicate-attrs': 'error',
      'html/no-multiple-empty-lines': 'error',
      'html/no-restricted-attr-values': [
        'error',
        {
          attrPatterns: [
            '^(async|autofocus|checked|defer|disabled|hidden|ismap|multiple|novalidate|open|readonly|required|reversed|scoped|seamless|selected)$',
          ],
          attrValuePatterns: ['.*'],
          message: 'Boolean attributes must not have a value (Code Guide, Boolean attributes).',
        },
      ],
      'html/no-trailing-spaces': 'error',
      'html/quotes': 'error',
      'html/require-closing-tags': 'error',
      'html/require-doctype': 'error',
      'html/require-img-alt': 'error',
      'html/require-lang': 'error',
      'html/sort-attrs': ['error', { priority: attributeOrder }],
    },
  },
  {
    files: ['**/fragments/**/*.html'],
    rules: {
      'html/require-doctype': 'off',
      'html/require-img-alt': 'off',
      'html/require-lang': 'off',
    },
  },
  prettierConfig,
];
