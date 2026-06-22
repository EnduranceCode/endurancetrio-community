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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import com.endurancetrio.app.config.KitProperties;
import com.endurancetrio.business.common.exception.EnduranceTrioException;
import com.endurancetrio.business.newsletter.dto.NewsletterSubscriptionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestClient;

@ExtendWith(MockitoExtension.class)
class KitClientMainTest {

  private static final String API_BASE_URL = "https://api.kit.com/v4";
  private static final String API_VERSION = "v4";
  private static final String API_KEY = "test-api-key";
  private static final String API_KEY_HEADER = "X-Kit-Api-Key";
  private static final String PATH_SUBSCRIBERS = "/subscribers";
  private static final String PATH_FORM_SUBSCRIBERS = "/forms/{form_id}/subscribers/{subscriber_id}";
  private static final Long SUBSCRIBER_ID = 123L;
  private static final String FORM_ID = "form_abc";
  private static final String FIRST_NAME = "John";
  private static final String EMAIL = "john@example.com";

  @Mock
  private KitProperties kitProperties;

  @Mock
  private RestClient.Builder restClientBuilder;

  @Mock
  private RestClient restClient;

  @Mock
  private RestClient.RequestBodyUriSpec requestBodyUriSpec;

  @Mock
  private RestClient.RequestBodySpec requestBodySpec;

  @Mock
  private RestClient.ResponseSpec responseSpec;

  private KitClientMain underTest;

  @BeforeEach
  void setUp() {
    when(kitProperties.getApiBaseUrl()).thenReturn(API_BASE_URL);
    when(restClientBuilder.baseUrl(API_BASE_URL)).thenReturn(restClientBuilder);
    when(restClientBuilder.build()).thenReturn(restClient);
  }

  @Test
  void createOrUpdateSubscriberShouldReturnId() throws EnduranceTrioException {
    when(kitProperties.getApiVersion()).thenReturn(API_VERSION);
    when(kitProperties.getApiKey()).thenReturn(API_KEY);
    when(kitProperties.getApiKeyHeader()).thenReturn(API_KEY_HEADER);
    when(kitProperties.getPathSubscribers()).thenReturn(PATH_SUBSCRIBERS);

    try (MockedStatic<RestClient> restClientMock = mockStatic(RestClient.class)) {
      restClientMock.when(RestClient::builder).thenReturn(restClientBuilder);
      underTest = new KitClientMain(kitProperties);

      when(restClient.post()).thenReturn(requestBodyUriSpec);
      when(requestBodyUriSpec.uri("/" + API_VERSION + PATH_SUBSCRIBERS)).thenReturn(requestBodySpec);
      when(requestBodySpec.header(API_KEY_HEADER, API_KEY)).thenReturn(requestBodySpec);
      when(requestBodySpec.contentType(MediaType.APPLICATION_JSON)).thenReturn(requestBodySpec);
      doReturn(requestBodySpec).when(requestBodySpec).body(any(Object.class));
      when(requestBodySpec.retrieve()).thenReturn(responseSpec);
      when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
      when(responseSpec.body((Class) any())).thenReturn(createMockResponse(SUBSCRIBER_ID));

      Long result = underTest.createOrUpdateSubscriber(
          new NewsletterSubscriptionDTO(FIRST_NAME, EMAIL));

      assertEquals(SUBSCRIBER_ID, result);
    }
  }

  @Test
  void createOrUpdateSubscriberShouldThrowWhenResponseBodyIsNull() {
    when(kitProperties.getApiVersion()).thenReturn(API_VERSION);
    when(kitProperties.getApiKey()).thenReturn(API_KEY);
    when(kitProperties.getApiKeyHeader()).thenReturn(API_KEY_HEADER);
    when(kitProperties.getPathSubscribers()).thenReturn(PATH_SUBSCRIBERS);

    try (MockedStatic<RestClient> restClientMock = mockStatic(RestClient.class)) {
      restClientMock.when(RestClient::builder).thenReturn(restClientBuilder);
      underTest = new KitClientMain(kitProperties);

      when(restClient.post()).thenReturn(requestBodyUriSpec);
      when(requestBodyUriSpec.uri("/" + API_VERSION + PATH_SUBSCRIBERS)).thenReturn(requestBodySpec);
      when(requestBodySpec.header(API_KEY_HEADER, API_KEY)).thenReturn(requestBodySpec);
      when(requestBodySpec.contentType(MediaType.APPLICATION_JSON)).thenReturn(requestBodySpec);
      doReturn(requestBodySpec).when(requestBodySpec).body(any(Object.class));
      when(requestBodySpec.retrieve()).thenReturn(responseSpec);
      when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
      when(responseSpec.body((Class) any())).thenReturn(null);

      assertThrows(EnduranceTrioException.class,
          () -> underTest.createOrUpdateSubscriber(
              new NewsletterSubscriptionDTO(FIRST_NAME, EMAIL))
      );
    }
  }

