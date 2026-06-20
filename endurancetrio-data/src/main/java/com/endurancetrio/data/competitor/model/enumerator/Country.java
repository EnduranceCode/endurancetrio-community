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
 * The {@link Country} enum represents a country using its National Olympic Committee (NOC) code.
 * <p>
 * Each constant includes the three-letter NOC code, the English full name, and the European
 * Portuguese full name. The NOC code is used for persistence and API interchange, while the full
 * names are available for display purposes in their respective languages.
 */
public enum Country {
  AFG("AFG", "Afghanistan", "Afeganistão"),
  ALB("ALB", "Albania", "Albânia"),
  ALG("ALG", "Algeria", "Argélia"),
  AND("AND", "Andorra", "Andorra"),
  ANG("ANG", "Angola", "Angola"),
  ANT("ANT", "Antigua and Barbuda", "Antígua e Barbuda"),
  ARG("ARG", "Argentina", "Argentina"),
  ARM("ARM", "Armenia", "Arménia"),
  ARU("ARU", "Aruba", "Aruba"),
  ASA("ASA", "American Samoa", "Samoa Americana"),
  AUS("AUS", "Australia", "Austrália"),
  AUT("AUT", "Austria", "Áustria"),
  AZE("AZE", "Azerbaijan", "Azerbaijão"),
  BAH("BAH", "Bahamas", "Bahamas"),
  BAN("BAN", "Bangladesh", "Bangladesh"),
  BAR("BAR", "Barbados", "Barbados"),
  BDI("BDI", "Burundi", "Burundi"),
  BEL("BEL", "Belgium", "Bélgica"),
  BEN("BEN", "Benin", "Benim"),
  BER("BER", "Bermuda", "Bermudas"),
  BHU("BHU", "Bhutan", "Butão"),
  BIH("BIH", "Bosnia and Herzegovina", "Bósnia e Herzegovina"),
  BIZ("BIZ", "Belize", "Belize"),
  BLR("BLR", "Belarus", "Bielorrússia"),
  BOL("BOL", "Bolivia", "Bolívia"),
  BOT("BOT", "Botswana", "Botsuana"),
  BRA("BRA", "Brazil", "Brasil"),
  BRN("BRN", "Bahrain", "Bahrein"),
  BRU("BRU", "Brunei Darussalam", "Brunei"),
  BUL("BUL", "Bulgaria", "Bulgária"),
  BUR("BUR", "Burkina Faso", "Burkina Faso"),
  CAF("CAF", "Central African Republic", "República Centro-Africana"),
  CAM("CAM", "Cambodia", "Camboja"),
  CAN("CAN", "Canada", "Canadá"),
  CAY("CAY", "Cayman Islands", "Ilhas Caimão"),
  CGO("CGO", "Congo", "Congo"),
  CHA("CHA", "Chad", "Chade"),
  CHI("CHI", "Chile", "Chile"),
  CHN("CHN", "China", "China"),
  CIV("CIV", "Côte d'Ivoire", "Costa do Marfim"),
  CMR("CMR", "Cameroon", "Camarões"),
  COD("COD", "Democratic Republic of the Congo", "República Democrática do Congo"),
  COK("COK", "Cook Islands", "Ilhas Cook"),
  COL("COL", "Colombia", "Colômbia"),
  COM("COM", "Comoros", "Comores"),
  CPV("CPV", "Cape Verde", "Cabo Verde"),
  CRC("CRC", "Costa Rica", "Costa Rica"),
  CRO("CRO", "Croatia", "Croácia"),
  CUB("CUB", "Cuba", "Cuba"),
  CYP("CYP", "Cyprus", "Chipre"),
  CZE("CZE", "Czechia", "Chéquia"),
  DEN("DEN", "Denmark", "Dinamarca"),
  DJI("DJI", "Djibouti", "Jibuti"),
  DMA("DMA", "Dominica", "Domínica"),
  DOM("DOM", "Dominican Republic", "República Dominicana"),
  ECU("ECU", "Ecuador", "Equador"),
  EGY("EGY", "Egypt", "Egito"),
  ERI("ERI", "Eritrea", "Eritreia"),
  ESA("ESA", "El Salvador", "El Salvador"),
  ESP("ESP", "Spain", "Espanha"),
  EST("EST", "Estonia", "Estónia"),
  ETH("ETH", "Ethiopia", "Etiópia"),
  FIJ("FIJ", "Fiji", "Fiji"),
  FIN("FIN", "Finland", "Finlândia"),
  FRA("FRA", "France", "França"),
  FSM("FSM", "Federated States of Micronesia", "Estados Federados da Micronésia"),
  GAB("GAB", "Gabon", "Gabão"),
  GAM("GAM", "Gambia", "Gâmbia"),
  GBR("GBR", "Great Britain", "Grã-Bretanha"),
  GBS("GBS", "Guinea-Bissau", "Guiné-Bissau"),
  GEO("GEO", "Georgia", "Geórgia"),
  GEQ("GEQ", "Equatorial Guinea", "Guiné Equatorial"),
  GER("GER", "Germany", "Alemanha"),
  GHA("GHA", "Ghana", "Gana"),
  GRE("GRE", "Greece", "Grécia"),
  GRN("GRN", "Grenada", "Granada"),
  GUA("GUA", "Guatemala", "Guatemala"),
  GUI("GUI", "Guinea", "Guiné"),
  GUM("GUM", "Guam", "Guame"),
  GUY("GUY", "Guyana", "Guiana"),
  HAI("HAI", "Haiti", "Haiti"),
  HKG("HKG", "Hong Kong, China", "Hong Kong, China"),
  HON("HON", "Honduras", "Honduras"),
  HUN("HUN", "Hungary", "Hungria"),
  INA("INA", "Indonesia", "Indonésia"),
  IND("IND", "India", "Índia"),
  IRL("IRL", "Ireland", "Irlanda"),
  IRI("IRI", "Iran", "Irão"),
  IRQ("IRQ", "Iraq", "Iraque"),
  ISL("ISL", "Iceland", "Islândia"),
  ISR("ISR", "Israel", "Israel"),
  ISV("ISV", "Virgin Islands, US", "Ilhas Virgens Americanas"),
  ITA("ITA", "Italy", "Itália"),
  IVB("IVB", "British Virgin Islands", "Ilhas Virgens Britânicas"),
  JAM("JAM", "Jamaica", "Jamaica"),
  JOR("JOR", "Jordan", "Jordânia"),
  JPN("JPN", "Japan", "Japão"),
  KAZ("KAZ", "Kazakhstan", "Cazaquistão"),
  KEN("KEN", "Kenya", "Quénia"),
  KGZ("KGZ", "Kyrgyzstan", "Quirguistão"),
  KIR("KIR", "Kiribati", "Quiribáti"),
  KOR("KOR", "Republic of Korea", "Coreia do Sul"),
  KOS("KOS", "Kosovo", "Kosovo"),
  KSA("KSA", "Saudi Arabia", "Arábia Saudita"),
  KUW("KUW", "Kuwait", "Kuwait"),
  LAO("LAO", "Laos", "Laos"),
  LAT("LAT", "Latvia", "Letónia"),
  LBA("LBA", "Libya", "Líbia"),
  LBN("LBN", "Lebanon", "Líbano"),
  LBR("LBR", "Liberia", "Libéria"),
  LCA("LCA", "Saint Lucia", "Santa Lúcia"),
  LES("LES", "Lesotho", "Lesoto"),
  LIE("LIE", "Liechtenstein", "Liechtenstein"),
  LTU("LTU", "Lithuania", "Lituânia"),
  LUX("LUX", "Luxembourg", "Luxemburgo"),
  MAD("MAD", "Madagascar", "Madagáscar"),
  MAR("MAR", "Morocco", "Marrocos"),
  MAS("MAS", "Malaysia", "Malásia"),
  MAW("MAW", "Malawi", "Maláui"),
  MDA("MDA", "Republic of Moldova", "Moldávia"),
  MDV("MDV", "Maldives", "Maldivas"),
  MEX("MEX", "Mexico", "México"),
  MGL("MGL", "Mongolia", "Mongólia"),
  MHL("MHL", "Marshall Islands", "Ilhas Marshall"),
  MKD("MKD", "North Macedonia", "Macedónia do Norte"),
  MLI("MLI", "Mali", "Mali"),
  MLT("MLT", "Malta", "Malta"),
  MNE("MNE", "Montenegro", "Montenegro"),
  MON("MON", "Monaco", "Mónaco"),
  MOZ("MOZ", "Mozambique", "Moçambique"),
  MRI("MRI", "Mauritius", "Maurícia"),
  MTN("MTN", "Mauritania", "Mauritânia"),
  MYA("MYA", "Myanmar", "Mianmar"),
  NAM("NAM", "Namibia", "Namíbia"),
  NCA("NCA", "Nicaragua", "Nicarágua"),
  NED("NED", "Netherlands", "Países Baixos"),
  NEP("NEP", "Nepal", "Nepal"),
  NGR("NGR", "Nigeria", "Nigéria"),
  NIG("NIG", "Niger", "Níger"),
  NOR("NOR", "Norway", "Noruega"),
  NRU("NRU", "Nauru", "Nauru"),
  NZL("NZL", "New Zealand", "Nova Zelândia"),
  OMA("OMA", "Oman", "Omã"),
  PAK("PAK", "Pakistan", "Paquistão"),
  PAN("PAN", "Panama", "Panamá"),
  PAR("PAR", "Paraguay", "Paraguai"),
  PER("PER", "Peru", "Peru"),
  PHI("PHI", "Philippines", "Filipinas"),
  PLE("PLE", "Palestine", "Palestina"),
  PLW("PLW", "Palau", "Palau"),
  PNG("PNG", "Papua New Guinea", "Papua-Nova Guiné"),
  POL("POL", "Poland", "Polónia"),
  POR("POR", "Portugal", "Portugal"),
  PRK("PRK", "Democratic People's Republic of Korea", "Coreia do Norte"),
  PUR("PUR", "Puerto Rico", "Porto Rico"),
  QAT("QAT", "Qatar", "Catar"),
  ROU("ROU", "Romania", "Roménia"),
  RSA("RSA", "South Africa", "África do Sul"),
  RUS("RUS", "Russia", "Rússia"),
  RWA("RWA", "Rwanda", "Ruanda"),
  SAM("SAM", "Samoa", "Samoa"),
  SEN("SEN", "Senegal", "Senegal"),
  SEY("SEY", "Seychelles", "Seicheles"),
  SGP("SGP", "Singapore", "Singapura"),
  SKN("SKN", "Saint Kitts and Nevis", "São Cristóvão e Neves"),
  SLE("SLE", "Sierra Leone", "Serra Leoa"),
  SLO("SLO", "Slovenia", "Eslovénia"),
  SMR("SMR", "San Marino", "São Marinho"),
  SOL("SOL", "Solomon Islands", "Ilhas Salomão"),
  SOM("SOM", "Somalia", "Somália"),
  SRB("SRB", "Serbia", "Sérvia"),
  SRI("SRI", "Sri Lanka", "Sri Lanka"),
  SSD("SSD", "South Sudan", "Sudão do Sul"),
  STP("STP", "Sao Tome and Principe", "São Tomé e Príncipe"),
  SUD("SUD", "Sudan", "Sudão"),
  SUI("SUI", "Switzerland", "Suíça"),
  SUR("SUR", "Suriname", "Suriname"),
  SVK("SVK", "Slovakia", "Eslováquia"),
  SWE("SWE", "Sweden", "Suécia"),
  SWZ("SWZ", "Eswatini", "Eswatini"),
  SYR("SYR", "Syrian Arab Republic", "Síria"),
  TAN("TAN", "Tanzania", "Tanzânia"),
  TGA("TGA", "Tonga", "Tonga"),
  THA("THA", "Thailand", "Tailândia"),
  TJK("TJK", "Tajikistan", "Tajiquistão"),
  TKM("TKM", "Turkmenistan", "Turquemenistão"),
  TLS("TLS", "Timor-Leste", "Timor-Leste"),
  TOG("TOG", "Togo", "Togo"),
  TPE("TPE", "Chinese Taipei", "Taipé Chinês"),
  TTO("TTO", "Trinidad and Tobago", "Trindade e Tobago"),
  TUN("TUN", "Tunisia", "Tunísia"),
  TUR("TUR", "Türkiye", "Turquia"),
  TUV("TUV", "Tuvalu", "Tuvalu"),
  UAE("UAE", "United Arab Emirates", "Emirados Árabes Unidos"),
  UGA("UGA", "Uganda", "Uganda"),
  UKR("UKR", "Ukraine", "Ucrânia"),
  URU("URU", "Uruguay", "Uruguai"),
  USA("USA", "United States of America", "Estados Unidos da América"),
  UZB("UZB", "Uzbekistan", "Usbequistão"),
  VAN("VAN", "Vanuatu", "Vanuatu"),
  VEN("VEN", "Venezuela", "Venezuela"),
  VIE("VIE", "Vietnam", "Vietname"),
  VIN("VIN", "Saint Vincent and the Grenadines", "São Vicente e Granadinas"),
  YEM("YEM", "Yemen", "Iémen"),
  ZAM("ZAM", "Zambia", "Zâmbia"),
  ZIM("ZIM", "Zimbabwe", "Zimbabué");

  private final String code;
  private final String nameEN;
  private final String namePT;

  Country(String code, String nameEN, String namePT) {
    this.code = code;
    this.nameEN = nameEN;
    this.namePT = namePT;
  }

  /**
   * Returns the three-letter NOC code of the country (e.g., {@code "POR"}, {@code "USA"}).
   * <p>
   * This value is used for persistence and API interchange.
   *
   * @return the NOC code
   */
  public String getCode() {
    return code;
  }

  /**
   * Returns the English full name of the country (e.g., {@code "Portugal"},
   * {@code "United States of America"}).
   * <p>
   * This value is intended for display in English-language contexts and is always available from
   * the enum instance without requiring a database join.
   *
   * @return the English full country name
   */
  public String getNameEN() {
    return nameEN;
  }

  /**
   * Returns the European Portuguese full name of the country (e.g., {@code "Portugal"},
   * {@code "Estados Unidos da América"}).
   * <p>
   * This value is intended for display in Portuguese-language contexts and is always available
   * from the enum instance without requiring a database join.
   *
   * @return the Portuguese full country name
   */
  public String getNamePT() {
    return namePT;
  }

  @Override
  public String toString() {
    return code;
  }
}
