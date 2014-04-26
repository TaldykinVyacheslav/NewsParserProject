package com.byelex.newsparser.Models;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Taldykin V.S.
 * @version 1.00 14.03.14 0:24
 */
@Entity
@Table(name = "profiles")
public class Profile {

    private Long id;
    private String name;
    private String text;
    private String reportId;
    private List<Template> templates;

    public Profile() {
    }

    public Profile(Long id, String name, String text) {
        this.id = id;
        this.name = name;
        this.text = text;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "profile_id")
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

    @Column(name = "report_id")
    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    public List<Template> getTemplates() {
        //Collections.sort(templates);
        return templates;
    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }
}
