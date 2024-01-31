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
import ru.geekbrains.springboothomework3.model.entity.IssueEntity;
import ru.geekbrains.springboothomework3.model.entity.ReaderEntity;
import ru.geekbrains.springboothomework3.service.ReaderService;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("ui/readers")
@Tag(name = "Reader")
public class ReaderUIController {

    private final ReaderService service;

    public ReaderUIController(ReaderService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get all readers")
    @ApiResponse(responseCode = "200", description = "Get the list of readers", content = {
            @Content(mediaType = "text/html", schema = @Schema(implementation = ReaderEntity.class))})
    public String getReaders(Model model) {
        List<ReaderEntity> readers = service.findAll();
        model.addAttribute("readers", readers);
        return "readers";
    }

    @GetMapping("/new")
    @Operation(summary = "Return HTML page for creating new reader")
    @ApiResponse(responseCode = "200", description = "Get HTML page for creating new reader", content = {
            @Content(mediaType = "text/html")})
    public String addReader(@Parameter(description = "New reader entity") @ModelAttribute("reader") ReaderEntity reader) {
        return "new-reader";
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get reader by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reader found",
                    content = {@Content(mediaType = "text/html", schema = @Schema(implementation = ReaderEntity.class))}),
            @ApiResponse(responseCode = "404", description = "Reader not found")
    })
    public String getReaderById(@PathVariable Long id, Model model) {
        try {
            ReaderEntity reader = service.findById(id);
            List<IssueEntity> openedIssues = service.getOpenedReaderIssues(reader);
            model.addAttribute("reader", reader);
            model.addAttribute("openedIssues", openedIssues);
            return "readerInfo";
        } catch (NoSuchElementException e) {
            return "404";
        }
    }

    @PostMapping
    @Operation(summary = "Create new reader")
    @ApiResponse(responseCode = "200", description = "New reader created", content = {
            @Content(mediaType = "text/html")})
    public String createReader(@Parameter(description = "New reader") @ModelAttribute("reader") ReaderEntity reader) {
        service.save(reader);
        return "redirect:/ui/readers";
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete reader by ID")
    @ApiResponse(responseCode = "200", description = "Book deleted", content = {
            @Content(mediaType = "text/html")})
    public String deleteReader(@Parameter(name = "ID of reader for delete") @PathVariable Long id) {
        service.delete(id);
        return "redirect:/ui/readers";
    }
}