package pl.linkshortener.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(ExpandLink.class);

    @Autowired
    Links links;


    @RequestMapping("/{shortUrl}")
    public void redirectShortLink(HttpServletRequest request, HttpServletResponse response, @PathVariable("shortUrl") String shortUrl) throws IOException {
        Link link = links.findOne(shortUrl);
        if (link != null) {
            Future future = new Future(new Loggable(new Executor(new GeolocateReaction())));
            CompletableFuture<LocatedIP> locatedIpFuture = future.schedule(new Geolocate(request.getRemoteAddr()));
            //locatedIpFuture.thenAcceptAsync(this::persistLocatedId);
            locatedIpFuture.whenCompleteAsync(this::persistLocatedId);
            String url = link.getUrl().toLowerCase();
            if (!url.startsWith("http://") && !url.startsWith("http://")) {
                url = "http://" + link.getUrl();
            }
            response.sendRedirect(url);
            return;
        }
        response.sendRedirect("/missingUrl?url=" + shortUrl);
    }

    private void handleLocalizationException(Runnable runnable) {
    }

    private void persistLocatedId(LocatedIP locatedIP, Throwable throwable) {
        if (throwable != null) {
            // handle success
            logger.info(">>>>GEOLOCATION: {}", locatedIP);
        } else {
            logger.error("Unable to retrieve geolocation.", throwable);
        }
    }
}
