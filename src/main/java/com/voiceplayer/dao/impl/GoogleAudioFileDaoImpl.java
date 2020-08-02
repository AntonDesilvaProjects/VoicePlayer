package com.voiceplayer.dao.impl;

import com.voiceplayer.common.googledrive.GoogleDriveService;
import com.voiceplayer.common.googledrive.model.File;
import com.voiceplayer.common.googledrive.model.FileListResponse;
import com.voiceplayer.common.googledrive.model.SearchParams;
import com.voiceplayer.dao.AudioFileDao;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

@Component
public class GoogleAudioFileDaoImpl implements AudioFileDao {
    final GoogleDriveService googleDriveService;

    public GoogleAudioFileDaoImpl(GoogleDriveService googleDriveService) {
        this.googleDriveService = googleDriveService;
    }

    @Override
    public File get(String id) {
        return null;
    }

    @Override
    public InputStreamResource getFileContent(String id) {
        return googleDriveService.downloadFile(id);
    }

    @Override
    public FileListResponse search(SearchParams params) {
        return (FileListResponse) googleDriveService.list(params);
    }
}
