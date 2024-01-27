package ru.geekbrains.springboothomework3.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @PostMapping
    public String createReader(@ModelAttribute("reader") ReaderEntity reader) {
        service.save(reader);
        return "redirect:/ui/readers";
    }
}