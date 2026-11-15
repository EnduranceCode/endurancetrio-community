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
 * Loading state for Bulma buttons.
 *
 * A button carrying the `data-loading` attribute gets Bulma's `is-loading`
 * modifier when it is clicked, showing a spinner while the action it triggers
 * (typically a form submission or navigation) completes.
 *
 * @namespace bulmaLoading
 */
const bulmaLoading = {
  /**
   * Initializes click listeners on all buttons with `data-loading`.
   *
   * @function init
   * @memberof bulmaLoading
   * @see {@link https://bulma.io/documentation/elements/button/#loading}
   */
  init() {
    document.querySelectorAll('[data-loading]').forEach((button) => {
      button.addEventListener('click', () => {
        button.classList.add('is-loading');
      });
    });
  },
};

export default bulmaLoading;
