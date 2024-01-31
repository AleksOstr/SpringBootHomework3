package ru.geekbrains.springboothomework3.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.springboothomework3.model.entity.ReaderEntity;
import ru.geekbrains.springboothomework3.service.ReaderService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@Tag(name = "Reader")
public class ReaderRestController {
    private final ReaderService readerService;

    public ReaderRestController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping("/api/readers")
    @Operation(summary = "Get all readers")
    @ApiResponse(responseCode = "200", description = "Get the list of readers", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ReaderEntity.class))})
    public ResponseEntity<List<ReaderEntity>> getReaders() {
        List<ReaderEntity> readers = readerService.findAll();
        return ResponseEntity.ok(readers);
    }

    @GetMapping("/api/readers/{id}")
    @Operation(summary = "Get reader by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get the reader", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ReaderEntity.class))}),
            @ApiResponse(responseCode = "404", description = "Reader not found")
    })
    public ResponseEntity<ReaderEntity> findById(@Parameter(description = "ID of target reader") @PathVariable Long id) {
        try {
            ReaderEntity reader = readerService.findById(id);
            return ResponseEntity.ok(reader);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
