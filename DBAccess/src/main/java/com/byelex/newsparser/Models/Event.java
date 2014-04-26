package com.byelex.newsparser.Models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * @author Taldykin V.S.
 * @version 1.00 23.03.14 21:20
 */
@Entity
@Table(name = "events")
public class Event {

    private Long id;
    private String name;
    private List<Template> templates;

    public Event() {
    }

    public Event(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "event_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    public List<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }
}
