package com.rss.service;

import com.rss.model.Feed;
import com.rss.model.Item;
import com.rss.model.SearchRequest;
import com.rss.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void save(Item item) {
        itemRepository.save(item);
    }

    public boolean existByLink(String link) {
        return itemRepository.existsByLink(link);
    }

    public Item findById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    public List<Item> findByFeed(Feed feed) {
        return itemRepository.findByFeed(feed);
    }

    public List<Item> findByCategory(String category) {
        return itemRepository.findByCategory(category);
    }

    public List<Item> search(SearchRequest searchRequest) {
        var categories = searchRequest.getCategories();
        var startDate = searchRequest.getStartDate();
        var endDate = searchRequest.getEndDate();

        if (categories == null) {
            categories = List.of();
        }

        if (startDate == null) {
            startDate = new Date(0);
        }

        if (endDate == null) {
            endDate = new Date();
        }

        return itemRepository.search(categories, startDate, endDate);
    }
}
