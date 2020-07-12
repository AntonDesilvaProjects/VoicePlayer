package com.voiceplayer.service;

import com.voiceplayer.model.AudioFile;
import com.voiceplayer.model.AudioFileSearchParams;

import java.util.List;

public interface AudioPlayerService {
   List<AudioFile> search(AudioFileSearchParams params);
}
