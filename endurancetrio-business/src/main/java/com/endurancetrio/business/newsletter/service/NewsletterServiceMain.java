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

import com.endurancetrio.business.common.exception.EnduranceTrioException;
import com.endurancetrio.business.newsletter.client.KitClient;
import com.endurancetrio.business.newsletter.dto.NewsletterSubscriptionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Kit.com-backed implementation of {@link NewsletterService}.
 * <p>
 * Orchestrates the two-step Kit.com subscription flow:
 * <ol>
 *   <li>Create or update the subscriber via {@link KitClient#createOrUpdateSubscriber}.</li>
 *   <li>Add the subscriber to the language-specific form via
 *       {@link KitClient#addSubscriberToForm}, triggering the double opt-in e-mail configured on
 *       that form in Kit.com.</li>
 * </ol>
 * Outbound HTTP calls are delegated to {@link KitClient} so this module remains free of
 * web/HTTP dependencies.
 */
@Service
public class NewsletterServiceMain implements NewsletterService {

  private static final String LANG_PT = "pt";

  private final KitClient kitClient;
  private final String formIdEn;
  private final String formIdPt;

  @Autowired
  public NewsletterServiceMain(
      KitClient kitClient, @Value("${kit.form-id-en}") String formIdEn,
      @Value("${kit.form-id-pt}") String formIdPt
  ) {
    this.kitClient = kitClient;
    this.formIdEn = formIdEn;
    this.formIdPt = formIdPt;
  }

  @Override
  public void subscribe(NewsletterSubscriptionDTO dto, String language)
      throws EnduranceTrioException {
    Long subscriberId = kitClient.createOrUpdateSubscriber(dto);
    kitClient.addSubscriberToForm(subscriberId, resolveFormId(language));
  }

  private String resolveFormId(String language) {
    return LANG_PT.equalsIgnoreCase(language) ? formIdPt : formIdEn;
  }
}
