package com.voiceplayer.common.googledrive.model;

import java.sql.Date;
import java.sql.Time;

public class File {
    private String kind;
    private String id;
    private String name;
    private String description;
    private String mimeType;
    private Long size;
    private boolean starred;
    private boolean trashed;
    private String modifiedByMeTime;
    private String modifiedTime;
    private String createdTime;
    private String webContentLink;
    private String webViewLink;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public boolean isStarred() {
        return starred;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }

    public boolean isTrashed() {
        return trashed;
    }

    public void setTrashed(boolean trashed) {
        this.trashed = trashed;
    }

    public String getModifiedByMeTime() {
        return modifiedByMeTime;
    }

    public void setModifiedByMeTime(String modifiedByMeTime) {
        this.modifiedByMeTime = modifiedByMeTime;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getWebContentLink() {
        return webContentLink;
    }

    public void setWebContentLink(String webContentLink) {
        this.webContentLink = webContentLink;
    }

    public String getWebViewLink() {
        return webViewLink;
    }

    public void setWebViewLink(String webViewLink) {
        this.webViewLink = webViewLink;
    }
}
