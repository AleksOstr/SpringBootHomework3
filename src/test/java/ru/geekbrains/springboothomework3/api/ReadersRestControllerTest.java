package ru.geekbrains.springboothomework3.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.geekbrains.springboothomework3.api.request.ReaderRequest;
import ru.geekbrains.springboothomework3.model.entity.ReaderEntity;
import ru.geekbrains.springboothomework3.repository.ReaderRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class ReadersRestControllerTest {

    @Autowired
    WebTestClient webClient;

    @Autowired
    ReaderRepository readerRepository;

    @Test
    void saveTest() {
        ReaderRequest request = new ReaderRequest();
        request.setName("Name");

        ReaderEntity response = webClient.post()
                .uri("/api/readers/save")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ReaderEntity.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getId());
        Assertions.assertNotNull(response.getName());
        Assertions.assertEquals(request.getName(), response.getName());

        Assertions.assertTrue(readerRepository.findById(response.getId()).isPresent());
    }
}
