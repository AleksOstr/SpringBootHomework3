package ru.geekbrains.springboothomework3.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Issue")
public class IssueUIController {

    private final IssuerService service;
    private final WebClient localApiClient = WebClient.create("http://localhost:8080");

    public IssueUIController(IssuerService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get all issues")
    @ApiResponse(responseCode = "200", description = "Get the list of issues", content = {
            @Content(mediaType = "text/html", schema = @Schema(implementation = IssueEntity.class))})
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
    @Operation(summary = "Return HTML page for creating new issue")
    @ApiResponse(responseCode = "200", description = "Get HTML page for creating new issue", content = {
            @Content(mediaType = "text/html")})
    public String newIssue(Model model) {
        List<ReaderEntity> readers = getReaders();
        List<BookEntity> books = getBooks();
        model.addAttribute("books", books);
        model.addAttribute("readers", readers);
        model.addAttribute("issue", new IssueEntity());
        return "new-issue";
    }

    @PostMapping
    @Operation(summary = "Create new issue")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New issue created", content = {
                    @Content(mediaType = "text/html")}),
            @ApiResponse(responseCode = "404", description = "Book or reader not found"),
            @ApiResponse(responseCode = "403", description = "Reader has maximum of allowed books")})
    public String createIssue(@Parameter(description = "New issue") @ModelAttribute("issue") IssueEntity issue) {
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
    @Operation(summary = "Close issue")
    @ApiResponse(responseCode = "200", description = "Return time set", content = {
            @Content(mediaType = "text/html")})
    public String closeIssue(@Parameter(description = "ID of issue to set return time") @PathVariable Long id) {
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
