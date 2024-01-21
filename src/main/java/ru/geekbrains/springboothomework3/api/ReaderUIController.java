package ru.geekbrains.springboothomework3.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.springboothomework3.model.Issue;
import ru.geekbrains.springboothomework3.model.Reader;
import ru.geekbrains.springboothomework3.service.ReaderService;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("ui/reader")
public class ReaderUIController {

    private ReaderService service;

    public ReaderUIController(ReaderService service) {
        this.service = service;
    }

    @GetMapping
    public String getReaders(Model model) {
        List<Reader> readers = service.getReaders();
        model.addAttribute("readers", readers);
        return "readers";
    }

    @GetMapping("/{id}")
    public String getReaderOpenedIssues(@PathVariable Long id, Model model) {
        try {
            Reader reader = service.getReaderById(id);
            List<Issue> openedIssues = service.getOpenedIssues(id);
            model.addAttribute("reader", reader);
            model.addAttribute("openedIssues", openedIssues);
            return "readerInfo";
        } catch (NoSuchElementException e) {
            return "readerInfo";
        }
    }
}