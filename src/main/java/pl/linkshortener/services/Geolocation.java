package pl.linkshortener.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.linkshortener.commands.geolocate.Geolocate;
import pl.linkshortener.commands.geolocate.GeolocateReaction;
import pl.linkshortener.commands.geolocate.LocatedIP;
import pl.linkshortener.decorators.Executor;
import pl.linkshortener.decorators.Future;
import pl.linkshortener.decorators.Loggable;

import java.util.concurrent.CompletableFuture;

@Service
public class Geolocation {
    private Logger logger = LoggerFactory.getLogger(Geolocation.class);

    public CompletableFuture<LocatedIP> locate(String remoteAddress) {
        Future future = new Future(new Loggable(new Executor(new GeolocateReaction())));
        CompletableFuture<LocatedIP> locatedIpFuture = future.schedule(new Geolocate(remoteAddress));
        locatedIpFuture.whenCompleteAsync(this::persistLocatedId);
        return locatedIpFuture;
    }

    private void persistLocatedId(LocatedIP locatedIP, Throwable throwable) {
        if (throwable != null) {
            // handle success
            logger.error("Unable to retrieve geolocation.", throwable);
        } else {
            logger.info(">>>>GEOLOCATION: {}", locatedIP);
        }
    }
}
