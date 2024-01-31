package ru.geekbrains.springboothomework3.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.springboothomework3.model.entity.BookEntity;
import ru.geekbrains.springboothomework3.service.BookService;

@Tag(name = "Book")
@Controller
@RequestMapping("/ui/books")
public class BookUIController {

    private final BookService bookService;

    public BookUIController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Get all books")
    @ApiResponse(responseCode = "200", description = "Get the list of books", content = {
            @Content(mediaType = "text/html", schema = @Schema(implementation = BookEntity.class))})
    @GetMapping
    public String getBooks(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books";
    }

    @GetMapping("/new")
    @Operation(summary = "Return HTML page for creating new book")
    @ApiResponse(responseCode = "200", description = "Get HTML page for creating new book", content = {
            @Content(mediaType = "text/html")})
    public String newBook(@Parameter(description = "New book entity") @ModelAttribute("book") BookEntity book) {
        return "new-book";
    }

    @PostMapping
    @Operation(summary = "Create new book")
    @ApiResponse(responseCode = "200", description = "New book created", content = {
            @Content(mediaType = "text/html")})
    public String createBook(@Parameter(description = "New book") @ModelAttribute("book") BookEntity book) {
        bookService.save(book);
        return "redirect:/ui/books";
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book by ID")
    @ApiResponse(responseCode = "200", description = "Book deleted", content = {
            @Content(mediaType = "text/html")})
    public String delete(@Parameter(description = "Id of book for delete") @PathVariable Long id) {
        bookService.deleteById(id);
        return "redirect:/ui/books";
    }
}
