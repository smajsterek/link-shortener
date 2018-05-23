package pl.linkshortener.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.linkshortener.dao.Links;
import pl.linkshortener.dao.entities.Link;
import pl.linkshortener.services.Geolocation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ExpandLink {

    private final Logger logger = LoggerFactory.getLogger(ExpandLink.class);

    private Geolocation geolocation;
    private Links links;

    public ExpandLink(Geolocation geolocation, Links links) {
        this.geolocation = geolocation;
        this.links = links;
    }

    @RequestMapping("/{shortUrl}")
    public void redirectShortLink(HttpServletRequest request, HttpServletResponse response, @PathVariable("shortUrl") String shortUrl) throws IOException {
        Link link = links.findOne(shortUrl);
        if (link != null) {
            geolocation.locate(request.getRemoteAddr());
            String url = link.getUrl().toLowerCase();
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + link.getUrl();
            }
            //TODO: create visit using Visitor service
            logger.debug("Redirecting from {} to {}", link.getShortUrl(), link.getUrl());
            response.sendRedirect(url);
            return;
        }
        response.sendRedirect("/missingUrl?url=" + shortUrl);
    }


}
