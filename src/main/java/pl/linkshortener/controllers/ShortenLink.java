package pl.linkshortener.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.linkshortener.commands.shorten.Shorten;
import pl.linkshortener.commands.shorten.ShortenReaction;
import pl.linkshortener.dao.Links;
import pl.linkshortener.model.ShortenedLink;

@RestController
public class ShortenLink {

    @Autowired
    Links links;

    @RequestMapping("/shorten")
    public ShortenedLink shortenLink(String url, String shortUrl) {
        ShortenReaction shortenReaction = new ShortenReaction(links);
        return shortenReaction.react(new Shorten(url, shortUrl));
    }
}
