package com.voiceplayer.dao.impl;

import com.voiceplayer.dao.AudioFileDao;
import com.voiceplayer.domain.AudioFile;
import com.voiceplayer.domain.AudioFileResponse;
import com.voiceplayer.domain.AudioFileSearchParams;
import org.springframework.stereotype.Repository;

/**
 *  DAO implementation responsible for interfacing with Google Drive to access users audio
 *  files
 * */
@Repository
public class GoogleDriveAudioFileDaoImpl implements AudioFileDao {
    @Override
    public AudioFile get(String id) {
        return null;
    }

    @Override
    public AudioFileResponse search(AudioFileSearchParams params) {
        return null;
    }
}
