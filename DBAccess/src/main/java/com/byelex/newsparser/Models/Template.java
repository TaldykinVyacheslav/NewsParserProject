package com.byelex.newsparser.Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author Taldykin V.S.
 * @version 1.00 23.03.14 23:05
 */
@Entity
@Table(name = "templates")
public class Template implements Comparable<Template>{

    private Long id;
    private String eventText;
    private Event event;
    private Profile profile;

    public Template() {
    }

    public Template(Long id, String eventText) {
        this.id = id;
        this.eventText = eventText;
    }

    @Id
    /*@org.hibernate.annotations.GenericGenerator(name="hilo-strategy", strategy = "hilo")
    @GeneratedValue(generator = "hilo-strategy")*/
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name="generator", strategy="increment")
    @GeneratedValue(generator="generator")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "event_id")
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Column(name = "event_text")
    public String getEventText() {
        return eventText;
    }

    public void setEventText(String eventText) {
        this.eventText = eventText;
    }

    @Override
    public int compareTo(Template o) {
        return this.getEvent().getName().compareTo(o.getEvent().getName());
    }
}
