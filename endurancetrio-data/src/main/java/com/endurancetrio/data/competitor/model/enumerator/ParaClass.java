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
 * The {@link ParaClass} enum represents sport classifications for multiple Paralympic disciplines.
 * <p>
 * The constants are grouped by sport using the following prefixes:
 * <dl>
 *   <dt>{@code AQ_}</dt><dd>World Para Aquatics (open water swimming)</dd>
 *   <dt>{@code UC_}</dt><dd>UCI Para Cycling (road events)</dd>
 *   <dt>{@code AT_}</dt><dd>World Para Athletics (road running)</dd>
 *   <dt>{@code WT_}</dt><dd>World Triathlon (paratriathlon)</dd>
 * </dl>
 * Each constant includes a persistence code, an English name, and a European Portuguese name.
 * The code is used for persistence and API interchange, while the full names are available for
 * display purposes in their respective languages.
 */
public enum ParaClass {

  // World Para Aquatics (Open Water Swimming)
  // -----------------------------------------------------------------------------------------------

  /**
   * Open water swimming class for athletes with the most severe physical impairment, affecting all
   * four limbs with minimal movement.
   */
  AQ_S1("S1", "Open Water, Minimal Limb Movement", "Águas Abertas, Movimento Mínimo dos Membros"),

  /**
   * Open water swimming class for athletes with a severe physical impairment, primarily using arm
   * movement with limited leg and trunk function.
   */
  AQ_S2("S2", "Open Water, Primarily Arms", "Águas Abertas, Maioritariamente Braços"),

  /**
   * Open water swimming class for athletes with a severe physical impairment, able to use arms and
   * slight leg movement.
   */
  AQ_S3("S3", "Open Water, Arms and Slight Legs", "Águas Abertas, Braços e Ligeiras Pernas"),

  /**
   * Open water swimming class for athletes with a moderate to severe physical impairment, using
   * arms and moderate leg and trunk function.
   */
  AQ_S4("S4", "Open Water, Moderate to Severe Impairment",
      "Águas Abertas, Deficiência Moderada a Grave"
  ),

  /**
   * Open water swimming class for athletes with a moderate physical impairment, typically using
   * arms and trunk with limited leg propulsion.
   */
  AQ_S5("S5", "Open Water, Arms and Trunk with Limited Legs",
      "Águas Abertas, Braços e Tronco com Pernas Limitadas"
  ),

  /**
   * Open water swimming class for athletes with a moderate physical impairment, with better trunk
   * and leg function than S5.
   */
  AQ_S6("S6", "Open Water, Better Trunk and Leg Function",
      "Águas Abertas, Melhor Função de Tronco e Pernas"
  ),

  /**
   * Open water swimming class for athletes with a moderate to mild physical impairment, with
   * functional use of arms, trunk, and legs.
   */
  AQ_S7("S7", "Open Water, Functional Arms, Trunk and Legs",
      "Águas Abertas, Braços, Tronco e Pernas Funcionais"
  ),

  /**
   * Open water swimming class for athletes with a mild physical impairment, such as a single
   * below-knee amputation or comparable limitation.
   */
  AQ_S8("S8", "Open Water, Single Below-Knee", "Águas Abertas, Abaixo do Joelho (Simples)"),

  /**
   * Open water swimming class for athletes with a mild physical impairment, such as a single
   * below-elbow amputation or comparable limitation.
   */
  AQ_S9("S9", "Open Water, Single Below-Elbow", "Águas Abertas, Abaixo do Cotovelo (Simples)"),

  /**
   * Open water swimming class for athletes with the least severe physical impairment, such as minor
   * loss of function in one joint.
   */
  AQ_S10("S10", "Open Water, Minimal Physical Impairment",
      "Águas Abertas, Deficiência Física Mínima"
  ),

  /**
   * Open water swimming class for athletes who are totally blind.
   * <p>
   * Athletes require a tapper (person who taps them at the turn and finish walls).
   */
  AQ_S11("S11", "Open Water, Blind", "Águas Abertas, Cegueira"),

  /**
   * Open water swimming class for athletes with severe vision impairment.
   * <p>
   * Athletes typically have a visual acuity of LogMAR 1.50 to 2.60.
   */
  AQ_S12("S12", "Open Water, Severe Vision Impairment", "Águas Abertas, Deficiência Visual Grave"),

