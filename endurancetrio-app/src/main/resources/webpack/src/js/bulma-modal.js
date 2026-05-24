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
 * Object containing Bulma Modal functionality.
 *
 * @namespace bulmaModal
 */
const bulmaModal = {
  /**
   * Initializes the Bulma Modal functionality.
   *
   * This function sets up event listeners on all elements with a 'data-modal' attribute.
   * When clicked, it opens the modal identified by the attribute value.
   * Clicking the modal background or close button closes it.
   * Pressing the Escape key closes all open modals.
   *
   * @function init
   * @memberof bulmaModal
   * @see {@link https://bulma.io/documentation/components/modal/}
   */
  init() {
    document.querySelectorAll('[data-modal]').forEach((trigger) => {
      trigger.addEventListener('click', () => {
        const target = document.getElementById(trigger.dataset.modal);
        if (target) {
          target.classList.add('is-active');
        }
      });
    });

    document.addEventListener('click', (e) => {
      const modal = e.target.closest('.modal');
      if (!modal) {
        return;
      }
      if (e.target.matches('.modal-background, .modal-close')) {
        modal.classList.remove('is-active');
      }
    });

    document.addEventListener('keydown', (e) => {
      if (e.key === 'Escape') {
        document.querySelectorAll('.modal.is-active').forEach((m) => {
          m.classList.remove('is-active');
        });
      }
    });
  },
};

export default bulmaModal;
