package pl.linkshortener.commands.shorten;

import pl.linkshortener.commands.Command;

public class Shorten implements Command<ShortenedLink> {
    private String url;

    private String shortUrl;

    public Shorten(String url, String shortUrl) {
        this.url = url;
        this.shortUrl = shortUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void assignShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