  /**
   * Open water swimming class for athletes with moderate vision impairment.
   * <p>
   * Athletes typically have a visual acuity of LogMAR 1.00 to 1.40.
   */
  AQ_S13("S13", "Open Water, Moderate Vision Impairment",
      "Águas Abertas, Deficiência Visual Moderada"
  ),

  /**
   * Open water swimming class for athletes with an intellectual impairment.
   * <p>
   * Requires to be documented IQ ≤ 75 with significant limitations in adaptive behavior.
   */
  AQ_S14("S14", "Open Water, Intellectual Impairment", "Águas Abertas, Deficiência Intelectual"),

  // UCI Para Cycling (Road)
  // -----------------------------------------------------------------------------------------------

  /**
   * Road cycling class for athletes with the most severe impairment affecting all four limbs, using
   * a standard bicycle with adaptations.
   */
  UC_C1("C1", "Road Cycling, Severe Physical Impairment",
      "Ciclismo de Estrada, Deficiência Física Grave"
  ),

  /**
   * Road cycling class for athletes with a severe impairment affecting lower or upper limbs, using
   * a standard bicycle with adaptations.
   */
  UC_C2("C2", "Road Cycling, Moderate to Severe Impairment",
      "Ciclismo de Estrada, Deficiência Moderada a Grave"
  ),

  /**
   * Road cycling class for athletes with a moderate impairment, such as a single above-knee
   * amputation, using a standard bicycle with adaptations.
   */
  UC_C3("C3", "Road Cycling, Moderate Impairment", "Ciclismo de Estrada, Deficiência Moderada"),

  /**
   * Road cycling class for athletes with a mild impairment, such as a single below-knee amputation,
   * using a standard bicycle with adaptations.
   */
  UC_C4("C4", "Road Cycling, Mild Impairment", "Ciclismo de Estrada, Deficiência Leve"),

  /**
   * Road cycling class for athletes with the least severe impairment, such as a minor loss of hand
   * or foot function, using a standard bicycle.
   */
  UC_C5("C5", "Road Cycling, Minimal Impairment", "Ciclismo de Estrada, Deficiência Mínima"),

  /**
   * Road handcycle class for athletes with tetraplegia at C1–C6 level or comparable impairment,
   * using a recumbent handcycle operated primarily with arm movement.
   */
  UC_H1("H1", "Road Handcycle, Tetraplegia C1-C6", "Handcycle de Estrada, Tetraplegia C1-C6"),

  /**
   * Road handcycle class for athletes with tetraplegia at C7–T3 level or comparable impairment,
   * using a recumbent handcycle with arm and limited trunk function.
   */
  UC_H2("H2", "Road Handcycle, Tetraplegia C7-T3", "Handcycle de Estrada, Tetraplegia C7-T3"),

  /**
   * Road handcycle class for athletes with paraplegia at T4–T10 level or comparable impairment,
   * using a recumbent handcycle with full arm function.
   */
  UC_H3("H3", "Road Handcycle, Paraplegia T4-T10", "Handcycle de Estrada, Paraplegia T4-T10"),

  /**
   * Road handcycle class for athletes with paraplegia at T11–S5 level or comparable impairment,
   * using a recumbent handcycle with full arm and partial trunk function.
   */
  UC_H4("H4", "Road Handcycle, Paraplegia T11-S5", "Handcycle de Estrada, Paraplegia T11-S5"),

  /**
   * Road handcycle class for athletes with lower limb impairments who ride in a kneeling position
   * on a handcycle, typically for those with bilateral above-knee amputations.
   */
  UC_H5("H5", "Road Handcycle, Kneeling Position", "Handcycle de Estrada, Posição de Joelhos"),

  /**
   * Road tricycle class for athletes with a severe coordination impairment affecting all four
   * limbs, requiring a three-wheeled tricycle for stability.
   */
  UC_T1("T1", "Road Tricycle, Severe Coordination Impairment",
      "Triciclo de Estrada, Deficiência Coordenação Grave"
  ),

  /**
   * Road tricycle class for athletes with a moderate coordination impairment, using a three-wheeled
   * tricycle for stability.
   */
  UC_T2("T2", "Road Tricycle, Moderate Coordination Impairment",
      "Triciclo de Estrada, Deficiência Coordenação Moderada"
  ),

