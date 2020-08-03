package com.voiceplayer.service;

import com.voiceplayer.common.googledrive.model.File;
import com.voiceplayer.model.AudioFile;
import com.voiceplayer.model.AudioFileSearchParams;
import org.springframework.core.io.InputStreamResource;

import java.util.List;

public interface AudioPlayerService {
   List<AudioFile> search(AudioFileSearchParams params);
   File getFile(String fileId);
   File getFileContent(String fileId);
}
