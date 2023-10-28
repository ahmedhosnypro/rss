package com.rss.util;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class FeedUrl {
    private @NotBlank String url;
}
