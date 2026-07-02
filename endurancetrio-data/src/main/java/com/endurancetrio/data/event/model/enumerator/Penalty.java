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

package com.endurancetrio.data.event.model.enumerator;

/**
 * The {@link Penalty} enum represents the possible penalty codes applied to an athlete's result.
 * <p>
 * The declaration order of constants is significant: it defines the sorting priority used when
 * ranking results with the same rank. {@link Enum#ordinal()} is used as the secondary sort key
 * in {@code RaceServiceMain}, meaning the first constant (DNF) has the highest priority and the
 * last (EC) has the lowest. Any reordering of constants may affect race result ordering.
 */
public enum Penalty {
  DNF("DNF", "Did Not Finish", "Não Completou"),
  LAP("LAP", "Lapped", "Ultrapassado"),
  NC("NC", "Not Classified", "Não Classificado"),
  NE("NE", "Not Eligible", "Não Elegível"),
  DSQ("DSQ", "Disqualified", "Desclassificado"),
  DNS("DNS", "Did Not Start", "Não Partiu"),
  EC("EC", "Error Correction", "Correção de Erro");

  private final String code;
  private final String titleEN;
  private final String titlePT;

  Penalty(String code, String titleEN, String titlePT) {
    this.code = code;
    this.titleEN = titleEN;
    this.titlePT = titlePT;
  }

  public String getCode() {
    return code;
  }

  public String getTitleEN() {
    return titleEN;
  }

  public String getTitlePT() {
    return titlePT;
  }

  @Override
  public String toString() {
    return code;
  }
}
