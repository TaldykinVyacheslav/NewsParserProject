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
@Table(name = "publication")
public class Publication {

    private Long pk;
    private String url;
    private String publishDate;
    private String contentType;
    private String title;
    private Long reach;
    private Integer noteId;
    private String event;
    private String reportId;
    private Set<Publicationtags> publicationtags;

    @Column(name = "NoteID")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    @Column(name = "pk")
    public Long getPk() {
        return pk;
    }

    public void setPk(Long pk) {
        this.pk = pk;
    }

    @Column(name = "publishDate")
    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    @Column(name = "contentType")
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "reach")
    public Long getReach() {
        return reach;
    }

    public void setReach(Long reach) {
        this.reach = reach;
    }

    @Column(name = "event")
    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @OneToMany (fetch = FetchType.LAZY)
    @JoinColumn(name = "publication_pk")
    public Set<Publicationtags> getPublicationtags() {
        return publicationtags;
    }

    public void setPublicationtags(Set<Publicationtags> publicationtags) {
        this.publicationtags = publicationtags;
    }

    @Column(name = "report_id")
    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Publication that = (Publication) o;

        if (contentType != null ? !contentType.equals(that.contentType) : that.contentType != null) return false;
        if (noteId != null ? !noteId.equals(that.noteId) : that.noteId != null) return false;
        if (pk != null ? !pk.equals(that.pk) : that.pk != null) return false;
        if (publishDate != null ? !publishDate.equals(that.publishDate) : that.publishDate != null) return false;
        if (reach != null ? !reach.equals(that.reach) : that.reach != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pk != null ? pk.hashCode() : 0;
        result = 31 * result + (publishDate != null ? publishDate.hashCode() : 0);
        result = 31 * result + (contentType != null ? contentType.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (reach != null ? reach.hashCode() : 0);
        result = 31 * result + (noteId != null ? noteId.hashCode() : 0);
        return result;
    }
}
