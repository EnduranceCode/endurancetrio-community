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

package com.endurancetrio.data.competitor.model.entity;

import com.endurancetrio.data.common.model.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serial;
import java.util.StringJoiner;

/**
 * Historical reference entity that documents the {@code age_group} table schema.
 * <p>
 * This entity exists solely to map the database table for historical data storage. It is NOT used
 * in application code. The active representation of age group data is the
 * {@link com.endurancetrio.data.competitor.model.enumerator.AgeGroup} enum, which is stored as a
 * VARCHAR column in result tables.
 * <p>
 * Do NOT import or reference this entity in business logic.
 */
@Entity(name = "AgeGroup")
@Table(name = "age_group")
@SequenceGenerator(
    name = "seq_endurancetrio_generator", sequenceName = "seq_age_group_id", allocationSize = 1
)
public class AgeGroup extends BaseEntity<Long> {

  @Serial
  private static final long serialVersionUID = 1L;

  @Column(name = "code", nullable = false)
  private String code;

  @Column(name = "denomination_en", nullable = false)
  private String denominationEN;

  @Column(name = "denomination_pt", nullable = false)
  private String denominationPT;

  public AgeGroup() {
    super();
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDenominationEN() {
    return denominationEN;
  }

  public void setDenominationEN(String denominationEN) {
    this.denominationEN = denominationEN;
  }

  public String getDenominationPT() {
    return denominationPT;
  }

  public void setDenominationPT(String denominationPT) {
    this.denominationPT = denominationPT;
  }

  @Override
  public boolean equals(Object o) {
    return super.equals(o);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", AgeGroup.class.getSimpleName() + "[", "]")
        .add("id=" + this.getId())
        .add("code='" + code + "'")
        .add("denominationEN='" + denominationEN + "'")
        .add("denominationPT='" + denominationPT + "'")
        .toString();
  }
}