  /**
   * Road tandem cycling class for athletes with a vision impairment, without further subclass
   * distinction.
   * <p>
   * Competes on a tandem bicycle with a sighted pilot.
   */
  UC_B("B", "Road Tandem, Visually Impaired", "Tandem de Estrada, Deficiência Visual"),

  /**
   * Road tandem cycling class for athletes who are totally blind.
   * <p>
   * Corresponds to a B1 visual acuity of less than LogMAR 2.60.
   */
  UC_B1("B1", "Road Tandem, Blind", "Tandem de Estrada, Cegueira"),

  /**
   * Road tandem cycling class for athletes with severe partial sight.
   * <p>
   * Corresponds to a B2 visual acuity ranging from LogMAR 1.50 to 2.60.
   */
  UC_B2("B2", "Road Tandem, Severe Vision Impairment",
      "Tandem de Estrada, Deficiência Visual Grave"
  ),

  /**
   * Road tandem cycling class for athletes with moderate partial sight.
   * <p>
   * Corresponds to a B3 visual acuity ranging from LogMAR 1.00 to 1.40.
   */
  UC_B3("B3", "Road Tandem, Moderate Vision Impairment",
      "Tandem de Estrada, Deficiência Visual Moderada"
  ),

  // World Para Athletics (Road Running)
  // -----------------------------------------------------------------------------------------------

  /**
   * Road running class for athletes with a vision impairment classified as B1.
   * <p>
   * Athletes are totally blind and must run with a guide.
   */
  AT_T11("T11", "Road, Vision Impairment B1", "Estrada, Deficiência Visual B1"),

  /**
   * Road running class for athletes with a vision impairment classified as B2.
   * <p>
   * Athletes have severe partial sight and may run with a guide.
   */
  AT_T12("T12", "Road, Vision Impairment B2", "Estrada, Deficiência Visual B2"),

  /**
   * Road running class for athletes with a vision impairment classified as B3.
   * <p>
   * Athletes have moderate partial sight and run without a guide.
   */
  AT_T13("T13", "Road, Vision Impairment B3", "Estrada, Deficiência Visual B3"),

  /**
   * Road running class for athletes with an intellectual impairment.
   * <p>
   * Requires to be documented IQ ≤ 75 with significant limitations in adaptive behavior.
   */
  AT_T20("T20", "Road, Intellectual Impairment", "Estrada, Deficiência Intelectual"),

  /**
   * Road running class for ambulant athletes with a coordination impairment (hypertonia, ataxia, or
   * athetosis) affecting lower limbs, most severe in this group.
   */
  AT_T35("T35", "Road, Coordination Most Severe", "Estrada, Coordenação Mais Grave"),

  /**
   * Road running class for ambulant athletes with a coordination impairment (hypertonia, ataxia, or
   * athetosis) affecting all four limbs.
   */
  AT_T36("T36", "Road, Coordination Quadruple", "Estrada, Coordenação Quádrupla"),

  /**
   * Road running class for ambulant athletes with a coordination impairment (hemiplegia) affecting
   * one side of the body.
   */
  AT_T37("T37", "Road, Coordination Hemiplegic", "Estrada, Coordenação Hemiplegia"),

  /**
   * Road running class for ambulant athletes with a coordination impairment (hypertonia, ataxia, or
   * athetosis) affecting one or both limbs, least severe in this group.
   */
  AT_T38("T38", "Road, Coordination Least Severe", "Estrada, Coordenação Menos Grave"),

  /**
   * Road running class for athletes of short stature (dwarfism).
   * <p>
   * Covers the most severe short stature conditions.
   */
  AT_T40("T40", "Road, Short Stature Severe", "Estrada, Baixa Estatura Grave"),

  /**
   * Road running class for athletes of short stature (dwarfism).
   * <p>
   * Covers less severe short stature conditions than T40.
   */
  AT_T41("T41", "Road, Short Stature Less Severe", "Estrada, Baixa Estatura Menos Grave"),

  /**
   * Road running class for athletes with a lower limb impairment who compete without a prosthesis.
   * <p>
   * Covers single above-knee amputation or comparable impairment.
   */
  AT_T42("T42", "Road, Lower Limb Single Above-Knee",
      "Estrada, Membro Inferior Acima do Joelho (Simples)"
  ),

  /**
   * Road running class for athletes with a lower limb impairment who compete without a prosthesis.
   * <p>
   * Covers double below-knee amputation or comparable impairment.
   */
  AT_T43("T43", "Road, Lower Limb Double Below-Knee",
      "Estrada, Membros Inferiores Abaixo do Joelho (Duplo)"
  ),

