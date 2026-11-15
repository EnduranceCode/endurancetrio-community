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
 * Opens a scroll container at its end edge and makes the scroll discoverable.
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
 * In addition, when the row is wrapped in an element carrying `data-scroll-region`, the wrapper
 * receives the `is-overflow-start` / `is-overflow-end` state classes (removed entirely when the
 * row fits the viewport), which the stylesheet uses to reveal the edge shadow. Once per element,
 * and only when motion is allowed, the row also nudges a few pixels
 * toward its hidden content on first reveal, so the clipped edge is noticed.
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
      this._manageOverflow(element);
      this._nudge(element);
      return;
    }

    const observer = new window.ResizeObserver(() => {
      if (element.clientWidth === 0) {
        return;
      }
      observer.disconnect();
      this._scrollToEnd(element);
      this._manageOverflow(element);
      this._nudge(element);
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

  /**
   * Resolves the element that hosts the overflow state classes for `element`.
   *
   * @param {Element} element
   * @returns {Element}
   */
  _region(element) {
    return element.closest('[data-scroll-region]') || element;
  },

  /**
   * Keeps the `is-overflow-start` / `is-overflow-end` state classes on the host region in sync
   * with the scroll position. Both classes are removed when the row fits the viewport, so no
   * affordance chrome is rendered for a non-scrolling row.
   *
   * @param {Element} element
   */
  _manageOverflow(element) {
    const region = this._region(element);

    const update = () => {
      if (element.scrollWidth <= element.clientWidth + 1) {
        region.classList.remove('is-overflow-start', 'is-overflow-end');
        return;
      }

      const atStart = element.scrollLeft <= 1;
      const atEnd = element.scrollLeft >= element.scrollWidth - element.clientWidth - 1;
      region.classList.toggle('is-overflow-start', !atStart);
      region.classList.toggle('is-overflow-end', !atEnd);
    };

    update();
    element.addEventListener('scroll', update, { passive: true });

    if ('ResizeObserver' in window) {
      const observer = new window.ResizeObserver(update);
      observer.observe(element);
    }
  },

  /**
   * Nudges the row a few pixels toward its hidden content once, on first reveal, to draw
   * attention to the clipped edge. Skipped under reduced motion, when the row fits, or when the
   * reader has already interacted.
   *
   * @param {Element} element
   */
  _nudge(element) {
    if (element.dataset.scrollNudged === 'true') {
      return;
    }
    element.dataset.scrollNudged = 'true';

    if (window.matchMedia('(prefers-reduced-motion: reduce)').matches) {
      return;
    }
    if (element.scrollWidth <= element.clientWidth + 1) {
      return;
    }
    // Only nudge when the resting position still hides content on the opposite side.
    if (element.scrollLeft < element.scrollWidth - element.clientWidth - 1) {
      return;
    }

    let cancelled = false;
    const cleanup = () => {
      element.removeEventListener('pointerdown', cancel);
      element.removeEventListener('keydown', cancel);
      element.removeEventListener('wheel', cancel);
      element.removeEventListener('touchstart', cancel);
    };
    const cancel = () => {
      cancelled = true;
      cleanup();
    };

    element.addEventListener('pointerdown', cancel, { passive: true });
    element.addEventListener('keydown', cancel);
    element.addEventListener('wheel', cancel, { passive: true });
    element.addEventListener('touchstart', cancel, { passive: true });

    window.requestAnimationFrame(() => {
      window.requestAnimationFrame(() => {
        if (cancelled) {
          cleanup();
          return;
        }

        const distance = Math.min(32, Math.round(element.clientWidth * 0.12));
        const start = element.scrollLeft;
        const duration = 350;
        const previousBehavior = element.style.scrollBehavior;
        const previousSnap = element.style.scrollSnapType;

        element.style.scrollBehavior = 'auto';
        element.style.scrollSnapType = 'none';

        const begin = window.performance.now();
        const step = (now) => {
          if (cancelled) {
            element.scrollLeft = start;
            element.style.scrollBehavior = previousBehavior;
            element.style.scrollSnapType = previousSnap;
            cleanup();
            return;
          }

          const progress = Math.min(1, (now - begin) / duration);
          // Triangle: out to the hidden edge, then back to the resting position.
          const phase = progress < 0.5 ? progress * 2 : (1 - progress) * 2;
          element.scrollLeft = start - distance * phase;

          if (progress < 1) {
            window.requestAnimationFrame(step);
          } else {
            element.scrollLeft = start;
            element.style.scrollBehavior = previousBehavior;
            element.style.scrollSnapType = previousSnap;
            cleanup();
          }
        };

        window.requestAnimationFrame(step);
      });
    });
  },
};

export default enduranceScroll;
