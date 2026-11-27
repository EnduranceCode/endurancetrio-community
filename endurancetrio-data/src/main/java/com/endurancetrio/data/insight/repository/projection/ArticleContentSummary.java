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

package com.endurancetrio.data.insight.repository.projection;

import com.endurancetrio.data.insight.model.entity.Article;
import com.endurancetrio.data.insight.model.entity.ArticleContent;
import java.time.LocalDateTime;

/**
 * A single article-content pair used by the athlete profile article cards. It carries only the
 * columns rendered by the card and never hydrates the {@link Article}
 * or {@link ArticleContent} entities.
 * <p>
 * Unlike the interface projections used by the other repositories, this row is a value object
 * consumed by the business layer and constructed directly in unit tests, so it is modelled as a
 * record.
 *
 * @param articleId     the ID of the article
 * @param locale        the locale of the content
 * @param title         the article title in the content locale
 * @param introText     the introductory HTML content
 * @param slug          the unique URL slug of the article
 * @param featuredImage the optional URL of the featured image
 * @param publishedDate the optional publication date of the article
 */
public record ArticleContentSummary(
    Long articleId,
    String locale,
    String title,
    String introText,
    String slug,
    String featuredImage,
    LocalDateTime publishedDate
) {

}
