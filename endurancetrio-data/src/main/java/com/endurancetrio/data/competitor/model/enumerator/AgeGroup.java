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

package com.endurancetrio.data.competitor.model.enumerator;

/**
 * The {@link AgeGroup} enum represents the age group category of a competitor in a race.
 * <p>
 * Each constant includes the persistence code, the English name, and the European Portuguese name.
 * The code is used for persistence and API interchange, while the full names are available for
 * display purposes in their respective languages.
 */
public enum AgeGroup {
  OPEN("OPEN", "Open", "Geral"),
  CAT_A("A", "Category A", "Escalão A"),
  CAT_B("B", "Category B", "Escalão B"),
  CAT_C("C", "Category C", "Escalão C"),
  CAT_D("D", "Category D", "Escalão D"),
  CAT_E("E", "Category E", "Escalão E"),
  CAT_F("F", "Category F", "Escalão F"),
  CAT_F1("F1", "Category F1", "Escalão F1"),
  CAT_F2("F2", "Category F2", "Escalão F2"),
  CAT_F3("F3", "Category F3", "Escalão F3"),
  CAT_F4("F4", "Category F4", "Escalão F4"),
  CAT_M1("M1", "Category M1", "Escalão M1"),
  CAT_M2("M2", "Category M2", "Escalão M2"),
  CAT_M3("M3", "Category M3", "Escalão M3"),
  CAT_M4("M4", "Category M4", "Escalão M4"),
  MINI("MINI", "Mini", "Mini"),
  PRI("PRI", "Novice", "Principiante"),
  BEN("BEN", "Benjamin", "Benjamin"),
  INF("INF", "Infant", "Infantil"),
  INI("INI", "Initiate", "Iniciado"),
  JUV("JUV", "Juvenile", "Juvenil"),
  A1("A1", "Group 1", "Agrupamento 1"),
  A2("A2", "Group 2", "Agrupamento 2"),
  CAD("CAD", "Cadet", "Cadete"),
  JUN("JUN", "Junior", "Junior"),
  YOUTH("YOUTH", "Youth", "Youth"),
  U23("U23", "Under 23", "Sub-23"),
  ELITE("ELITE", "Elite", "Elite"),
  SEN("SEN", "Senior", "Sénior"),
  VET("VET", "Veteran", "Veterano"),
  VET_V1("V1", "Veteran I", "Veterano I"),
  VET_V2("V2", "Veteran II", "Veterano II"),
  VET_V3("V3", "Veteran III", "Veterano III"),
  VET_V4("V4", "Veteran IV", "Veterano IV"),
  VET_V5("V5", "Veteran V", "Veterano V"),
  AG_16_17("16-17", "Age Group 16-17", "Grupo de Idade 16-17"),
  AG_18_19("18-19", "Age Group 18-19", "Grupo de Idade 18-19"),
  AG_20_24("20-24", "Age Group 20-24", "Grupo de Idade 20-24"),
  AG_25_29("25-29", "Age Group 25-29", "Grupo de Idade 25-29"),
  AG_30_34("30-34", "Age Group 30-34", "Grupo de Idade 30-34"),
  AG_35_39("35-39", "Age Group 35-39", "Grupo de Idade 35-39"),
  AG_40_44("40-44", "Age Group 40-44", "Grupo de Idade 40-44"),
  AG_45_49("45-49", "Age Group 45-49", "Grupo de Idade 45-49"),
  AG_50_54("50-54", "Age Group 50-54", "Grupo de Idade 50-54"),
  AG_55_59("55-59", "Age Group 55-59", "Grupo de Idade 55-59"),
  AG_60_64("60-64", "Age Group 60-64", "Grupo de Idade 60-64"),
  AG_65_69("65-69", "Age Group 65-69", "Grupo de Idade 65-69"),
  AG_70_74("70-74", "Age Group 70-74", "Grupo de Idade 70-74"),
  AG_75_79("75-79", "Age Group 75-79", "Grupo de Idade 75-79"),
  AG_80_84("80-84", "Age Group 80-84", "Grupo de Idade 80-84"),
  AG_85_89("85-89", "Age Group 85-89", "Grupo de Idade 85-89"),
  AG_90_94("90-94", "Age Group 90-94", "Grupo de Idade 90-94"),
  AG_95_99("95-99", "Age Group 95-99", "Grupo de Idade 95-99");

  private final String code;
  private final String denominationEN;
  private final String denominationPT;

  AgeGroup(String code, String denominationEN, String denominationPT) {
    this.code = code;
    this.denominationEN = denominationEN;
    this.denominationPT = denominationPT;
  }

  /**
   * Returns the persistence code of the age group (e.g., {@code "OPEN"}).
   * <p>
   * This value is used for persistence and API interchange.
   *
   * @return the age group code
   */
  public String getCode() {
    return code;
  }

  /**
   * Returns the English name of the age group (e.g., {@code "Open"}).
   *
   * @return the English age group name
   */
  public String getDenominationEN() {
    return denominationEN;
  }

  /**
   * Returns the European Portuguese name of the age group (e.g., {@code "Geral"}).
   *
   * @return the Portuguese age group name
   */
  public String getDenominationPT() {
    return denominationPT;
  }

  @Override
  public String toString() {
    return code;
  }
}
