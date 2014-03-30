package com.byelex.newsparser.Models;

import javax.persistence.*;
import java.util.Set;

/**
 * Date: 2/20/14
 * Time: 4:03 PM
 */
@Entity
@Table(name = "opencalaistag")
public class Opencalaistag {

    private Long id;
    private String name;
    private String category;
    private Set<Publicationtags> publicationtags;

    @Column(name = "id")
    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name")
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "category")
    @Basic
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "opencalaistag_id")
    public Set<Publicationtags> getPublicationtags() {
        return publicationtags;
    }

    public void setPublicationtags(Set<Publicationtags> publicationtags) {
        this.publicationtags = publicationtags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Opencalaistag that = (Opencalaistag) o;

        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }
}