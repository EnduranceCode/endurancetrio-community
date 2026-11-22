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

/**
 * Builds the current page path without its language prefix, keeping the query string.
 *
 * @param {string} currentLanguage The language prefix of the current URL
 * @returns {string} The path and query string following the language prefix
 */
function pathAndQueryWithoutPrefix(currentLanguage) {
  const prefix = '/' + currentLanguage;
  const pathname = globalThis.location.pathname;
  const pathWithoutPrefix =
    pathname.startsWith(prefix + '/') || pathname === prefix ? pathname.slice(prefix.length) : pathname;

  return pathWithoutPrefix + globalThis.location.search;
}

/**
 * Object containing language selector functionality.
 *
 * @namespace languageSelector
 */
const languageSelector = {
  /**
   * Initializes the language selector functionality.
   *
   * This function sets up event listeners on the language selector checkbox. When the checkbox is toggled, the
   * language is stored (when storage is available) and the page is redirected to the same path under the other
   * language prefix. A stored preference that differs from the URL is applied on page load.
   *
   * @function init
   * @memberof languageSelector
   */
  init() {
    const languageOption = document.querySelector('#language-selector');
    if (!languageOption) {
      return;
    }

    const storageKey = 'endurancetrioLanguage';
    let navigating = false;

    /**
     * Gets the current language based on the URL path. Defaults to 'en' if no language is found.
     *
     * @returns {string} The current language ('pt' or 'en')
     */
    function getCurrentLanguage() {
      const supportedLanguages = new Set(['pt', 'en']);

      const pathSegments = globalThis.location.pathname.split('/');

      return supportedLanguages.has(pathSegments[1]) ? pathSegments[1] : 'en';
    }

    /**
     * Reads the preferred language from local storage. Storage can be unavailable in private browsing or when
     * cookies are blocked, in which case no preference is reported.
     *
     * @returns {?string} The stored language ('pt' or 'en'), or null
     */
    function getLanguagePreference() {
      try {
        return localStorage.getItem(storageKey);
      } catch {
        return null;
      }
    }

    /**
     * Sets the preferred language in local storage ('pt' or 'en'). This function is called when the user changes
     * the language preference. A storage failure must not prevent the navigation: the URL prefix is authoritative
     * for the request that follows.
     *
     * @param {String} locale The language to set as the preferred language ('pt' or 'en')
     */
    function setLanguagePreference(locale) {
      try {
        localStorage.setItem(storageKey, locale);
      } catch {
        // Storage is optional; the language prefix in the URL carries the choice.
      }
    }

    /**
     * Applies the stored language preference on page load. If a stored language exists and does not match the current
     * URL, the page is redirected to the page with the selected language.
     */
    function applyStoredLanguage() {
      const storedLanguage = getLanguagePreference();
      const currentLanguage = getCurrentLanguage();

      if (storedLanguage && storedLanguage !== currentLanguage) {
        globalThis.location.href = `/${storedLanguage}${pathAndQueryWithoutPrefix(currentLanguage)}`;
      } else {
        languageOption.checked = currentLanguage === 'pt';
      }
    }

    /**
     * Event listener for the language switch. Some browsers fire only 'change' for a checkbox toggled through its
     * label while others fire 'input' first; the guard keeps the navigation to a single occurrence.
     */
    function handleLanguageChange() {
      if (navigating) {
        return;
      }

      const newLanguage = this.checked ? 'pt' : 'en';
      const currentLanguage = getCurrentLanguage();

      if (newLanguage !== currentLanguage) {
        navigating = true;
        setLanguagePreference(newLanguage);
        globalThis.location.replace(`/${newLanguage}${pathAndQueryWithoutPrefix(currentLanguage)}`);
      }
    }

    languageOption.addEventListener('input', handleLanguageChange);
    languageOption.addEventListener('change', handleLanguageChange);

    applyStoredLanguage();
  },
};

export default languageSelector;
