//package ru.geekbrains.springboothomework3.api;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import ru.geekbrains.springboothomework3.model.entity.IssueEntity;
//import ru.geekbrains.springboothomework3.service.IssuerService;
//
//import java.util.List;
//import java.util.NoSuchElementException;
//
//@Controller
//@RequestMapping("ui/issues")
//public class IssueUIController {
//
//    private IssuerService service;
//
//    public IssueUIController(IssuerService service) {
//        this.service = service;
//    }
//
//    @GetMapping
//    public String getIssues() {
//        try {
//            List<IssueEntity> issues = service.findAll();
//            return "issues";
//        } catch (NoSuchElementException e) {
//            return "404";
//        }
//    }
//
//    @GetMapping("/new")
//    public String newIssue(Model model) {
//        model.addAttribute("readers", book)
//    }
//}
