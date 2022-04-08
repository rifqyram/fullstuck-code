package com.fullstuckcode.fullstuckcode.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "tutorial")
public class Tutorial {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(unique = true, nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    private Boolean published;

    private Date publishedAt;

    private Date createdAt;

    private Date updatedAt;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Category.class)
    @JoinTable(
            name = "post_category",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Tag.class)
    @JoinTable(
            name = "post_tag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    public Tutorial() {
    }

    public Tutorial(String id, String title, String content, Boolean published, Date publishedAt, Date createdAt, Set<Category> categories, Set<Tag> tags) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.published = published;
        this.publishedAt = publishedAt;
        this.createdAt = createdAt;
        this.categories = categories;
        this.tags = tags;
    }

    public Tutorial(String title, String content, Boolean published, Date publishedAt, Set<Category> categories, Set<Tag> tags) {
        this.title = title;
        this.content = content;
        this.published = published;
        this.publishedAt = publishedAt;
        this.categories = categories;
        this.tags = tags;
    }

    @PrePersist
    private void createdDate() {
        if (createdAt == null) this.createdAt = new Date();
    }

    @PreUpdate
    private void updatedDate() {
        this.updatedAt = new Date();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Boolean getPublished() {
        return published;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Set<Tag> getTags() {
        return tags;
    }
}
