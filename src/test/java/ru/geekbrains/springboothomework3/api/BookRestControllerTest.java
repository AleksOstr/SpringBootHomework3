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
import ru.geekbrains.springboothomework3.api.request.BookRequest;
import ru.geekbrains.springboothomework3.model.entity.BookEntity;
import ru.geekbrains.springboothomework3.repository.BookRepository;

import java.util.List;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class BookRestControllerTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    WebTestClient webClient;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void saveTest() {
        BookRequest request = new BookRequest();
        request.setName("Book");

        BookEntity response = webClient.post()
                .uri("/api/books/save")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(BookEntity.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getId());
        Assertions.assertNotNull(response.getName());
        Assertions.assertEquals(request.getName(), response.getName());

        Assertions.assertTrue(bookRepository.findById(response.getId()).isPresent());

        bookRepository.deleteAll();
    }

    @Test
    void getBooksTest() {
        List<BookEntity> expected = bookRepository.saveAll(List.of(
                new BookEntity("first"),
                new BookEntity("second")
        ));

        List<BookEntity> response = webClient.get()
                .uri("/api/books")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<BookEntity>>(){})
                .returnResult().getResponseBody();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(expected.size(), response.size());

        for (BookEntity book : response) {
            boolean found = expected.stream().filter(it -> it.getId().equals(book.getId()))
                    .anyMatch(it -> it.getName().equals(book.getName()));
            Assertions.assertTrue(found);
        }

        bookRepository.deleteAll();
    }
}
