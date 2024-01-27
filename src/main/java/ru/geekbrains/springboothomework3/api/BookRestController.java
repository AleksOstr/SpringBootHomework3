package ru.geekbrains.springboothomework3.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.springboothomework3.model.entity.BookEntity;
import ru.geekbrains.springboothomework3.service.BookService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class BookRestController {
    private final BookService bookService;

    @Autowired
    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/api/books")
    public ResponseEntity<List<BookEntity>> getBooks() {
        List<BookEntity> books = bookService.findAll();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/api/books/{id}")
    public ResponseEntity<BookEntity> findById(@PathVariable Long id) {
        try {
            BookEntity book = bookService.findById(id);
            return ResponseEntity.ok(book);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