  @Test
  void createOrUpdateSubscriberShouldThrowOnRestClientException() {
    when(kitProperties.getApiVersion()).thenReturn(API_VERSION);
    when(kitProperties.getApiKey()).thenReturn(API_KEY);
    when(kitProperties.getApiKeyHeader()).thenReturn(API_KEY_HEADER);
    when(kitProperties.getPathSubscribers()).thenReturn(PATH_SUBSCRIBERS);

    try (MockedStatic<RestClient> restClientMock = mockStatic(RestClient.class)) {
      restClientMock.when(RestClient::builder).thenReturn(restClientBuilder);
      underTest = new KitClientMain(kitProperties);

      when(restClient.post()).thenReturn(requestBodyUriSpec);
      when(requestBodyUriSpec.uri("/" + API_VERSION + PATH_SUBSCRIBERS)).thenReturn(requestBodySpec);
      when(requestBodySpec.header(API_KEY_HEADER, API_KEY)).thenReturn(requestBodySpec);
      when(requestBodySpec.contentType(MediaType.APPLICATION_JSON)).thenReturn(requestBodySpec);
      doReturn(requestBodySpec).when(requestBodySpec).body(any(Object.class));
      when(requestBodySpec.retrieve()).thenThrow(
          new org.springframework.web.client.RestClientException("timeout"));

      assertThrows(EnduranceTrioException.class,
          () -> underTest.createOrUpdateSubscriber(
              new NewsletterSubscriptionDTO(FIRST_NAME, EMAIL))
      );
    }
  }

  @Test
  void createOrUpdateSubscriberShouldThrowOnHttpError() {
    when(kitProperties.getApiVersion()).thenReturn(API_VERSION);
    when(kitProperties.getApiKey()).thenReturn(API_KEY);
    when(kitProperties.getApiKeyHeader()).thenReturn(API_KEY_HEADER);
    when(kitProperties.getPathSubscribers()).thenReturn(PATH_SUBSCRIBERS);

    try (MockedStatic<RestClient> restClientMock = mockStatic(RestClient.class)) {
      restClientMock.when(RestClient::builder).thenReturn(restClientBuilder);
      underTest = new KitClientMain(kitProperties);

      when(restClient.post()).thenReturn(requestBodyUriSpec);
      when(requestBodyUriSpec.uri("/" + API_VERSION + PATH_SUBSCRIBERS)).thenReturn(requestBodySpec);
      when(requestBodySpec.header(API_KEY_HEADER, API_KEY)).thenReturn(requestBodySpec);
      when(requestBodySpec.contentType(MediaType.APPLICATION_JSON)).thenReturn(requestBodySpec);
      doReturn(requestBodySpec).when(requestBodySpec).body(any(Object.class));
      when(requestBodySpec.retrieve()).thenReturn(responseSpec);
      when(responseSpec.onStatus(any(), any())).thenAnswer(invocation -> {
        RestClient.ResponseSpec.ErrorHandler handler = invocation.getArgument(1);
        ClientHttpResponse errorResponse = mock(ClientHttpResponse.class);
        when(errorResponse.getStatusCode()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);
        handler.handle(mock(HttpRequest.class), errorResponse);
        return responseSpec;
      });

      assertThrows(EnduranceTrioException.class,
          () -> underTest.createOrUpdateSubscriber(
              new NewsletterSubscriptionDTO(FIRST_NAME, EMAIL))
      );
    }
  }

  @Test
  void addSubscriberToFormShouldCompleteSuccessfully() throws EnduranceTrioException {
    when(kitProperties.getApiVersion()).thenReturn(API_VERSION);
    when(kitProperties.getApiKey()).thenReturn(API_KEY);
    when(kitProperties.getApiKeyHeader()).thenReturn(API_KEY_HEADER);
    when(kitProperties.getPathFormSubscribers()).thenReturn(PATH_FORM_SUBSCRIBERS);

    try (MockedStatic<RestClient> restClientMock = mockStatic(RestClient.class)) {
      restClientMock.when(RestClient::builder).thenReturn(restClientBuilder);
      underTest = new KitClientMain(kitProperties);

      when(restClient.post()).thenReturn(requestBodyUriSpec);
      when(requestBodyUriSpec.uri(eq("/" + API_VERSION + PATH_FORM_SUBSCRIBERS), eq(FORM_ID),
          eq(SUBSCRIBER_ID)
      )).thenReturn(requestBodySpec);
      when(requestBodySpec.header(API_KEY_HEADER, API_KEY)).thenReturn(requestBodySpec);
      when(requestBodySpec.contentType(MediaType.APPLICATION_JSON)).thenReturn(requestBodySpec);
      when(requestBodySpec.retrieve()).thenReturn(responseSpec);
      when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
      when(responseSpec.toBodilessEntity()).thenReturn(null);

      underTest.addSubscriberToForm(SUBSCRIBER_ID, FORM_ID);
    }
  }

