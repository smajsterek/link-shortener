package pl.linkshortener.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.linkshortener.dao.Links;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController

public class ExpandLink {

    @Autowired
    Links links;


    @RequestMapping("/{shortUrl}")
    public void redirectShortLink(HttpServletResponse response, @PathVariable("shortUrl") String shortUrl) throws IOException {
        response.sendRedirect(shortUrl);
    }
}
