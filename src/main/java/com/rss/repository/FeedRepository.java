package com.rss.repository;

import com.rss.model.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {

    boolean existsByLink(String link);
}
