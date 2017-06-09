package com.padeoe.batterhistorian.pojo;

import com.padeoe.platformtools.ApkInfo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by padeoe on 2017/4/23.
 */
@Entity
@Table(name = "app")
public class App {
    private int id;
    @NotNull//android没有限制包名长度，本系统默认采用最大255
    private String packageName;
    @NotNull //android将versionCode定义为一个int
    private int versionCode;
    @NotNull
    @Column(length = 30)//android规定的app名称的最大长度
    private String name;
    @NotNull
    private String versionName;
    private String description;
    private Set<Tag> tags;

    public App() {
    }

    public App(String packageName, int versionCode, String name, String versionName, String description) {
        this.packageName = packageName;
        this.versionCode = versionCode;
        this.name = name;
        this.versionName = versionName;
        this.description = description;
    }

    public App(String packageName, int versionCode, String name, String versionName, String description, Set<Tag> tags) {
        this.packageName = packageName;
        this.versionCode = versionCode;
        this.name = name;
        this.versionName = versionName;
        this.description = description;
        this.tags = tags;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "app_tag", joinColumns = @JoinColumn(name = "app_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "App{" +
                "id=" + id +
                ", packageName='" + packageName + '\'' +
                ", versionCode=" + versionCode +
                ", name='" + name + '\'' +
                ", versionName='" + versionName + '\'' +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                '}';
    }

    public static App fromApkInfo(ApkInfo apkInfo){
        return new App(apkInfo.getPackageName(),Integer.parseInt(apkInfo.getVersionCode()),apkInfo.getApkName(),apkInfo.getVersionName(),null);
    }
}

