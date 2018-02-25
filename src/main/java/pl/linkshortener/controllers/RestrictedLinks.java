package pl.linkshortener.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestrictedLinks {

    @RequestMapping("/missingUrl")
    public String missingUrl(String url) {
        return "No url matching short id " + url + " found.";
    }
}
