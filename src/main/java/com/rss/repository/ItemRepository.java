package com.rss.repository;

import com.rss.model.Feed;
import com.rss.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    boolean existsByLink(String link);

    List<Item> findByFeed(Feed feed);

    @Query("SELECT i FROM Item i JOIN i.categories c WHERE c.name = ?1")
    List<Item> findByCategory(String category);

    @Query("SELECT i FROM Item i JOIN i.categories c WHERE c.name IN ?1 AND i.pubDate BETWEEN ?2 AND ?3")
    List<Item> search(List<String> categories, Date startDate, Date endDate);
}
