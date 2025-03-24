/*
 * Copyright (c) 2011-2025 Ricardo do Canto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Object containing language selector functionality.
 *
 * @namespace languageSelector
 */
const languageSelector = {
  /**
   * Initializes the language selector functionality.
   *
   * This function sets up an event listener on the language selector checkbox. When the checkbox is toggled, the
   * language is updated in local storage and the page is redirected to the page with the selected language.
   *
   * @function init
   * @memberof languageSelector
   */
  init() {
    const languageOption = document.querySelector('#language-selector');
    if (!languageOption) {
      return;
    }

    /**
     * Gets the current language based on the URL path. Defaults to 'en' if no language is found.
     *
     * @returns {string} The current language ('pt' or 'en')
     */
    function getCurrentLanguage() {
      const supportedLanguages = new Set(['pt', 'en']);

      const pathSegments = window.location.pathname.split('/');

      return supportedLanguages.has(pathSegments[1]) ? pathSegments[1] : 'en';
    }

    /**
     * Sets the preferred language in local storage ('pt' or 'en'). This function is called when the user changes
     * the language preference.
     *
     * @param {String} locale The language to set as the preferred language ('pt' or 'en')
     */
    function setLanguagePreference(locale) {
      localStorage.setItem('endurancetrioLanguage', locale);
    }

    /**
     * Applies the stored language preference on page load. If a stored language exists and does not match the current
     * URL, the page is redirected to the page with the selected language.
     */
    function applyStoredLanguage() {
      const storedLanguage = localStorage.getItem('endurancetrioLanguage');
      const currentLanguage = getCurrentLanguage();

      if (storedLanguage && storedLanguage !== currentLanguage) {
        window.location.href = `/${storedLanguage}` + window.location.pathname.substring(3);
      } else {
        languageOption.checked = currentLanguage === 'pt';
      }
    }

    /**
     * Event listener for the language switch. When the switch is toggled, the language is updated in local storage
     * and the page is redirected to the page with the selected language.
     */
    languageOption.addEventListener('input', function () {
      const newLanguage = this.checked ? 'pt' : 'en';

      if (newLanguage !== getCurrentLanguage()) {
        setLanguagePreference(newLanguage);
        window.location.replace(`/${newLanguage}${window.location.pathname.substring(3)}`);
      }
    });

    applyStoredLanguage();
  },
};

export default languageSelector;
