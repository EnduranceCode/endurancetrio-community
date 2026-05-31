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

package com.endurancetrio.business.newsletter.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.endurancetrio.business.common.exception.EnduranceTrioException;
import com.endurancetrio.business.newsletter.client.KitClient;
import com.endurancetrio.business.newsletter.dto.NewsletterSubscriptionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NewsletterServiceMainTest {

  private static final Long SUBSCRIBER_ID = 42L;
  private static final String FORM_ID_EN = "form_en_123";
  private static final String FORM_ID_PT = "form_pt_456";
  private static final String FIRST_NAME = "John";
  private static final String EMAIL = "john@example.com";

  @Mock
  KitClient kitClient;

  NewsletterServiceMain underTest;
  NewsletterSubscriptionDTO dto;

  @BeforeEach
  void setUp() {
    underTest = new NewsletterServiceMain(kitClient, FORM_ID_EN, FORM_ID_PT);
    dto = new NewsletterSubscriptionDTO(FIRST_NAME, EMAIL);
  }

  @Test
  void subscribeWithEnglishLocale() throws EnduranceTrioException {
    when(kitClient.createOrUpdateSubscriber(dto)).thenReturn(SUBSCRIBER_ID);

    underTest.subscribe(dto, "en");

    verify(kitClient).createOrUpdateSubscriber(dto);
    verify(kitClient).addSubscriberToForm(SUBSCRIBER_ID, FORM_ID_EN);
    verifyNoMoreInteractions(kitClient);
  }

  @Test
  void subscribeWithPortugueseLocale() throws EnduranceTrioException {
    when(kitClient.createOrUpdateSubscriber(dto)).thenReturn(SUBSCRIBER_ID);

    underTest.subscribe(dto, "pt");

    verify(kitClient).createOrUpdateSubscriber(dto);
    verify(kitClient).addSubscriberToForm(SUBSCRIBER_ID, FORM_ID_PT);
    verifyNoMoreInteractions(kitClient);
  }

  @Test
  void apiFailurePropagatesException() throws EnduranceTrioException {
    doThrow(EnduranceTrioException.class).when(kitClient).createOrUpdateSubscriber(dto);

    assertThrows(EnduranceTrioException.class, () -> underTest.subscribe(dto, "en"));
  }
}
