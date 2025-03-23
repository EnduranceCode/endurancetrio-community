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
 * Object containing locale selector functionality.
 *
 * @namespace localeSelector
 */
const localeSelector = {
  /**
   * Initializes the locale selector functionality.
   *
   * This function sets up an event listener on the locale selector checkbox. When the checkbox is toggled, the
   * locale is updated in local storage and the page is redirected to the new locale.
   *
   * @function init
   * @memberof localeSelector
   */
  init() {
    const localeSwitch = document.querySelector('#locale-selector');
    if (!localeSwitch) {
      return;
    }

    /**
     * Gets the current locale based on the URL path. Defaults to 'en' if no locale is found.
     *
     * @returns {string} The current locale ('pt' or 'en')
     */
    function getCurrentLocale() {
      const supportedLocales = new Set(['pt', 'en']);

      const pathSegments = window.location.pathname.split('/');

      return supportedLocales.has(pathSegments[1]) ? pathSegments[1] : 'en';
    }

    /**
     * Sets the preferred locale in local storage ('pt' or 'en'). This function is called when the user changes
     * the language preference.
     *
     * @param {String} locale The locale to set as the preferred locale
     */
    function setLanguagePreference(locale) {
      localStorage.setItem('endurancetrioLocale', locale);
    }

    /**
     * Applies the stored locale preference on page load. If a stored locale exists and does not match the current
     * URL, the page is redirected to the stored locale.
     */
    function applyStoredLocale() {
      const storedLocale = localStorage.getItem('endurancetrioLocale');
      const currentLocale = getCurrentLocale();

      if (storedLocale && storedLocale !== currentLocale) {
        window.location.href = `/${storedLocale}` + window.location.pathname.substring(3);
      } else {
        localeSwitch.checked = currentLocale === 'pt';
      }
    }

    /**
     * Event listener for the language switch. When the switch is toggled, the locale is updated in local storage
     * and the page is redirected to the new locale.
     */
    localeSwitch.addEventListener('input', function () {
      const newLocale = this.checked ? 'pt' : 'en';

      if (newLocale !== getCurrentLocale()) {
        setLanguagePreference(newLocale);
        window.location.replace(`/${newLocale}${window.location.pathname.substring(3)}`);
      }
    });

    applyStoredLocale();
  },
};

export default localeSelector;
