/*
 * Copyright 2022-2022 the original author or authors.
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

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EventService {

  private static final Logger LOGGER = Logger.getLogger(EventService.class.getName());

  private final EventRepository eventRepository;

  private Random random = new Random();

  public EventService(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  public void removeAllEvents() {
    eventRepository
        .deleteAll();
  }

  public List<Event> getTargetedEvents() {
    List<Event> eventsToProcess = new ArrayList<>();
    Date cutoffDate = Date.from(Instant.now());
    eventRepository.findAllByTimeSentLessThanAndProcessedAtIsNull(cutoffDate)
        .doOnNext(event -> LOGGER.info(String.format("Receiving event: '%s'", event.getId())))
        .subscribe(eventsToProcess::add);
    return eventsToProcess;
  }

  public Flux<Event> insert1000Events() {
    List<Event> events = new ArrayList<>();
    for (int i = 0; i < 1000; i++) {
      Event event = new Event();
      event.id = UUID.randomUUID().toString();
      event.timeSent = generateRandomDateAfterFeb1st2025();
      event.processedAt = generateRandomProcessedAt(event.timeSent);
      events.add(event);
    }
    return eventRepository
        .saveAll(events);
  }

  private Date generateRandomProcessedAt(Date timeSent) {
    if (random.nextBoolean()) {
      return null;
    }
    Instant timeSentInstant = timeSent.toInstant();
    long minutesUntilToday = Duration.between(timeSentInstant, Instant.now()).toMinutes();
    return Date.from(timeSentInstant.plus(Duration.ofMinutes(random.nextLong(minutesUntilToday))));
  }

  private Date generateRandomDateAfterFeb1st2025() {
    GregorianCalendar february1st = new GregorianCalendar();
    february1st.set(2025, Calendar.FEBRUARY, 1);
    long minutesSinceFeb1st2025 = Duration.between(february1st.toInstant(), Instant.now()).toMinutes();
    return Date.from(Instant.now().minus(Duration.ofMinutes(random.nextLong(minutesSinceFeb1st2025))));
  }
}
