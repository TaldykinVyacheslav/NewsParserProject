package com.byelex.newsparser.Models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Taldykin V.S.
 * @version 1.00 23.03.14 21:20
 */
@Entity
@Table(name = "TOKENS")
public class Token {

    private Long id;
    private String name;
    private Boolean isComplex;
    private String description;

    public Token() {
    }

    public Token(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "COMPLEX")
    public Boolean getComplex() {
        return isComplex;
    }

    public void setComplex(Boolean complex) {
        isComplex = complex;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
