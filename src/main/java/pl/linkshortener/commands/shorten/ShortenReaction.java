package pl.linkshortener.commands.shorten;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.linkshortener.commands.Reaction;
import pl.linkshortener.dao.Links;
import pl.linkshortener.dao.entities.Link;
import pl.linkshortener.model.ShortenedLink;


public class ShortenReaction implements Reaction<Shorten, ShortenedLink> {

    Logger logger = LoggerFactory.getLogger(ShortenReaction.class);


    private Links links;

    public ShortenReaction(Links links) {
        this.links = links;
    }

    @Override
    public ShortenedLink react(Shorten shorten) {
        Link link = new Link();
        link.setUrl(shorten.getUrl());
        if (shorten.getShortUrl() != null && !shorten.getShortUrl().isEmpty()) {
            boolean exist = links.existsByShortUrl(shorten.getShortUrl());
            if (exist) {
                logger.error("Requested short url is already taken.");
            } else {
                link.setShortUrl(shorten.getShortUrl());
            }
        } else {
            link.setShortUrl(generateShortUrl(shorten.getUrl()));
        }
        link.setManagementUrl(shorten.getUrl().concat("management"));
        links.save(link);
        return new ShortenedLink(link.getUrl(), link.getShortUrl(), link.getManagementUrl());
    }

    private String generateShortUrl(String url) {
        return url.concat("shortUrl");
    }
}
