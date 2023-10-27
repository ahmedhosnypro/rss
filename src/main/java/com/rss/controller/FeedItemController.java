package com.rss.controller;

import com.rss.model.Item;
import com.rss.service.CategoryService;
import com.rss.service.FeedService;
import com.rss.service.ItemService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@Validated
@RestController
@RequestMapping("/posts")
public class FeedItemController {
    private final FeedService feedService;
    private final CategoryService categoryService;
    private final ItemService itemService;

    public FeedItemController(FeedService feedService, CategoryService categoryService, ItemService itemService) {
        this.feedService = feedService;
        this.categoryService = categoryService;
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<Item>> findItemsByFeedId(@PositiveOrZero @PathVariable Long feedId) {
        var feed = feedService.findById(feedId);
        var items = itemService.findByFeed(feed);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> findItemById(@PositiveOrZero @PathVariable Long id) {
        var item = itemService.findById(id);
        return ResponseEntity.ok(item);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Item>> findItemsByCategory(@NotBlank @PathVariable String category) {
        var items = itemService.findByCategory(categoryService.findByName(category).getName());
        return ResponseEntity.ok(items);
    }

    @GetMapping("/?categories={categories}&startDate={startDate}&endDate={endDate}")
    public ResponseEntity<List<Item>> search
            (
                    @PathVariable List<String> categories,
                    @Past @PathVariable Date startDate,
                    @Past @PathVariable Date endDate
            ) {
        var items = itemService.search(categories, startDate, endDate);
        return ResponseEntity.ok(items);
    }
}
