package io.chat.java.api.external.home;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/info")
    public String processOpen() {
        return "OK";
    }

}
