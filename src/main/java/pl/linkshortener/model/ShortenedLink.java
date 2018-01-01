package pl.linkshortener.model;

import pl.linkshortener.commands.Command;

public class ShortenedLink implements Command.Response {
    private String originalUrl;
    private String shortenedUrl;
    private String managementUrl;

    public ShortenedLink(String originalUrl, String shortenedUrl, String managementUrl) {
        this.originalUrl = originalUrl;
        this.shortenedUrl = shortenedUrl;
        this.managementUrl = managementUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortenedUrl() {
        return shortenedUrl;
    }

    public String getManagementUrl() {
        return managementUrl;
    }

}
