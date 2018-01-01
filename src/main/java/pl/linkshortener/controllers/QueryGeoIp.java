package pl.linkshortener.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.linkshortener.dao.entities.GeoIP;

@RestController
public class QueryGeoIp {

    @RequestMapping(value = "/geoip/{ip}")
    public GeoIP getGeoIp(@PathVariable("ip") String ip) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://ip-api.com/json/" + ip, GeoIP.class);
    }
}
