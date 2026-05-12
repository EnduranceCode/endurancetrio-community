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

package com.endurancetrio.app.common.web;

import com.endurancetrio.app.common.model.PageMetadata;
import com.endurancetrio.app.common.service.MessageService;
import com.endurancetrio.app.common.utils.PageMetadataUtils;
import com.endurancetrio.app.config.AppProperties;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PolicyWebController {

  private static final String VIEW = "privacy-policy";
  private static final Locale PORTUGUESE_LOCALE = Locale.of("pt", "PT");

  private final MessageService messageService;
  private final AppProperties appProperties;

  @Autowired
  public PolicyWebController(MessageService messageService, AppProperties appProperties) {
    this.messageService = messageService;
    this.appProperties = appProperties;
  }

  @GetMapping("/{language:en|pt}/privacy-policy")
  public String policy(@PathVariable String language, HttpServletRequest request, Model model) {
    Locale locale = "pt".equalsIgnoreCase(language) ? PORTUGUESE_LOCALE : Locale.ENGLISH;

    PageMetadata metadata = PageMetadataUtils.create(VIEW,
        messageService.getMessage("page.policy.metadata.title", null, locale),
        messageService.getMessage("page.policy.metadata.description", null, locale), request,
        appProperties
    );

    model.addAttribute("language", locale.getLanguage());
    model.addAttribute("metadata", metadata);

    return VIEW;
  }
}
