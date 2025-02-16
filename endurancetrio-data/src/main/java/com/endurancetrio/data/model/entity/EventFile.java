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
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * The {@link EventFile} entity represents a file with information (other than the race's results)
 * concerning an {@link Event} or an event's race.
 * <p>
 * An {@link EventFile} can refer to the {@link Event event}'s guide, regulations, courses or media
 * images.
 * <p>
 * The {@link Event} defines the event that the file refers/belongs to.
 * <p>
 * The {@link FileType} defines the type of the {@link EventFile}.
 * <p>
 * The field {@link #getTitle() title} stores the file title that should describe the file's
 * content.
 * <p>
 * The field {@link #getFileName() fileName} value has exactly 24 characters and is in the format
 * YYYYMMDDXXXNNN-TTTYYY-VV.ext. In this format ""YYYYMMDDXXXNNN"" corresponds to the
 * {@link Event}'s {@link Event#getEventReference()}, "TTT", written in upper case, corresponds to
 * the document type ("GDE" for guides, "REG" for Regulations, "MAP" for course maps, "IMG" for
 * images and "STL" for start lists), "YYY" is the document's order number (in the case where there
 * are, for example, different regulations in the same event: middle distance regulations and full
 * distance regulations), "VV" corresponds to the document's revision number (the first revision
 * always has the number 01) and "ext" corresponds to the file extension (always in lower case
 * letters).
 * <p>
 * The field {@link #getRevisionNumber() revisionNumber} stores the revision number of the file and
 * the first revision of each file must always be one.
 * <p>
 * The field {@link #getActive() isActive} is a flag that must be set to <i>true</i> for the file,
 * referring to the same content, with the highest revision number. All other files of the same
 * content must have this flag set to <i>false</i>.
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
  private String fileName;

  @Column(name = "revision", nullable = false)
  private Integer revisionNumber;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive;

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
        .add("courseId=" + Optional.ofNullable(event).map(Event::getId).orElse(null))
        .add("title='" + title + "'")
        .add("fileType=" + fileType)
        .add("fileName='" + fileName + "'")
        .add("revisionNumber=" + revisionNumber)
        .add("isActive=" + isActive)
        .toString();
  }
}
