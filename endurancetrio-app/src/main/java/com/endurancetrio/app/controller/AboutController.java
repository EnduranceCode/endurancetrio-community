/*
 * Copyright (c) 2011-2025 Ricardo do Canto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.endurancetrio.app.controller;

import com.endurancetrio.app.component.MessageService;
import com.endurancetrio.app.model.PageMetadata;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AboutController {

  private static final String VIEW = "about";
  private static final Locale PORTUGUESE_LOCALE = new Locale("pt", "PT");

  private final MessageService messageService;

  @Autowired
  public AboutController(MessageService messageService) {
    this.messageService = messageService;
  }

  @GetMapping("/{language}/about")
  public String about(@PathVariable String language, Model model) {
    Locale locale = "pt".equalsIgnoreCase(language) ? PORTUGUESE_LOCALE : Locale.ENGLISH;

    PageMetadata metadata = new PageMetadata();
    metadata.setView(VIEW);
    metadata.setTitle(messageService.getMessage("page.about.metadata.title", null, locale));

    model.addAttribute("language", locale.getLanguage());
    model.addAttribute("metadata", metadata);

    return VIEW;
  }
}
