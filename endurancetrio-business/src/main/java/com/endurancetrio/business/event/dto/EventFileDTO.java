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

package com.endurancetrio.business.event.dto;

/**
 * The {@link EventFileDTO} represents a downloadable file associated with an event.
 *
 * @param id       the unique identifier of the file (nullable for unpersisted DTOs)
 * @param title    the display title of the file
 * @param fileType the type of the file (e.g. GUIDE, RULES, POSTER)
 * @param fileName the file name used for downloading
 */
public record EventFileDTO(Long id, String title, String fileType, String fileName) {

  public EventFileDTO {
    if (title == null || title.isBlank()) {
      throw new IllegalArgumentException("title must not be null or blank");
    }
    if (fileType == null || fileType.isBlank()) {
      throw new IllegalArgumentException("fileType must not be null or blank");
    }
    if (fileName == null || fileName.isBlank()) {
      throw new IllegalArgumentException("fileName must not be null or blank");
    }
  }
}
