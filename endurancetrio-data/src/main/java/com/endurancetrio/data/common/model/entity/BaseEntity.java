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

package com.endurancetrio.data.common.model.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import org.hibernate.Hibernate;

/**
 * {@link BaseEntity} is an abstract class that serves as a base for all entities in the system. It
 * provides a generic identifier field and common equality and hashing methods.
 *
 * @param <T> the type of the identifier, which must be serializable
 */
@MappedSuperclass
public abstract class BaseEntity<T extends Serializable> extends AuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_endurancetrio_generator")
  private T id;

  public T getId() {
    return id;
  }

  public void setId(T id) {
    this.id = id;
  }

  /**
   * Equality is based on the entity's identifier. Two entities are considered equal if they are of
   * the same class and have the same non-null identifier.
   * <p>
   * If the ID is null, we treat it as a transient (new) object. Transient objects are only equal if
   * they are the exact same instance.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof BaseEntity<?> that)) {
      return false;
    }

    Class<?> thisClass = org.hibernate.Hibernate.getClass(this);
    Class<?> thatClass = org.hibernate.Hibernate.getClass(that);

    if (thisClass != thatClass) {
      return false;
    }

    return id != null && id.equals(that.getId());
  }

  @Override
  public int hashCode() {
    return Hibernate.getClass(this).hashCode();
  }
}
