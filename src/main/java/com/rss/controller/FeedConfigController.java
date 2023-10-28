package com.rss.controller;

import com.rss.model.Feed;
import com.rss.model.FeedUrl;
import com.rss.service.FeedService;
import com.rss.service.IngestionService;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<Feed>> getFeedConfigurations() {
        var feedList = feedService.findAll();
        return ResponseEntity.ok().body(feedList);
    }

    @PostMapping
    public ResponseEntity<Feed> addFeedConfiguration(@RequestBody FeedUrl url) {
        var feed = ingestionService.ingestFeed(url.getUrl());
        return ResponseEntity.ok().body(feed);
    }

    @PostMapping("/{id}/enable")
    public ResponseEntity<Feed> enableFeed(@PositiveOrZero @PathVariable Long id) {
        var feed = feedService.enableFeed(id);
        return ResponseEntity.ok().body(feed);
    }

    @PostMapping("/{id}/disable")
    public ResponseEntity<Feed> disableFeed(@PositiveOrZero @PathVariable Long id) {
        var feed = feedService.disableFeed(id);
        return ResponseEntity.ok().body(feed);
    }
}

