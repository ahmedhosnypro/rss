package com.rss.service;


import com.rss.model.Feed;
import com.rss.repository.FeedRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedService {
    private final FeedRepository feedRepository;

    public FeedService(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    public void save(Feed feed) {
        feedRepository.save(feed);
    }

    public Feed findById(Long id) {
        var feed =  feedRepository.findById(id).orElse(null);
        if (feed == null) {
            throw new EntityNotFoundException("Feed with id " + id + " not found");
        }
        return feed;
    }

    public List<Feed> findAll() {
        return feedRepository.findAll();
    }

    public Feed enableFeed(@Positive Long id) {
        var feed = feedRepository.findById(id).orElse(null);
        if (feed == null) {
            throw new EntityNotFoundException("Feed with id " + id + " not found");
        }
        feed.setEnabled(true);
        return feedRepository.save(feed);
    }

    public Feed disableFeed(@Positive Long id) {
        var feed = feedRepository.findById(id).orElse(null);
        if (feed == null) {
            throw new EntityNotFoundException("Feed with id " + id + " not found");
        }
        feed.setEnabled(false);
        return feedRepository.save(feed);
    }

    public boolean existByLink(String link) {
        return feedRepository.existsByLink(link);
    }
}

