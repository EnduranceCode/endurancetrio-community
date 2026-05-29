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

package com.endurancetrio.business.newsletter.client;

import com.endurancetrio.business.common.exception.EnduranceTrioException;
import com.endurancetrio.business.newsletter.dto.NewsletterSubscriptionDTO;

/**
 * Port for outbound Kit.com API calls.
 * <p>
 * Implementations live in the {@code endurancetrio-app} module so that the business module remains
 * free of web/HTTP dependencies.
 */
public interface KitClient {

  /**
   * Adds an already-created subscriber to a Kit.com form, triggering the double opt-in e-mail
   * configured on that form.
   *
   * @param subscriberId the Kit.com subscriber ID
   * @param formId       the Kit.com form ID
   * @throws EnduranceTrioException if the API call fails
   */
  void addSubscriberToForm(Long subscriberId, String formId) throws EnduranceTrioException;

  /**
   * Creates or updates a subscriber in Kit.com and returns their subscriber ID.
   *
   * @param dto the subscriber's first name and e-mail address
   * @return the Kit.com subscriber ID
   * @throws EnduranceTrioException if the API call fails
   */
  Long createOrUpdateSubscriber(NewsletterSubscriptionDTO dto) throws EnduranceTrioException;
}
