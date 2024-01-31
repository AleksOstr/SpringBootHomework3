package ru.geekbrains.springboothomework3.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Book")
public class BookRestController {
    private final BookService bookService;

    @Autowired
    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/api/books")
    @Operation(summary = "Get all books")
    @ApiResponse(responseCode = "200", description = "Get the list of books", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = BookEntity.class))})
    public ResponseEntity<List<BookEntity>> getBooks() {
        List<BookEntity> books = bookService.findAll();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/api/books/{id}")
    @Operation(summary = "Get book by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get the book", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = BookEntity.class))}),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<BookEntity> findById(@Parameter(description = "ID of target book") @PathVariable Long id) {
        try {
            BookEntity book = bookService.findById(id);
            return ResponseEntity.ok(book);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
