package ru.geekbrains.springboothomework3.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.springboothomework3.api.request.ReaderRequest;
import ru.geekbrains.springboothomework3.model.Issue;
import ru.geekbrains.springboothomework3.model.Reader;
import ru.geekbrains.springboothomework3.service.ReaderService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/reader")
public class ReaderController {

    private ReaderService service;

    public ReaderController(ReaderService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reader> getReaderById(@PathVariable Long id) {
        try {
            Reader reader = service.getReaderById(id);
            return ResponseEntity.status(HttpStatus.OK).body(reader);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}/issue")
    public ResponseEntity<List<Issue>> getReaderIssues(@PathVariable Long id) {
        try {
            List<Issue> issues = service.getReaderIssues(id);
            return ResponseEntity.ok().body(issues);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteReader(@PathVariable Long id) {
        try {
            service.deleteReader(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Reader> insertReader(ReaderRequest request) {
        try {
            Reader reader = service.insertReader(request);
            return ResponseEntity.status(HttpStatus.OK).body(reader);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
