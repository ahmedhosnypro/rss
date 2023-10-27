package com.rss.service;


import com.rometools.rome.feed.synd.SyndEntry;
import com.rss.model.Feed;
import com.rss.repository.FeedRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FeedService {
    private FeedRepository feedRepository;

    public FeedService(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    public Feed save(Feed feed) {
        return feedRepository.save(feed);
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

    public Feed update(Feed feed) {
        return feedRepository.save(feed);
    }

    public List<SyndEntry> getRssEntries(List<String> categories, Date startDate, Date endDate) {
        return feedRepository.getEntries(categories, startDate, endDate);
    }

    public Feed enableFeed(Long id) {
        var feed = feedRepository.findById(id).orElse(null);
        if (feed == null) {
            throw new EntityNotFoundException("Feed with id " + id + " not found");
        }
        feed.setEnabled(true);
        return feedRepository.save(feed);
    }

    public Feed disableFeed(Long id) {
        var feed = feedRepository.findById(id).orElse(null);
        if (feed == null) {
            throw new EntityNotFoundException("Feed with id " + id + " not found");
        }
        feed.setEnabled(false);
        return feedRepository.save(feed);
    }
}

