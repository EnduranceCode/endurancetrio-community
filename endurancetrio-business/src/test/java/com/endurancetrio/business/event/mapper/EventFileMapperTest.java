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
 * EVEN IF WE HAVE BEEN INFORMED OF THE POSSIBILITY IN ADVANCE.
 */

package com.endurancetrio.business.event.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.endurancetrio.business.event.dto.EventFileDTO;
import com.endurancetrio.data.event.model.entity.EventFile;
import com.endurancetrio.data.event.model.enumerator.FileType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class   EventFileMapperTest {

  private static final Long ID = 1L;
  private static final String TITLE = "Regulamento";
  private static final FileType FILE_TYPE = FileType.RULES;
  private static final String FILE_NAME = "19840815NAC001-REG001-01.pdf";

  private EventFile entityTest;

  private EventFileMapper underTest;

  @BeforeEach
  void setUp() {
    underTest = new EventFileMapper();

    entityTest = new EventFile();
    entityTest.setId(ID);
    entityTest.setTitle(TITLE);
    entityTest.setFileType(FILE_TYPE);
    entityTest.setFileName(FILE_NAME);
  }

  @Test
  void mapEntity() {
    EventFileDTO result = underTest.map(entityTest);

    assertNotNull(result);
    assertEquals(ID, result.id());
    assertEquals(TITLE, result.title());
    assertEquals(FILE_TYPE.getCode(), result.fileType());
    assertEquals(FILE_NAME, result.fileName());
  }

  @Test
  void mapNullEntity() {
    EventFileDTO result = underTest.map((EventFile) null);

    assertNull(result);
  }
}
