package io.joework.pictureproviderapi.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {
    

    @GetMapping("/")
    public String hello(){
        return "hello";
    }

}
