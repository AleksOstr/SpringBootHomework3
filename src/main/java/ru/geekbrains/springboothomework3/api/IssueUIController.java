package ru.geekbrains.springboothomework3.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.springboothomework3.model.Issue;
import ru.geekbrains.springboothomework3.service.IssuerService;

import java.util.List;

@Controller
@RequestMapping("ui/issues")
public class IssueUIController {

    private IssuerService service;

    public IssueUIController(IssuerService service) {
        this.service = service;
    }

    @GetMapping
    public String geteIssues(Model model) {
        List<Issue> issues = service.getIssues();
        model.addAttribute("issues", issues);
        return "issues";
    }
}
