package ru.geekbrains.springboothomework3.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.springboothomework3.model.Book;
import ru.geekbrains.springboothomework3.service.BookService;

import java.util.List;

@Controller
@RequestMapping("/ui/books")
public class BookUIController {

    private BookService service;

    public BookUIController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public String getBooks(Model model) {
        List<Book> books = service.getBooks();
        model.addAttribute("books", books);
        return "books";
    }
}
