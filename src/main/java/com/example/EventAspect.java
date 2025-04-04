package com.example;

import com.google.cloud.spring.data.firestore.repository.config.EnableReactiveFirestoreRepositories;
import java.util.logging.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Aspect
@Component
@Configuration
@EnableReactiveFirestoreRepositories
public class EventAspect {

  private static final Logger LOGGER = Logger.getLogger(EventAspect.class.getName());

  private FormalDocumentValidator formalDocumentValidator = new FormalDocumentValidator();

  @AfterReturning(value = "execution(* com.example.EventRepository.findAll*(..))", returning = "eventDocuments")
  public void validateEventDocumentAfterLoad(Flux<Event> eventDocuments) {
    LOGGER.info("Validating EventDocuments after loading");
    eventDocuments.subscribe(formalDocumentValidator::validateEventDocument);
  }


  class FormalDocumentValidator {
    public void validateEventDocument(Event event) {
      // dummy validation (is there any complex logic in the real scenario?)
      if (event == null) {
        throw new IllegalArgumentException("Event is null");
      }
    }
  }
}
