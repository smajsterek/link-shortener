package pl.linkshortener.commands.shorten;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.linkshortener.commands.Reaction;
import pl.linkshortener.dao.Links;
import pl.linkshortener.dao.entities.Link;

import java.util.UUID;


public class ShortenReaction implements Reaction<Shorten, ShortenedLink> {

    private Logger logger = LoggerFactory.getLogger(ShortenReaction.class);


    private Links links;

    public ShortenReaction(Links links) {
        this.links = links;
    }

    @Override
    public ShortenedLink react(Shorten shorten) {
        ShortenedLink shortenedLink = new ShortenedLink(shorten.getUrl());
        HashedLinks hashedLinks = generateHash();
        if (shorten.getShortUrl() != null && !shorten.getShortUrl().isEmpty()) {
            boolean exist = links.exists(shorten.getShortUrl());
            if (exist) {
                logger.error("Requested short url '{}' for url '{}' is unavailable.", shorten.getShortUrl(), shorten.getUrl());
                shortenedLink.error("Requested short url is unavailable.", shorten.getShortUrl());
                return shortenedLink;
            } else {
                shortenedLink.setShortUrl(hashedLinks.getShortUrl());
            }
        } else {
            shortenedLink.setShortUrl(hashedLinks.getShortUrl());
        }
        shortenedLink.setManagementUrl(hashedLinks.getManagementUrl());
        links.save(map(shortenedLink));
        return shortenedLink;
    }

    private HashedLinks generateHash() {
        String uuid = UUID.randomUUID().toString();
        return new HashedLinks(uuid.substring(0, 8), uuid.substring(24));
    }

    private Link map(ShortenedLink shortenedLink) {
        Link link = new Link();
        link.setShortUrl(shortenedLink.getShortUrl());
        link.setManagementUrl(shortenedLink.getManagementUrl());
        link.setUrl(shortenedLink.getOriginalUrl());
        return link;
    }
}

class HashedLinks {
    private String shortUrl;
    private String managementUrl;

    HashedLinks(String shortUrl, String managementUrl) {
        this.shortUrl = shortUrl;
        this.managementUrl = managementUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public String getManagementUrl() {
        return managementUrl;
    }
}


