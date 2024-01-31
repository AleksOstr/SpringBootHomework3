package ru.geekbrains.springboothomework3.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Tag(name = "Homepage")
public class HomePageController {
    @GetMapping("/home")
    @Operation(summary = "Returns homepage")
    @ApiResponse(responseCode = "200", description = "Homepage returned",
            content = @Content(mediaType = "text/html"))
    public String homePage() {
        return "index";
    }
}
