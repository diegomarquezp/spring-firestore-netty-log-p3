/*
 * Copyright 2017-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example;

import com.google.cloud.firestore.annotation.DocumentId;
import java.util.Date;
import java.util.Objects;
import org.springframework.data.annotation.Id;

public class Event {

  @DocumentId
  String id;

  Date timeSent;

  Date processedAt;

  public Event() {}

  public Event(String id, Date timeSent, Date processedAt) {
    this.id = id;
    this.timeSent = timeSent;
    this.processedAt = processedAt;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Date getTimeSent() {
    return timeSent;
  }

  public void setTimeSent(Date timeSent) {
    this.timeSent = timeSent;
  }

  public Date getProcessedAt() {
    return processedAt;
  }

  public void setProcessedAt(Date processedAt) {
    this.processedAt = processedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Event event = (Event) o;
    return Objects.equals(id, event.id) && Objects.equals(timeSent,
        event.timeSent) && Objects.equals(processedAt, event.processedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, timeSent, processedAt);
  }
}