  /**
   * Road running class for athletes with a lower limb impairment who compete without a prosthesis.
   * <p>
   * Covers single below-knee amputation or comparable impairment.
   */
  AT_T44("T44", "Road, Lower Limb Single Below-Knee",
      "Estrada, Membro Inferior Abaixo do Joelho (Simples)"
  ),

  /**
   * Road running class for athletes with an upper limb impairment affecting both arms.
   */
  AT_T45("T45", "Road, Upper Limb Bilateral", "Estrada, Membro Superior Bilateral"),

  /**
   * Road running class for athletes with an upper limb impairment affecting one arm.
   */
  AT_T46("T46", "Road, Upper Limb Unilateral", "Estrada, Membro Superior Unilateral"),

  /**
   * Road running class for athletes with an upper limb impairment (combined single-arm category).
   * <p>
   * Replaces the former T45 and T46 subclasses in current classification.
   */
  AT_T47("T47", "Road, Upper Limb Combined", "Estrada, Membro Superior Combinado"),

  /**
   * Wheelchair road racing class for athletes with the most severe impairments, typically involving
   * reduced shoulder, arm, and hand function.
   */
  AT_T51("T51", "Road, Wheelchair Minimal Trunk Function",
      "Estrada, Cadeira de Rodas Função Mínima de Tronco"
  ),

  /**
   * Wheelchair road racing class for athletes with moderate to severe impairment affecting
   * shoulder, arm, and hand function.
   */
  AT_T52("T52", "Road, Wheelchair Limited Trunk Function",
      "Estrada, Cadeira de Rodas Função Limitada de Tronco"
  ),

  /**
   * Wheelchair road racing class for athletes with good shoulder and arm function but limited trunk
   * and leg function.
   */
  AT_T53("T53", "Road, Wheelchair Good Arm Function",
      "Estrada, Cadeira de Rodas Boa Função dos Braços"
  ),

  /**
   * Wheelchair road racing class for athletes with full shoulder, arm, and trunk function,
   * typically with some leg function.
   */
  AT_T54("T54", "Road, Wheelchair Full Trunk Function",
      "Estrada, Cadeira de Rodas Função Completa do Tronco"
  ),

  /**
   * Road running class for athletes with a lower limb impairment who compete with a prosthesis,
   * single above-knee.
   */
  AT_T61("T61", "Road, Prosthesis Single Above-Knee",
      "Estrada, Prótese Acima do Joelho (Simples)"
  ),

  /**
   * Road running class for athletes with a lower limb impairment who compete with a prosthesis,
   * double below-knee.
   */
  AT_T62("T62", "Road, Prosthesis Double Below-Knee",
      "Estrada, Prótese Abaixo do Joelho (Dupla)"
  ),

  /**
   * Road running class for athletes with a lower limb impairment who compete with a prosthesis,
   * single below-knee.
   */
  AT_T63("T63", "Road, Prosthesis Single Below-Knee",
      "Estrada, Prótese Abaixo do Joelho (Simples)"
  ),

  /**
   * Road running class for athletes with a lower limb impairment who compete with a prosthesis,
   * single below-knee or comparable, least severe in this group.
   */
  AT_T64("T64", "Road, Prosthesis Least Severe", "Estrada, Prótese Menos Grave"),

  /**
   * Road running class for athletes who compete using a frame runner (three-wheel support frame),
   * most severe impairment in this group.
   */
  AT_T71("T71", "Road, Frame Running More Severe", "Estrada, Corrida com Apoio Mais Grave"),

  /**
   * Road running class for athletes who compete using a frame runner (three-wheel support frame),
   * less severe impairment in this group.
   */
  AT_T72("T72", "Road, Frame Running Less Severe", "Estrada, Corrida com Apoio Menos Grave"),

  // World Triathlon (Paratriathlon)
  // -----------------------------------------------------------------------------------------------

  /**
   * Paratriathlon class for athletes with an intellectual impairment.
   * <p>
   * Covers athletes with an IQ ≤ 75 and significant limitations in adaptive behavior. Used by the
   * Portuguese Triathlon Federation for national competitions.
   */
  WT_PTII("PTII", "Intellectual Impairment", "Deficiência Intelectual"),

