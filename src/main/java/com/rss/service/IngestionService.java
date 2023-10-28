package com.rss.service;

import com.rometools.rome.feed.synd.SyndCategory;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.rss.model.Category;
import com.rss.model.Feed;
import com.rss.model.Item;
import com.rss.model.RelatedIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class IngestionService {
    private final FeedService feedService;
    private final ItemService itemService;
    private final CategoryService categoryService;
    private final RelatedIdentifierService relatedIdentifierService;

    Logger logger = LoggerFactory.getLogger(IngestionService.class);

    public IngestionService(
            FeedService feedService,
            ItemService itemService,
            CategoryService categoryService,
            RelatedIdentifierService relatedIdentifierService
    ) {
        this.feedService = feedService;
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.relatedIdentifierService = relatedIdentifierService;
    }

    @Scheduled(fixedRate = 3600)
    public void ingestFeeds() {
        // Get all enabled feeds
        List<Feed> configs = feedService.findAll();

        // Ingest each feed
        for (Feed config : configs) {
            if (Boolean.TRUE.equals(config.isEnabled())) {
                ingestFeed(config);
            }
        }
    }

    public Feed ingestFeed(String link) {
        SyndFeed syndFeed = readFeed(link);
        if (syndFeed == null) {
            return null;
        }
        Feed feed = new Feed();
        feed.setTitle(syndFeed.getTitle());
        feed.setDescription(syndFeed.getDescription());
        feed.setLink(link);
        feed.setEnabled(true);
        feedService.save(feed);
        for (SyndEntry entry : syndFeed.getEntries()) {
            processItems(feed, entry);
        }
        return feed;
    }

    private void ingestFeed(Feed feed) {
        SyndFeed syndFeed = readFeed(feed.getLink());
        if (syndFeed == null) {
            return;
        }

        for (SyndEntry entry : syndFeed.getEntries()) {
            processItems(feed, entry);
        }
    }

    private void processItems(Feed feed, SyndEntry entry) {
        Item item = new Item();

        // Map entry fields to feed item
        item.setFeed(feed);
        item.setTitle(entry.getTitle());
        item.setLink(entry.getLink());
        item.setDescription(entry.getDescription().getValue());
        item.setPubDate(entry.getPublishedDate());
        item.setAuthor(entry.getAuthor());

        // Handle thumbnails
        Set<String> thumbnails = new HashSet<>();
        entry.getEnclosures().forEach(enclosure -> {
                    if (enclosure.getType().contains("image")) {
                        thumbnails.add(enclosure.getUrl());
                    }
                }
        );
        item.setThumbnails(thumbnails);

        // Handle categories
        Set<Category> categories = getCategories(entry);
        item.setCategories(categories);

        // Handle related identifiers
        Set<RelatedIdentifier> identifiers = new HashSet<>();
        entry.getForeignMarkup().forEach(foreignMarkup -> {
            RelatedIdentifier identifier = new RelatedIdentifier();
            identifier.setValue(foreignMarkup.getValue());
            identifier.setType(foreignMarkup.getName());
            identifiers.add(identifier);
        });
        var savedIdentifiers = relatedIdentifierService.saveAll(identifiers);
        item.setRelatedIdentifiers(savedIdentifiers);

        itemService.save(item);
    }

    private Set<Category> getCategories(SyndEntry entry) {
        List<SyndCategory> syndCategories = entry.getCategories();
        Set<Category> categories = new HashSet<>();
        for (SyndCategory category : syndCategories) {
            categories.add(categoryService.findOrCreate(category.getName()));
        }
        return categories;
    }

    private SyndFeed readFeed(String link) {
        try {
            SyndFeedInput input = new SyndFeedInput();
            URI uri = new URI(link);
            URL url = uri.toURL();
            InputStream stream = url.openConnection().getInputStream();
            return input.build(new XmlReader(stream));
        } catch (Exception ex) {
            logger.error("Error ingesting feed {}", link, ex);
        }
        return null;
    }
}