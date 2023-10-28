package com.rss.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.List;
import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Feed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    @Basic(fetch = LAZY)
    private String description;

    @Column(unique = true)
    private String link;

    private Boolean enabled;

    @ElementCollection
    @CollectionTable(name = "feed_categories", joinColumns = @JoinColumn(name = "feed_id"))
    private Set<String> categories;

    public boolean isEnabled() {
        return enabled;
    }
}
