package com.rss.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Feed feed;

    private String title;

    private String link;

    @Lob
    @Basic(fetch = LAZY)
    private String description;

    private String guid;

    private Date pubDate;

    //thumbnails
    @ElementCollection
    @CollectionTable(name = "item_thumbnail", joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "thumbnail")
    private Set<String> thumbnails;

    private String author;


    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "item_category",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;

    @OneToMany(cascade = CascadeType.ALL)
    private List<RelatedIdentifier> relatedIdentifiers;
}