package com.rss.repository;

import com.rss.model.RelatedIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelatedIdentifierRepo extends JpaRepository<RelatedIdentifier, Long> {
}
