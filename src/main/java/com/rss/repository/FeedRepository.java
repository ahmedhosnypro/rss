package com.rss.repository;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rss.model.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {
    List<SyndEntry> getEntries(List<String> categories, Date startDate, Date endDate);
}
