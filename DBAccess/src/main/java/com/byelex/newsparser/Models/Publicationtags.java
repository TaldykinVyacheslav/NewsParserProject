package com.byelex.newsparser.Models;

import javax.persistence.*;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Игорь
 * Date: 2/20/14
 * Time: 4:03 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "publicationtags")
public class Publicationtags {

    private long pk;
    private long id;
    private int pt_id;
    private Set<Publication> publications;
    private Set<Opencalaistag> opencalaistags;

    public Publicationtags() {
    }

    public Publicationtags(long pk, long id) {
        this.pk = pk;
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pt_id")
    public int getPt_id() {
        return pt_id;
    }

    public void setPt_id(int pt_id) {
        this.pt_id = pt_id;
    }

    @Column(name = "publication_pk")
    public long getPk() {
        return pk;
    }

    public void setPk(long pk) {
        this.pk = pk;
    }

    @Column(name = "opencalaistag_id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "pk")
    public Set<Publication> getPublications() {
        return publications;
    }

    public void setPublications(Set<Publication> publications) {
        this.publications = publications;
    }

    @OneToMany (fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    public Set<Opencalaistag> getOpencalaistags() {
        return opencalaistags;
    }

    public void setOpencalaistags(Set<Opencalaistag> opencalaistags) {
        this.opencalaistags = opencalaistags;
    }
}
