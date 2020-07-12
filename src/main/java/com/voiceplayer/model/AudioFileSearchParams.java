package com.voiceplayer.model;

import java.util.Set;

public class AudioFileSearchParams {
    private String fileName;
    private Set<String> artists;
    private boolean includeFileContent = false;

    public String getFileName() {
        return fileName;
    }

    public AudioFileSearchParams setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public Set<String> getArtists() {
        return artists;
    }

    public AudioFileSearchParams setArtists(Set<String> artists) {
        this.artists = artists;
        return this;
    }

    public boolean isIncludeFileContent() {
        return includeFileContent;
    }

    public AudioFileSearchParams setIncludeFileContent(boolean includeFileContent) {
        this.includeFileContent = includeFileContent;
        return this;
    }
}
