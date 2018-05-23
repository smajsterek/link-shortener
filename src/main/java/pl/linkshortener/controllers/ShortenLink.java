package pl.linkshortener.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.linkshortener.commands.shorten.Shorten;
import pl.linkshortener.commands.shorten.ShortenReaction;
import pl.linkshortener.commands.shorten.ShortenedLink;
import pl.linkshortener.dao.Links;
import pl.linkshortener.decorators.Executor;
import pl.linkshortener.decorators.Loggable;
import pl.linkshortener.decorators.Now;

@RestController
public class ShortenLink {

    private Links links;

    public ShortenLink(Links links) {
        this.links = links;
    }

    @RequestMapping("/shorten")
    public ShortenedLink shortenLink(String url, String shortUrl) {
        Now now = new Now(new Loggable(new Executor(new ShortenReaction(links))));
        return now.execute(new Shorten(url, shortUrl));
    }
}
