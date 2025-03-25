/*
 * Copyright 2017-2019 the original author or authors.
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

import com.google.cloud.spring.data.firestore.FirestoreReactiveOperations;
import com.google.cloud.spring.data.firestore.FirestoreTemplate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** Example controller demonstrating usage of Spring Data Repositories for Firestore. */
@RestController
@RequestMapping()
public class EventController {

  private final EventService eventService;

  public EventController(EventService eventService, EventRepository eventRepository) {
    this.eventService = eventService;
  }

  @GetMapping("/reset")
  private Mono<Void> reset() {
    eventService.removeAllEvents();
    return Mono.empty();
  }

  @GetMapping("/generate")
  private Flux<Event> generate() {
    return eventService.insert1000Events();
  }

  @GetMapping("/test")
  private Flux<Event> test() {
    return eventService.getTargetedEvents();
  }
}
