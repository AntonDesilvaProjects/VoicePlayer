package com.voiceplayer.service.impl;

import com.voiceplayer.dao.AudioFileDao;
import com.voiceplayer.domain.AudioFileResponse;
import com.voiceplayer.domain.AudioFileSearchParams;
import com.voiceplayer.service.AudioPlayerService;
import org.springframework.stereotype.Service;

@Service
public class GoogleAudioPlayerService implements AudioPlayerService {
    private final AudioFileDao audioFileDao;

    public GoogleAudioPlayerService(AudioFileDao audioFileDao) {
        this.audioFileDao = audioFileDao;
    }

    @Override
    public AudioFileResponse search(AudioFileSearchParams params) {
        return audioFileDao.search(params);
    }
}
