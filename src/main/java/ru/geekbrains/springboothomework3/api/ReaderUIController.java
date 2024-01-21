package ru.geekbrains.springboothomework3.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.springboothomework3.model.Reader;
import ru.geekbrains.springboothomework3.service.ReaderService;

import java.util.List;

@Controller
@RequestMapping("ui/readers")
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
}
