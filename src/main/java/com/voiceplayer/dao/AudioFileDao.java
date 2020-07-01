package com.voiceplayer.dao;

import com.voiceplayer.domain.AudioFile;
import com.voiceplayer.domain.AudioFileResponse;
import com.voiceplayer.domain.AudioFileSearchParams;

public interface AudioFileDao {
    AudioFile get(String id);
    AudioFileResponse search(AudioFileSearchParams params);
}
