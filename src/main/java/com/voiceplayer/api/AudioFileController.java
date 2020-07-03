package com.voiceplayer.api;

import com.voiceplayer.common.googledrive.GoogleDriveService;
import com.voiceplayer.common.googledrive.model.EntityType;
import com.voiceplayer.common.googledrive.model.FileListResponse;
import com.voiceplayer.common.googledrive.model.ListResponse;
import com.voiceplayer.common.googledrive.model.SearchParams;
import com.voiceplayer.service.AudioPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/files")
public class AudioFileController {
    private final AudioPlayerService audioPlayerService;

    @Autowired
    GoogleDriveService googleDriveService;

    public AudioFileController(AudioPlayerService audioPlayerService) {
        this.audioPlayerService = audioPlayerService;
    }

    @GetMapping("/list")
    public FileListResponse listFiles() {
        return audioPlayerService.search(new SearchParams.AudioFileSearchBuilder()
                .withQuery("name contains 'based on'")
                .forEntityType(EntityType.FILE).build());
    }
}
