package com.voiceplayer.common.googledrive.model;

import java.util.List;

public class FileListResponse extends ListResponse {
    private List<File> files;

    public List<File> getFiles() {
        return files;
    }

    public FileListResponse setFiles(List<File> files) {
        this.files = files;
        return this;
    }
}