  /**
   * Paratriathlon class for athletes with a hearing impairment.
   * <p>
   * Used by the Portuguese Triathlon Federation for national competitions.
   */
  WT_PTHI("PTHI", "Hearing Impairment", "Deficiência Auditiva"),

  /**
   * Paratriathlon class for athletes with a severe impairment in at least two of the three
   * disciplines (swim, bike, run).
   * <p>
   * Typically includes wheelchair users on the bike (handcycle) and run (racing wheelchair).
   */
  WT_PTS2("PTS2", "Severe Impairment", "Deficiência Grave"),

  /**
   * Paratriathlon class for athletes with a significant impairment affecting one or more
   * disciplines.
   * <p>
   * May use a handcycle for the bike leg and/or a wheelchair for the run leg.
   */
  WT_PTS3("PTS3", "Significant Impairment", "Deficiência Significativa"),

  /**
   * Paratriathlon class for athletes with a moderate impairment in one or more disciplines.
   * <p>
   * Ambulant athletes who may use prosthetics or other assistive devices.
   */
  WT_PTS4("PTS4", "Moderate Impairment", "Deficiência Moderada"),

  /**
   * Paratriathlon class for athletes with a mild impairment in one or more disciplines.
   * <p>
   * Ambulant athletes with the least severe physical impairment in this category.
   */
  WT_PTS5("PTS5", "Mild Impairment", "Deficiência Leve"),

  /**
   * Paratriathlon class for athletes who compete using a wheelchair in the run segment and a
   * handcycle in the bike segment, without further subclass distinction.
   */
  WT_PTWC("PTWC", "Wheelchair User", "Utilizador de Cadeira de Rodas"),

  /**
   * Paratriathlon wheelchair class for athletes with the most severe impairments, including
   * tetraplegia and comparable disabilities.
   */
  WT_PTWC1("PTWC1", "Wheelchair, Severe Impairment", "Cadeira de Rodas, Deficiência Grave"),

  /**
   * Paratriathlon wheelchair class for athletes with less severe impairments compared to PTWC1,
   * including paraplegia and comparable disabilities.
   */
  WT_PTWC2("PTWC2", "Wheelchair, Moderate Impairment", "Cadeira de Rodas, Deficiência Moderada"),

  /**
   * Paratriathlon class for athletes with a vision impairment, without further subclass
   * distinction.
   * <p>
   * Competes with a guide of the same sex and uses a tether during the swim and run legs.
   */
  WT_PTVI("PTVI", "Vision Impaired", "Deficiência Visual"),

  /**
   * Paratriathlon vision impairment class for athletes who are totally blind.
   * <p>
   * Corresponds to a B1 visual acuity of less than LogMAR 2.60.
   */
  WT_PTVI1("PTVI1", "Totally Blind", "Cegueira Total"),

  /**
   * Paratriathlon vision impairment class for athletes with severe partial sight.
   * <p>
   * Corresponds to a B2 visual acuity ranging from LogMAR 1.50 to 2.60.
   */
  WT_PTVI2("PTVI2", "Severely Partially Sighted", "Deficiência Visual Parcial Grave"),

  /**
   * Paratriathlon vision impairment class for athletes with moderate partial sight.
   * <p>
   * Corresponds to a B3 visual acuity ranging from LogMAR 1.00 to 1.40.
   */
  WT_PTVI3("PTVI3", "Moderately Partially Sighted", "Deficiência Visual Parcial Moderada");

  private final String code;
  private final String denominationEN;
  private final String denominationPT;

  ParaClass(String code, String denominationEN, String denominationPT) {
    this.code = code;
    this.denominationEN = denominationEN;
    this.denominationPT = denominationPT;
  }

  /**
   * Returns the persistence code of the para class (e.g., {@code "PTVI"}).
   * <p>
   * This value is used for persistence and API interchange.
   *
   * @return the para class code
   */
  public String getCode() {
    return code;
  }

  /**
   * Returns the English name of the para class (e.g., {@code "Vision Impaired"}).
   *
   * @return the English para class name
   */
  public String getDenominationEN() {
    return denominationEN;
  }

  /**
   * Returns the European Portuguese name of the para class (e.g., {@code "Deficiência Visual"}).
   *
   * @return the Portuguese para class name
   */
  public String getNamePT() {
    return denominationPT;
  }

  @Override
  public String toString() {
    return code;
  }
}
