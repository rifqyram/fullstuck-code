package com.fullstuckcode.fullstuckcode.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String name;

    private String slug;

    private Date createdAt;

    public Category() {
    }

    public Category(String id, String name, String slug, Date createdAt) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.createdAt = createdAt;
    }

    public Category(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @PrePersist
    private void createdDate() {
        if (createdAt == null) this.createdAt = new Date();
    }

    @PreUpdate
    private void updatedDate() {
        this.createdAt = new Date();
    }
}
