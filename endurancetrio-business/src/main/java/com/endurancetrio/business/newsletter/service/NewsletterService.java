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
import com.endurancetrio.business.newsletter.dto.NewsletterSubscriptionDTO;

/**
 * Service for managing newsletter subscriptions via Kit.com.
 */
public interface NewsletterService {

  /**
   * Subscribes the given subscriber to the Kit.com form that corresponds to the requested
   * language.
   * <p>
   * The Kit.com v4 API is called in two steps: first the subscriber is created or updated via
   * {@code POST /v4/subscribers}, then the resulting subscriber ID is used to add them to the
   * language-specific form via {@code POST /v4/forms/{form_id}/subscribers/{id}}, which triggers
   * the double opt-in confirmation email configured on that form.
   *
   * @param dto      the subscriber's first name and email address
   * @param language the page language code ({@code "en"} or {@code "pt"}) used to select the
   *                 correct Kit.com form
   * @throws EnduranceTrioException if the subscription fails
   */
  void subscribe(NewsletterSubscriptionDTO dto, String language);
}
