package ru.geekbrains.springboothomework3.api;

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
public class ReaderUIController {

    private final ReaderService service;

    public ReaderUIController(ReaderService service) {
        this.service = service;
    }

    @GetMapping
    public String getReaders(Model model) {
        try {
            List<ReaderEntity> readers = service.findAll();
            model.addAttribute("readers", readers);
            return "readers";
        } catch (NoSuchElementException e) {
            return "404";
        }
    }

    @GetMapping("/new")
    public String addReader(@ModelAttribute("reader") ReaderEntity reader) {
        return "new-reader";
    }

    @GetMapping("/{id}")
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
    public String createReader(@ModelAttribute("reader") ReaderEntity reader) {
        service.save(reader);
        return "redirect:/ui/readers";
    }

    @DeleteMapping("/{id}")
    public String deleteReader(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/ui/readers";
    }
}