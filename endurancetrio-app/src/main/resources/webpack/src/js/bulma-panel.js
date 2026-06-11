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
 * Object providing tab-based filtering for Bulma panel components.
 *
 * @namespace bulmaPanel
 */
const bulmaPanel = {
  /**
   * Initialises tab-based content filtering on Bulma `.panel` or `.endurance-panel` elements.
   *
   * The required markup structure is:
   *
   * ```html
   * <nav class="panel">                              <!-- or .endurance-panel -->
   *   <p class="panel-heading">…</p>
   *   <div class="panel-tabs" data-panel-tabs>        <!-- tab bar container -->
   *     <a class="is-active" data-panel-target="ALL">All</a>
   *     <a data-panel-target="VALUE_A,VALUE_B">Group</a>
   *   </div>
   *   <div data-panel-value="VALUE_A">…</div>         <!-- filterable content -->
   *   <div data-panel-value="VALUE_B">…</div>
   *   <div data-panel-value="VALUE_C">…</div>         <!-- hidden when Group is active -->
   * </nav>
   * ```
   *
   * **Data attributes used:**
   *
   * | Attribute           | Applied to                    | Purpose                                            |
   * |---------------------|-------------------------------|----------------------------------------------------|
   * | `data-panel-tabs`   | `.panel-tabs` container       | Hooks the click event listener                     |
   * | `data-panel-target` | Each `<a>` inside the tab bar | Comma-separated values to show, or `"ALL"` for all |
   * | `data-panel-value`  | Each filterable content block | Value matched against the active tab's target      |
   *
   * On first interaction the panel's `minHeight` is locked to prevent layout shift.
   *
   * @function init
   * @memberof bulmaPanel
   * @see {@link https://bulma.io/documentation/components/panel/}
   */
  init() {
    const containers = document.querySelectorAll('[data-panel-tabs]');
    if (containers.length === 0) {
      return;
    }

    containers.forEach((container) => {
      container.addEventListener('click', (event) => {
        const tab = event.target.closest('a');
        if (!tab) {
          return;
        }
        event.preventDefault();

        container.querySelectorAll('a').forEach((t) => t.classList.remove('is-active'));
        tab.classList.add('is-active');

        const rawTarget = tab.dataset.panelTarget;
        const panel = container.closest('.endurance-panel, .panel');
        if (panel) {
          if (!panel.style.minHeight) {
            panel.style.minHeight = panel.offsetHeight + 'px';
          }

          panel.querySelectorAll('[data-panel-value]').forEach((block) => {
            const blockValue = block.dataset.panelValue;
            const matches = rawTarget === 'ALL' || rawTarget.split(',').some((t) => t.trim() === blockValue);
            block.style.display = matches ? '' : 'none';
          });
        }
      });
    });
  },
};

export default bulmaPanel;
