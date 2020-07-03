package com.voiceplayer.service;

import com.voiceplayer.common.googledrive.model.FileListResponse;
import com.voiceplayer.dao.AudioFileDao;
import com.voiceplayer.common.googledrive.model.SearchParams;
import org.springframework.stereotype.Service;

@Service
public class AudioPlayerService {
    private final AudioFileDao audioFileDao;

    public AudioPlayerService(AudioFileDao audioFileDao) {
        this.audioFileDao = audioFileDao;
    }

    public FileListResponse search(SearchParams params) {
        return audioFileDao.search(params);
    }
}
