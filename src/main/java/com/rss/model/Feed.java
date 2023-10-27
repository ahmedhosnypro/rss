package com.rss.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


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

    private String description;

    @Column(unique = true)
    private String link;

    private Boolean enabled;

    @ElementCollection
    @CollectionTable(name = "feed_categories", joinColumns = @JoinColumn(name = "feed_id"))
    private Set<String> categories;

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL)
    private Set<Item> items;

    public Boolean isEnabled() {
        return enabled;
    }
}
