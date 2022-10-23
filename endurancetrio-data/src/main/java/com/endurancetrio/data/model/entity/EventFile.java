/*
 * Copyright (c) 2011-2022 Ricardo do Canto
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
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The {@link EventFile} entity represents a file with information (other than the race's results)
 * concerning an {@link Event} or an event's race.
 * <p>
 * An {@link EventFile} can refer to the {@link Event event}'s guide, regulations, race courses or
 * media images.
 * <p>
 * The {@link Event} defines the event that the file refers/belongs to.
 * <p>
 * The {@link FileType} defines the type of the {@link EventFile}.
 * <p>
 * The field {@link #getTitle() title} stores the file title that should describe the file's
 * content.
 * <p>
 * The field {@link #getFileName() fileName} stores the file name and must be unique. It must also
 * be written in small caps and must follow the format <b>aaaammdd-nnnnnn-fffii-vv.ext</b>, which
 * will be enforced on the business layer of the application, and is described next:
 * <ul>
 *   <li><b>aaaa</b> is the year of the event's start date;</li>
 *   <li><b>mm</b> is the month number of the event's start date;</li>
 *   <li><b>dd</b> is the day of the event's start date;</li>
 *   <li><b>nnnnnn</b> is the last six digits of the event's ID;</li>
 *   <li><b>fff</b> is the file's type code;</li>
 *   <li><b>ii</b> is the file's type sequence number;</li>
 *   <li><b>vv</b> is the file's revision number;</li>
 *   <li><b>ext</b> is the extension of the file which is determined by the file type.</li>
 * </ul>
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

  private static final long serialVersionUID = 5079912795319022972L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
      CascadeType.REFRESH, CascadeType.DETACH}, optional = false)
  @JoinColumn(name = "event_id", nullable = false)
  private Event event;

  @Convert(converter = FileTypeConverter.class)
  @Column(name = "file_type", nullable = false)
  private FileType fileType;

  @Column(name = "title", nullable = false)
  private String title;

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

  public FileType getFileType() {
    return fileType;
  }

  public void setFileType(FileType fileType) {
    this.fileType = fileType;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
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
}
