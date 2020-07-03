package com.voiceplayer.common.googledrive.model;

import com.voiceplayer.common.googledrive.model.AudioFile;

import java.util.List;

public class AudioFileResponse {
    private String kind;
    private String nextPageToken;
    private boolean incompleteSearch;
    private List<AudioFile> files;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public boolean isIncompleteSearch() {
        return incompleteSearch;
    }

    public void setIncompleteSearch(boolean incompleteSearch) {
        this.incompleteSearch = incompleteSearch;
    }

    public List<AudioFile> getFiles() {
        return files;
    }

    public void setFiles(List<AudioFile> files) {
        this.files = files;
    }
}
