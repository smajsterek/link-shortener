package pl.linkshortener.commands.geolocate;

import pl.linkshortener.commands.Command;

public class Geolocate implements Command<LocatedIP> {
    private String ip;

    public Geolocate(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }
}
