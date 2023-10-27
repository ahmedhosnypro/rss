package com.rss.service;

import com.rss.model.RelatedIdentifier;
import com.rss.repository.RelatedIdentifierRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RelatedIdentifierService {
    private final RelatedIdentifierRepo relatedIdentifierRepo;

    public RelatedIdentifierService(RelatedIdentifierRepo relatedIdentifierRepo) {
        this.relatedIdentifierRepo = relatedIdentifierRepo;
    }

    public RelatedIdentifier save(RelatedIdentifier relatedIdentifier) {
        return relatedIdentifierRepo.save(relatedIdentifier);
    }

    public RelatedIdentifier findById(Long id) {
        return relatedIdentifierRepo.findById(id).orElse(null);
    }

    public List<RelatedIdentifier> saveAll(Set<RelatedIdentifier> identifiers) {
        return relatedIdentifierRepo.saveAll(identifiers);
    }
}
