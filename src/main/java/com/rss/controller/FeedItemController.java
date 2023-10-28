package com.rss.controller;

import com.rss.model.Item;
import com.rss.util.SearchRequest;
import com.rss.service.CategoryService;
import com.rss.service.FeedService;
import com.rss.service.ItemService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/items")
public class FeedItemController {
    private final FeedService feedService;
    private final CategoryService categoryService;
    private final ItemService itemService;

    public FeedItemController(FeedService feedService, CategoryService categoryService, ItemService itemService) {
        this.feedService = feedService;
        this.categoryService = categoryService;
        this.itemService = itemService;
    }

    /**
     * @param feedId - id of a feed
     * @return - List of items for a given feed
     * <br> - 404 if feed not found
     * <br> - bad request if feedId is negative
     */
    @GetMapping("/{feedId}")
    public ResponseEntity<List<Item>> findItemsByFeedId(@Positive @PathVariable Long feedId) {
        var feed = feedService.findById(feedId);
        var items = itemService.findByFeed(feed);
        return ResponseEntity.ok(items);
    }

    /**
     * @param id - id of an item
     * @return - Item for a given id
     * <br> - 404 if item not found
     * <br> - bad request if id is negative
     */
    @GetMapping("/{id}")
    public ResponseEntity<Item> findItemById(@Positive @PathVariable Long id) {
        var item = itemService.findById(id);
        return ResponseEntity.ok(item);
    }

    /**
     * @param category - name of a category
     * @return - List of items for a given category
     * <br> - 404 if category not found
     * <br> - bad request if category is blank
     */
    @GetMapping("/{category}")
    public ResponseEntity<List<Item>> findItemsByCategory(@NotBlank @PathVariable String category) {
        var items = itemService.findByCategory(categoryService.findByName(category).getName());
        return ResponseEntity.ok(items);
    }

    /**
     * @param searchRequest - search request
     * @return - List of items for a given search request
     * <br> - bad request if search request is invalid
     */
    @GetMapping
    public ResponseEntity<List<Item>> search(@RequestBody SearchRequest searchRequest) {
        var items = itemService.search(searchRequest);
        return ResponseEntity.ok(items);
    }
}