  @Test
  void addSubscriberToFormShouldThrowOnHttpError() {
    when(kitProperties.getApiVersion()).thenReturn(API_VERSION);
    when(kitProperties.getApiKey()).thenReturn(API_KEY);
    when(kitProperties.getApiKeyHeader()).thenReturn(API_KEY_HEADER);
    when(kitProperties.getPathFormSubscribers()).thenReturn(PATH_FORM_SUBSCRIBERS);

    try (MockedStatic<RestClient> restClientMock = mockStatic(RestClient.class)) {
      restClientMock.when(RestClient::builder).thenReturn(restClientBuilder);
      underTest = new KitClientMain(kitProperties);

      when(restClient.post()).thenReturn(requestBodyUriSpec);
      when(requestBodyUriSpec.uri(eq("/" + API_VERSION + PATH_FORM_SUBSCRIBERS), eq(FORM_ID),
          eq(SUBSCRIBER_ID)
      )).thenReturn(requestBodySpec);
      when(requestBodySpec.header(API_KEY_HEADER, API_KEY)).thenReturn(requestBodySpec);
      when(requestBodySpec.contentType(MediaType.APPLICATION_JSON)).thenReturn(requestBodySpec);
      when(requestBodySpec.retrieve()).thenReturn(responseSpec);
      when(responseSpec.onStatus(any(), any())).thenAnswer(invocation -> {
        RestClient.ResponseSpec.ErrorHandler handler = invocation.getArgument(1);
        ClientHttpResponse errorResponse = mock(ClientHttpResponse.class);
        when(errorResponse.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST);
        handler.handle(mock(HttpRequest.class), errorResponse);
        return responseSpec;
      });

      assertThrows(EnduranceTrioException.class,
          () -> underTest.addSubscriberToForm(SUBSCRIBER_ID, FORM_ID)
      );
    }
  }

  @Test
  void addSubscriberToFormShouldThrowOnRestClientException() {
    when(kitProperties.getApiVersion()).thenReturn(API_VERSION);
    when(kitProperties.getApiKey()).thenReturn(API_KEY);
    when(kitProperties.getApiKeyHeader()).thenReturn(API_KEY_HEADER);
    when(kitProperties.getPathFormSubscribers()).thenReturn(PATH_FORM_SUBSCRIBERS);

    try (MockedStatic<RestClient> restClientMock = mockStatic(RestClient.class)) {
      restClientMock.when(RestClient::builder).thenReturn(restClientBuilder);
      underTest = new KitClientMain(kitProperties);

      when(restClient.post()).thenReturn(requestBodyUriSpec);
      when(requestBodyUriSpec.uri(eq("/" + API_VERSION + PATH_FORM_SUBSCRIBERS), eq(FORM_ID),
          eq(SUBSCRIBER_ID)
      )).thenReturn(requestBodySpec);
      when(requestBodySpec.header(API_KEY_HEADER, API_KEY)).thenReturn(requestBodySpec);
      when(requestBodySpec.contentType(MediaType.APPLICATION_JSON)).thenReturn(requestBodySpec);
      when(requestBodySpec.retrieve()).thenThrow(
          new org.springframework.web.client.RestClientException("connection refused"));

      assertThrows(EnduranceTrioException.class,
          () -> underTest.addSubscriberToForm(SUBSCRIBER_ID, FORM_ID)
      );
    }
  }

  private Object createMockResponse(Long subscriberId) {
    try {
      var subscriberClass = Class.forName(
          "com.endurancetrio.app.newsletter.client.KitClientMain$KitSubscriber");
      var responseClass = Class.forName(
          "com.endurancetrio.app.newsletter.client.KitClientMain$KitSubscriberResponse");

      var subscriberCon = subscriberClass.getDeclaredConstructor(Long.class);
      subscriberCon.setAccessible(true);
      Object subscriber = subscriberCon.newInstance(subscriberId);

      var responseCon = responseClass.getDeclaredConstructor(subscriberClass);
      responseCon.setAccessible(true);
      return responseCon.newInstance(subscriber);
    } catch (Exception e) {
      throw new RuntimeException("Failed to create mock response", e);
    }
  }
}
