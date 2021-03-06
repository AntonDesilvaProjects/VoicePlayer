package com.voiceplayer.dao.impl;

import com.voiceplayer.common.googledrive.GoogleDriveService;
import com.voiceplayer.common.googledrive.model.File;
import com.voiceplayer.common.googledrive.model.FileListResponse;
import com.voiceplayer.common.googledrive.model.SearchParams;
import com.voiceplayer.dao.AudioFileDao;

import org.springframework.stereotype.Component;

@Component
public class GoogleAudioFileDaoImpl implements AudioFileDao {
    final GoogleDriveService googleDriveService;

    public GoogleAudioFileDaoImpl(GoogleDriveService googleDriveService) {
        this.googleDriveService = googleDriveService;
    }

    @Override
    public File get(String id) {
        return googleDriveService.get(id);
    }

    @Override
    public File getFileContent(String id) {
        return googleDriveService.downloadFile(id);
    }

    @Override
    public FileListResponse search(SearchParams params) {
        return (FileListResponse) googleDriveService.list(params);
    }
}
