package ru.geekbrains.springboothomework3.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.springboothomework3.model.entity.ReaderEntity;
import ru.geekbrains.springboothomework3.service.ReaderService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class ReaderRestController {
    private final ReaderService readerService;

    public ReaderRestController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping("/api/readers")
    public ResponseEntity<List<ReaderEntity>> getReaders() {
        List<ReaderEntity> readers = readerService.findAll();
        return ResponseEntity.ok(readers);
    }

    @GetMapping("/api/readers/{id}")
    public ResponseEntity<ReaderEntity> findById(@PathVariable Long id) {
        try {
            ReaderEntity reader = readerService.findById(id);
            return ResponseEntity.ok(reader);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
