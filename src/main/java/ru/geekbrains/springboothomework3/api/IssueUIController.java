package ru.geekbrains.springboothomework3.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import ru.geekbrains.springboothomework3.model.entity.BookEntity;
import ru.geekbrains.springboothomework3.model.entity.IssueEntity;
import ru.geekbrains.springboothomework3.model.entity.ReaderEntity;
import ru.geekbrains.springboothomework3.service.IssuerService;

import javax.naming.OperationNotSupportedException;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("ui/issues")
public class IssueUIController {

    private final IssuerService service;
    private final WebClient localApiClient = WebClient.create("http://localhost:8080");

    public IssueUIController(IssuerService service) {
        this.service = service;
    }

    @GetMapping
    public String getIssues(Model model) {
        try {
            List<IssueEntity> issues = service.findAll();
            model.addAttribute("issues", issues);
            return "issues";
        } catch (NoSuchElementException e) {
            return "404";
        }
    }

    @GetMapping("/new")
    public String newIssue(Model model) {
        List<ReaderEntity> readers = getReaders();
        List<BookEntity> books = getBooks();
        model.addAttribute("books", books);
        model.addAttribute("readers", readers);
        model.addAttribute("issue", new IssueEntity());
        return "new-issue";
    }

    @PostMapping
    public String createIssue(@ModelAttribute("issue") IssueEntity issue) {
        try {
            service.save(issue);
            return "redirect:/ui/issues";
        } catch (OperationNotSupportedException e) {
            return "403";
        } catch (NoSuchElementException e) {
            return "404";
        }
    }

    @PatchMapping("/{id}")
    public String closeIssue(@PathVariable Long id) {
        service.closeIssue(id);
        return "redirect:/ui/issues";
    }

    private List<ReaderEntity> getReaders() {
        return localApiClient.get()
                .uri("/api/readers")
                .retrieve()
                .bodyToFlux(ReaderEntity.class)
                .collectList()
                .block();
    }

    private List<BookEntity> getBooks() {
        return localApiClient.get()
                .uri("/api/books")
                .retrieve()
                .bodyToFlux(BookEntity.class)
                .collectList()
                .block();
    }
}
