package com.voiceplayer.model;

import com.voiceplayer.common.googledrive.model.File;

public class AudioFile {
    private String id;
    private String name;
    private String description;
    private String webContentLink;
    private Long size;

    public static AudioFile from(File file) {
        AudioFile audioFile = new AudioFile();
        audioFile.setId(file.getId());
        audioFile.setName(file.getName());
        audioFile.setDescription(file.getDescription());
        audioFile.setSize(file.getSize());
        audioFile.setWebContentLink(file.getWebContentLink());

        return audioFile;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebContentLink() {
        return webContentLink;
    }

    public void setWebContentLink(String webContentLink) {
        this.webContentLink = webContentLink;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
