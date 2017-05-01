package com.padeoe.batterhistorian.pojo;

import javax.persistence.*;
import java.util.Set;

/**
 * App的标签。譬如社交，游戏，工具等用于描述App的分类
 * Created by padeoe on 2017/4/28.
 *
 */
@Entity
@Table(name = "tag")
public class Tag {
    private int id;
    private String tagName;
    private Set<App> apps;

    public Tag() {
    }

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    public Tag(String tagName, Set<App> apps) {
        this.tagName = tagName;
        this.apps = apps;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @ManyToMany(mappedBy = "tags")
    public Set<App> getApps() {
        return apps;
    }

    public void setApps(Set<App> apps) {
        this.apps = apps;
    }
}
