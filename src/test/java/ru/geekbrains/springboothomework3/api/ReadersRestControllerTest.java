package ru.geekbrains.springboothomework3.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.geekbrains.springboothomework3.api.request.ReaderRequest;
import ru.geekbrains.springboothomework3.model.entity.ReaderEntity;
import ru.geekbrains.springboothomework3.repository.ReaderRepository;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class ReadersRestControllerTest {

    @Autowired
    WebTestClient webClient;

    @Autowired
    ReaderRepository readerRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

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

        readerRepository.deleteAll();
    }

    @Test
    void getReadersTest() {
        List<ReaderEntity> expected = readerRepository.saveAll(List.of(
                new ReaderEntity("First"),
                new ReaderEntity("Second")
        ));

        List<ReaderEntity> response = webClient.get()
                .uri("/api/readers")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<ReaderEntity>>(){})
                .returnResult().getResponseBody();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(expected.size(), response.size());

        for (ReaderEntity reader : response) {
            boolean found = expected.stream().filter(it -> it.getId().equals(reader.getId()))
                    .anyMatch(it -> it.getName().equals(reader.getName()));
            Assertions.assertTrue(found);
        }

        readerRepository.deleteAll();
    }

    @Test
    void findByIdTestOk() {
        ReaderEntity expectedReader = readerRepository.save(new ReaderEntity("Reader"));

        ReaderEntity response = webClient.get()
                .uri("/api/readers/" + expectedReader.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(ReaderEntity.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedReader.getId(), response.getId());
        Assertions.assertEquals(expectedReader.getName(), response.getName());

        readerRepository.deleteAll();
    }

    @Test
    void findByIdTestNotFound() {
        ReaderEntity expectedReader = readerRepository.save(new ReaderEntity("Reader"));

        Long maxId = jdbcTemplate.queryForObject("SELECT max(id) FROM READER", Long.class);

        webClient.get()
                .uri("/api/readers/" + maxId + "1")
                .exchange()
                .expectStatus().isNotFound();

        readerRepository.deleteAll();
    }
}
