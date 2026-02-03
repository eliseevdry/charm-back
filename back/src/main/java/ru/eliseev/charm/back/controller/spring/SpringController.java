package ru.eliseev.charm.back.controller.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpringController {

    @GetMapping("/message")
    public String message() {
        return "showMessage";
    }
}
