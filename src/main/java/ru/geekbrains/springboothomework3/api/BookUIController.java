package ru.geekbrains.springboothomework3.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.springboothomework3.model.entity.BookEntity;
import ru.geekbrains.springboothomework3.service.BookService;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/ui/books")
public class BookUIController {

    private final BookService bookService;

    public BookUIController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String getBooks(Model model) {
        try {
            model.addAttribute("books", bookService.findAll());
            return "books";
        } catch (NoSuchElementException e) {
            return "404";
        }
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") BookEntity book) {
        return "new-book";
    }

    @PostMapping
    public String createBook(@ModelAttribute("book")BookEntity book) {
        bookService.save(book);
        return "redirect:/ui/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        bookService.deleteById(id);
        return "redirect:/ui/books";
    }
}
