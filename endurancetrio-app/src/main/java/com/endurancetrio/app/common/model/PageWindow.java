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

package com.endurancetrio.app.common.model;

import java.util.List;

/**
 * {@link PageWindow} holds the windowed list of page indices rendered by a numbered pagination
 * control, together with flags indicating whether an ellipsis placeholder is needed before or
 * after the window.
 *
 * @param pages     the sorted list of 0-based page indices to display
 * @param gapBefore {@code true} if an ellipsis is needed between the first page and the window
 * @param gapAfter  {@code true} if an ellipsis is needed between the window and the last page
 */
public record PageWindow(List<Integer> pages, boolean gapBefore, boolean gapAfter) {

  public PageWindow {
    if (pages == null) {
      throw new IllegalArgumentException("pages must not be null");
    }
    pages = List.copyOf(pages);
  }
}
