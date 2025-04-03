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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT,
    classes = FirestoreSampleApplication.class)
@TestPropertySource("classpath:application-test.properties")
class FirestoreSampleApplicationIntegrationTests {

  @Autowired private TestRestTemplate testRestTemplate;

  @BeforeEach
  void cleanupEnvironment() {
    testRestTemplate.getForEntity("/reset", Void.class);
    testRestTemplate.getForEntity("/generate", Void.class);
  }

  @Test
  void testB404882848() throws InterruptedException {
    ResponseEntity<?> result = testRestTemplate.getForEntity("/test", String.class);
    System.out.println(result.getBody());
    assertEquals(HttpStatusCode.valueOf(200), result.getStatusCode());
  }
}
