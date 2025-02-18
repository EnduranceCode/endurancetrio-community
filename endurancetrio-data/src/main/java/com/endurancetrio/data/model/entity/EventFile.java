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
 */
package com.endurancetrio.data.model.entity;

import com.endurancetrio.data.model.converter.FileTypeConverter;
import com.endurancetrio.data.model.enumerator.FileType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
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
 *   <li>{@link #getEvent() event} : the {@link Event} that the {@link EventFile} belongs to.</li>
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
 *     {@link #getRevisionNumber() revisionNumber} : the revision number
 *     of the {@link EventFile file}. The first revision always starts at "01".
 *   </li>
 *   <li>
 *     {@link #getActive() isActive} : flag that indicates the most recent version
 *     of a {@link EventFile}. It is set to <i>true</i> for the file with the highest
 *     {@link #getRevisionNumber() revisionNumber} and <i>false</i> for all previous versions.
 *   </li>
 * </ul>
 */
@Entity(name = "EventFile")
@Table(name = "event_file")
public class EventFile implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}
  )
  @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
  private Event event;

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
  private Integer revisionNumber;

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

  public Event getEvent() {
    return event;
  }

  public void setEvent(Event event) {
    this.event = event;
    if (event != null) {
      event.getEventFiles().add(this);
    }
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

  public Integer getRevisionNumber() {
    return revisionNumber;
  }

  public void setRevisionNumber(Integer revisionNumber) {
    this.revisionNumber = revisionNumber;
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
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", EventFile.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("eventId=" + Optional.ofNullable(event).map(Event::getId).orElse(null))
        .add("title='" + title + "'")
        .add("fileType=" + fileType)
        .add("fileName='" + fileName + "'")
        .add("revisionNumber=" + revisionNumber)
        .add("isActive=" + isActive)
        .toString();
  }
}
