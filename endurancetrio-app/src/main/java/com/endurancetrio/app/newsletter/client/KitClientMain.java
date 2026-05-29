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

package com.endurancetrio.app.newsletter.client;

import com.endurancetrio.app.config.KitProperties;
import com.endurancetrio.business.common.dto.ErrorDTO;
import com.endurancetrio.business.common.exception.EnduranceTrioError;
import com.endurancetrio.business.common.exception.EnduranceTrioException;
import com.endurancetrio.business.newsletter.client.KitClient;
import com.endurancetrio.business.newsletter.dto.NewsletterSubscriptionDTO;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

/**
 * {@link RestClient}-backed implementation of {@link KitClient}.
 * <p>
 * Performs the two Kit.com v4 API calls needed for newsletter subscription. Lives in
 * {@code endurancetrio-app} so the business module remains free of web/HTTP dependencies.
 */
@Component
public class KitClientMain implements KitClient {

  private static final Logger LOG = LoggerFactory.getLogger(KitClientMain.class);

  private final KitProperties kitProperties;
  private final RestClient restClient;

  @Autowired
  public KitClientMain(KitProperties kitProperties) {
    this.kitProperties = kitProperties;
    this.restClient = RestClient.builder().baseUrl(kitProperties.getApiBaseUrl()).build();
  }

  @Override
  public void addSubscriberToForm(Long subscriberId, String formId) throws EnduranceTrioException {
    try {
      restClient.post()
          .uri("/" + kitProperties.getApiVersion() + kitProperties.getPathFormSubscribers(), formId,
              subscriberId
          )
          .header(kitProperties.getApiKeyHeader(), kitProperties.getApiKey())
          .contentType(MediaType.APPLICATION_JSON)
          .retrieve()
          .onStatus(HttpStatusCode::isError, (req, res) -> {
                LOG.error("Kit.com add-to-form returned HTTP {} for subscriber {} / form {}",
                    res.getStatusCode(), subscriberId, formId
                );
                throw new RestClientException("Add subscriber to form failed: " + res.getStatusCode());
              }
          )
          .toBodilessEntity();
    } catch (RestClientException exception) {
      LOG.error("Kit.com add-to-form call failed for subscriber {} / form {}: {}", subscriberId,
          formId, exception.getMessage()
      );
      throw new EnduranceTrioException(List.of(new ErrorDTO(EnduranceTrioError.INTERNAL_ERROR,
          "Add subscriber to form failed: " + exception.getMessage()
      )), exception
      );
    }
  }

  @Override
  public Long createOrUpdateSubscriber(NewsletterSubscriptionDTO dto)
      throws EnduranceTrioException {
    Map<String, String> requestBody = Map.of("first_name", dto.firstName(), "email_address",
        dto.email()
    );

    try {
      KitSubscriberResponse response = restClient.post()
          .uri("/" + kitProperties.getApiVersion() + kitProperties.getPathSubscribers())
          .header(kitProperties.getApiKeyHeader(), kitProperties.getApiKey())
          .contentType(MediaType.APPLICATION_JSON)
          .body(requestBody)
          .retrieve()
          .onStatus(HttpStatusCode::isError, (req, res) -> {
                LOG.error("Kit.com create-subscriber returned HTTP {}", res.getStatusCode());
                throw new RestClientException("Create subscriber failed: " + res.getStatusCode());
              }
          )
          .body(KitSubscriberResponse.class);

      if (response == null || response.subscriber() == null) {
        LOG.error("Kit.com create-subscriber returned empty body for email {}", dto.email());
        throw new EnduranceTrioException(new ErrorDTO(EnduranceTrioError.INTERNAL_ERROR,
            "Kit.com returned empty response body"
        ));
      }

      return response.subscriber().id();
    } catch (RestClientException e) {
      LOG.error("Kit.com create-subscriber call failed for email {}: {}", dto.email(),
          e.getMessage()
      );
      throw new EnduranceTrioException(List.of(new ErrorDTO(EnduranceTrioError.INTERNAL_ERROR,
          "Create subscriber failed: " + e.getMessage()
      )), e
      );
    }
  }

  private record KitSubscriberResponse(KitSubscriber subscriber) {

  }

  private record KitSubscriber(Long id) {

  }
}
