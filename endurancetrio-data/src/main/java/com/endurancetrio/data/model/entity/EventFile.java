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

package com.endurancetrio.data.model.entity;

import com.endurancetrio.data.model.converter.FileTypeConverter;
import com.endurancetrio.data.model.enumerator.FileType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * The {@link EventFile} entity represents a file with information (other than the
 * {@link Race race}'s results) concerning an {@link Event} or an event's {@link Race}.
 * <p>
 * An {@link EventFile} may refer to the {@link Event event}'s guide, regulations, courses or media
 * images.
 * <p>
 * The {@link EventFile}'s fields are defined as follows:
 * <ul>
 *   <li>
 *     {@link #getId() id} : the unique identifier of the {@link EventFile} that
 *     is automatically generated and is the primary key.
 *   </li>
 *   <li>
 *     {@link #getTitle() title} : the title of the file that should describe
 *     the {@link EventFile}'s content.
 *   </li>
 *   <li>
 *     {@link #getFileType() fileType} : the {@link FileType} used to classify the type
 *     of {@link EventFile} that is associated with an {@link Event}.
 *   </li>
 *   <li>
 *     {@link #getFileName() fileName} : the {@link EventFile file} name that has exactly
 *     24 characters and has the format YYYYMMDDXXXNNN-TTTYYY-VV.ext. In this format,
 *     "YYYYMMDDXXXNNN" corresponds to the {@link Event}'s {@link Event#getEventReference() reference},
 *     "TTT", written in upper case letters, corresponds to the document type
 *     ("GDE" for guides, "REG" for Regulations, "MAP" for course maps, "IMG" for images
 *     and "STL" for start lists), "YYY" is the document's order number (in the cases where
 *     there are, for example, different regulations in the same event: middle distance regulations
 *     and full distance regulations), "VV" corresponds to the document's revision number
 *     (the first revision always starts at "01") and "ext" corresponds to the file extension
 *     (always in lower case letters).
 *   </li>
 *   <li>
 *     {@link #getRevision() revisionNumber} : the revision number
 *     of the {@link EventFile file}. The first revision always starts at "01".
 *   </li>
 *   <li>
 *     {@link #getActive() isActive} : flag that indicates the most recent version
 *     of a {@link EventFile}. It is set to <i>true</i> for the file with the highest
 *     {@link #getRevision() revisionNumber} and <i>false</i> for all previous versions.
 *   </li>
 * </ul>
 */
@Entity(name = "EventFile")
@Table(name = "event_file")
public class EventFile implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_event_file")
  @SequenceGenerator(name = "sq_event_file", sequenceName = "sq_event_file", allocationSize = 1)
  private Long id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "file_type", nullable = false)
  @Convert(converter = FileTypeConverter.class)
  private FileType fileType;

  @Column(name = "file_name", nullable = false, unique = true)
  @Pattern(
      regexp = "^\\d{8}[A-Z]{3}\\d{3}-[A-Z]{3}\\d{3}-\\d{2}\\.[a-zA-Z0-9]+$",
      message = "File name must follow the format YYYYMMDDXXXNNN-TTTYYY-VV.ext (e.g., 19840815NAC001-IMG001-01.png)"
  )
  private String fileName;

  @Column(name = "revision", nullable = false)
  private Integer revision;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive;

  /**
   * Default constructor for the {@link EventFile} entity.
   */
  public EventFile() {
    super();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public FileType getFileType() {
    return fileType;
  }

  public void setFileType(FileType fileType) {
    this.fileType = fileType;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public Integer getRevision() {
    return revision;
  }

  public void setRevision(Integer revision) {
    this.revision = revision;
  }

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EventFile eventFile = (EventFile) o;
    return id != null && Objects.equals(id, eventFile.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", EventFile.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("title='" + title + "'")
        .add("fileType=" + fileType)
        .add("fileName='" + fileName + "'")
        .add("revisionNumber=" + revision)
        .add("isActive=" + isActive)
        .toString();
  }
}
