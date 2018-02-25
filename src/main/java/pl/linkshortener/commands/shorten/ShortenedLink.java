package pl.linkshortener.commands.shorten;

import pl.linkshortener.commands.Command;

enum ShortenStatus {
    SUCCESS, ERROR
}

public class ShortenedLink implements Command.Response {
    private ShortenStatus status;
    private String message;
    private String originalUrl;
    private String shortUrl;

    private String managementUrl;

    ShortenedLink(String originalUrl) {
        this.status = ShortenStatus.SUCCESS;
        this.originalUrl = originalUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public String getManagementUrl() {
        return managementUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public ShortenStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setManagementUrl(String managementUrl) {
        this.managementUrl = managementUrl;
    }

    public void error(String message, String shortenedUrl) {
        this.message = message;
        this.shortUrl = shortenedUrl;
        this.status = ShortenStatus.ERROR;
    }
}