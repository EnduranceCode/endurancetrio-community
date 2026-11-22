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

import assert from 'node:assert/strict';
import test from 'node:test';

import languageSelector from '../src/js/language-selector.js';

const managedGlobals = ['document', 'localStorage', 'location'];

function rememberGlobals() {
  const originals = new Map(managedGlobals.map((name) => [name, globalThis[name]]));

  return () => {
    for (const [name, value] of originals) {
      if (value === undefined) {
        delete globalThis[name];
      } else {
        globalThis[name] = value;
      }
    }
  };
}

function installStorage({ stored = null, getItemThrows = false, setItemThrows = false } = {}) {
  const values = new Map();
  if (stored !== null) {
    values.set('endurancetrioLanguage', stored);
  }

  globalThis.localStorage = {
    getItem(key) {
      if (getItemThrows) {
        throw new Error('storage blocked');
      }
      return values.get(key) ?? null;
    },
    setItem(key, value) {
      if (setItemThrows) {
        throw new Error('storage blocked');
      }
      values.set(key, value);
    },
  };

  return values;
}

function installElement(checked) {
  const listeners = new Map();
  const element = {
    checked,
    addEventListener(type, handler) {
      listeners.set(type, [...(listeners.get(type) ?? []), handler]);
    },
    fire(type) {
      for (const handler of listeners.get(type) ?? []) {
        handler.call(element, { type });
      }
    },
  };

  globalThis.document = {
    querySelector(selector) {
      return selector === '#language-selector' ? element : null;
    },
  };

  return element;
}

function installLocation(pathname, search = '') {
  const location = {
    href: '',
    pathname,
    search,
    replaced: [],
    replace(url) {
      location.replaced.push(url);
    },
  };
  globalThis.location = location;

  return location;
}

test('toggling off stores English and replaces the URL while keeping the query string', (t) => {
  t.after(rememberGlobals());
  const values = installStorage();
  const element = installElement(true);
  const location = installLocation('/pt/events/1984', '?page=2');

  languageSelector.init();
  element.checked = false;
  element.fire('input');

  assert.equal(values.get('endurancetrioLanguage'), 'en');
  assert.deepEqual(location.replaced, ['/en/events/1984?page=2']);
});

test('toggling on stores Portuguese and replaces the URL while keeping the query string', (t) => {
  t.after(rememberGlobals());
  const values = installStorage();
  const element = installElement(false);
  const location = installLocation('/en/events', '?page=2');

  languageSelector.init();
  element.checked = true;
  element.fire('input');

  assert.equal(values.get('endurancetrioLanguage'), 'pt');
  assert.deepEqual(location.replaced, ['/pt/events?page=2']);
});

test('a stored language differing from the URL redirects on load and keeps the query string', (t) => {
  t.after(rememberGlobals());
  installStorage({ stored: 'en' });
  installElement(false);
  const location = installLocation('/pt/events', '?page=2');

  languageSelector.init();

  assert.equal(location.href, '/en/events?page=2');
});

test('the switch reflects the URL language when no preference is stored', (t) => {
  t.after(rememberGlobals());
  installStorage();
  const portugueseElement = installElement(false);
  installLocation('/pt/events');

  languageSelector.init();

  assert.equal(portugueseElement.checked, true);
});

test('a blocked setItem does not prevent the language navigation', (t) => {
  t.after(rememberGlobals());
  installStorage({ setItemThrows: true });
  const element = installElement(true);
  const location = installLocation('/pt/events');

  languageSelector.init();
  element.checked = false;
  element.fire('input');

  assert.deepEqual(location.replaced, ['/en/events']);
});

test('a blocked getItem is ignored and the switch still responds to change', (t) => {
  t.after(rememberGlobals());
  const values = installStorage({ getItemThrows: true });
  const element = installElement(false);
  const location = installLocation('/en/events');

  languageSelector.init();
  element.checked = true;
  element.fire('change');

  assert.deepEqual(location.replaced, ['/pt/events']);
  assert.equal(values.get('endurancetrioLanguage'), 'pt');
});

test('input and change fire once each but navigate only once', (t) => {
  t.after(rememberGlobals());
  installStorage();
  const element = installElement(true);
  const location = installLocation('/pt/events');

  languageSelector.init();
  element.checked = false;
  element.fire('input');
  element.fire('change');

  assert.deepEqual(location.replaced, ['/en/events']);
});

test('init does nothing when the switch is absent', (t) => {
  t.after(rememberGlobals());
  installStorage();
  installLocation('/en/events');
  globalThis.document = { querySelector: () => null };

  assert.doesNotThrow(() => languageSelector.init());
});
