package com.rss.model;

import jakarta.validation.constraints.NotBlank;

public class FeedUrl {
    private @NotBlank String url;

    public String getUrl() {
        return url;
    }
}
