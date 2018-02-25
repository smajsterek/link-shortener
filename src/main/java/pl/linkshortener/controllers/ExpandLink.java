package pl.linkshortener.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.linkshortener.commands.geolocate.Geolocate;
import pl.linkshortener.commands.geolocate.GeolocateReaction;
import pl.linkshortener.commands.geolocate.LocatedIP;
import pl.linkshortener.dao.Links;
import pl.linkshortener.dao.entities.Link;
import pl.linkshortener.decorators.Executor;
import pl.linkshortener.decorators.Future;
import pl.linkshortener.decorators.Loggable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RestController
public class ExpandLink {

    @Autowired
    Links links;


    @RequestMapping("/{shortUrl}")
    public void redirectShortLink(HttpServletRequest request, HttpServletResponse response, @PathVariable("shortUrl") String shortUrl) throws IOException {
        Link link = links.findOne(shortUrl);
        if (link != null) {
            Future future = new Future(new Loggable(new Executor(new GeolocateReaction())));
            CompletableFuture<LocatedIP> locatedIpFuture = future.schedule(new Geolocate(request.getRemoteAddr()));
            locatedIpFuture.thenAcceptAsync(this::persistLocatedId);
            response.sendRedirect(link.getUrl());
        }
        response.sendRedirect("/missingUrl");
    }

    private void persistLocatedId(LocatedIP locatedIP) {
        //TODO: implement
    }
}
