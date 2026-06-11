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
 * Object providing tabbed content switching with URL hash synchronisation.
 *
 * @namespace bulmaTab
 */
const bulmaTab = {
  /**
   * Initialises tabbed content switching on elements with a `data-tabs` attribute.
   *
   * The required markup structure is:
   *
   * ```html
   * <div data-tabs>
   *   <a data-target="tab1" class="is-active">First</a>
   *   <a data-target="tab2">Second</a>
   * </div>
   *
   * <div id="tab1" data-tab-content class="is-active">…</div>
   * <div id="tab2" data-tab-content>…</div>
   * ```
   *
   * **Data attributes used:**
   *
   * | Attribute                      | Applied to                | Purpose                                         |
   * |--------------------------------|---------------------------|-------------------------------------------------|
   * | `data-tabs`                    | Tab bar container         | Hooks initialisation and click listeners        |
   * | `data-target`                  | Each tab item             | ID of the associated content panel              |
   * | `data-tab-content`             | Each content panel        | Marks panels that are toggled via `is-active`   |
   * | `data-button="tab-navigation"` | External trigger elements | Alternative way to switch tabs programmatically |
   *
   * The active tab is determined by (in order of precedence):
   *
   *   1. The URL hash (e.g. `#tab2`)
   *   2. The element with the `is-active` class in the tab bar
   *
   * On every switch the URL hash is updated and the viewport scrolls to top.
   *
   * @function init
   * @memberof bulmaTab
   * @see {@link https://bulma.io/documentation/components/tabs/}
   */
  init() {
    document.querySelectorAll('[data-tabs]').forEach((tabContainer) => {
      const tabItems = Array.from(tabContainer.children);
      const tabIds = tabItems.map((item) => item.dataset.target);
      let activeTab = tabItems.find((item) => item.classList.contains('is-active'))?.dataset.target;
      const tabFromUrl = window.location.hash.substring(1);
      if (tabIds.includes(tabFromUrl)) {
        activeTab = tabFromUrl;
      }
      if (activeTab) {
        this.toggle(activeTab);
      }
      tabItems.forEach((item) => {
        item.addEventListener('click', () => {
          this.toggle(item.dataset.target);
        });
      });
    });
    document.querySelectorAll("[data-button='tab-navigation']").forEach((button) => {
      button.addEventListener('click', () => {
        this.toggle(button.dataset.target);
      });
    });
    window.addEventListener('hashchange', () => {
      const tabId = window.location.hash.substring(1);
      if (document.querySelector(`[data-tabs] [data-target="${tabId}"]`)) {
        this.toggle(tabId);
      }
    });
  },

  /**
   * Switches to the tab identified by `targetId`.
   *
   * Toggles the `is-active` class on the matching content panel and tab item,
   * pushes the target ID into the URL hash, and scrolls to the top of the page.
   *
   * @function toggle
   * @memberof bulmaTab
   * @param {string} targetId - The ID of the content panel to show (matches `data-target` on the tab and `id` on the content panel).
   */
  toggle(targetId) {
    document.querySelectorAll('[data-tab-content]').forEach((panel) => {
      panel.classList.toggle('is-active', panel.id === targetId);
    });
    document.querySelectorAll('[data-tabs] [data-target]').forEach((item) => {
      item.classList.toggle('is-active', item.dataset.target === targetId);
    });
    window.history.replaceState(null, '', `#${targetId}`);
    window.scroll(0, 0);
  },
};

export default bulmaTab;
