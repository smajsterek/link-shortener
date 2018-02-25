package pl.linkshortener.commands.geolocate;

import org.springframework.web.client.RestTemplate;
import pl.linkshortener.commands.Reaction;

public class GeolocateReaction implements Reaction<Geolocate, LocatedIP> {
    @Override
    public LocatedIP react(Geolocate command) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://ip-api.com/json/" + command.getIp(), LocatedIP.class);
    }
}
