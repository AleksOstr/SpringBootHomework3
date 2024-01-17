package ru.geekbrains.springboothomework3.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.springboothomework3.api.request.BookRequest;
import ru.geekbrains.springboothomework3.model.Book;
import ru.geekbrains.springboothomework3.service.BookService;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/book")
public class BookController {

    private BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id) {
        try {
            Book book = service.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(book);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable Long id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Book> insertBook(@RequestBody BookRequest request) {
        Book book = service.insertBook(new Book(request.getName()));
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }
}
