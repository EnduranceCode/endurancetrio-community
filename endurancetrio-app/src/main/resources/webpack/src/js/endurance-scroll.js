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
 * Opens a scroll container at its end edge.
 *
 * An element carrying the `data-scroll-to-end` attribute is scrolled to its right end once it
 * has a layout width, so the last item (a total, for example) is visible without pinning it.
 * CSS cannot do this portably: `scroll-initial-target` is unsupported by Safari and Firefox.
 *
 * A container inside a collapsed region (`data-expand-content`) has no width until the region
 * is revealed, so the scroll waits for the element to become visible. It is applied once only:
 * from then on the scroll position belongs to the reader, and re-opening the region must not
 * pull the row back.
 *
 * Without JavaScript the row opens at its start edge; nothing is hidden or unreachable.
 *
 * @namespace enduranceScroll
 */
const enduranceScroll = {
  init() {
    document.querySelectorAll('[data-scroll-to-end]').forEach((element) => {
      this._scrollWhenVisible(element);
    });
  },

  /**
   * Scrolls `element` to its end edge as soon as it has a layout width.
   *
   * @param {Element} element
   */
  _scrollWhenVisible(element) {
    if (!('ResizeObserver' in window)) {
      this._scrollToEnd(element);
      return;
    }

    const observer = new window.ResizeObserver(() => {
      if (element.clientWidth === 0) {
        return;
      }
      observer.disconnect();
      this._scrollToEnd(element);
    });

    observer.observe(element);
  },

  /**
   * Moves the scroll position to the end edge without animating.
   *
   * The row opts into smooth scrolling, which would otherwise slide the content into place the
   * first time the region opens. The inline override is reverted straight after the jump, so
   * later keyboard scrolling keeps the authored behaviour.
   *
   * @param {Element} element
   */
  _scrollToEnd(element) {
    if (element.scrollWidth <= element.clientWidth) {
      return;
    }

    const { scrollBehavior } = element.style;
    element.style.scrollBehavior = 'auto';
    element.scrollLeft = element.scrollWidth - element.clientWidth;
    element.style.scrollBehavior = scrollBehavior;
  },
};

export default enduranceScroll;
