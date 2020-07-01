package com.voiceplayer.service;

import com.voiceplayer.domain.AudioFileResponse;
import com.voiceplayer.domain.AudioFileSearchParams;

public interface AudioPlayerService {
    AudioFileResponse search(AudioFileSearchParams params);
}
