package com.rss.controller;

import com.rss.model.Feed;
import com.rss.util.FeedUrl;
import com.rss.service.FeedService;
import com.rss.service.IngestionService;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * FeedConfigController
 * <p>
 *     <a href ="">/get /api/v1/feed - get available feeds</a>
 *     <a href ="">/post /api/v1/feed - add a new feed</a>
 *     <a href ="">/post /api/v1/feed/{id}/enable - enable a feed</a>
 *     <a href ="">/post /api/v1/feed/{id}/disable - disable a feed</a>
 */
@Validated
@RestController
@RequestMapping("/api/v1/feed")
public class FeedConfigController {
    private final FeedService feedService;
    private final IngestionService ingestionService;


    public FeedConfigController(FeedService feedService, IngestionService ingestionService) {
        this.feedService = feedService;
        this.ingestionService = ingestionService;
    }

    /**
     * Get all available feeds
     * @return list of feeds
     */
    @GetMapping
    public ResponseEntity<List<Feed>> getFeedConfigurations() {
        var feedList = feedService.findAll();
        return ResponseEntity.ok().body(feedList);
    }

    /**
     * Add a new feed
     * @param url feed url
     * @return - feed item
     * <br> - or bad request if url is invalid or feed is already present
     */
    @PostMapping
    public ResponseEntity<Feed> addFeedConfiguration(@RequestBody FeedUrl url) {
        var feed = ingestionService.ingestFeed(url.getUrl());
        if (feed == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(feed);
    }

    /**
     * Enable a feed
     * @param id feed id
     * @return - feed item
     * <br> - or not found if feed is not present
     */
    @PostMapping("/{id}/enable")
    public ResponseEntity<Feed> enableFeed(@PositiveOrZero @PathVariable Long id) {
        var feed = feedService.enableFeed(id);
        return ResponseEntity.ok().body(feed);
    }

/**
     * Disable a feed
     * @param id feed id
     * @return - feed item
     * <br> - or not found if feed is not present
     */
    @PostMapping("/{id}/disable")
    public ResponseEntity<Feed> disableFeed(@PositiveOrZero @PathVariable Long id) {
        var feed = feedService.disableFeed(id);
        return ResponseEntity.ok().body(feed);
    }
}

