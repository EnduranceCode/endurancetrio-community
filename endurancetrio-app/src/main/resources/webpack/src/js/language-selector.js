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
