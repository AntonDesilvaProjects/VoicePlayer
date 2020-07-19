package com.voiceplayer.api;

import com.voiceplayer.common.googledrive.model.EntityType;
import com.voiceplayer.common.googledrive.model.FileListResponse;
import com.voiceplayer.common.googledrive.model.SearchParams;
import com.voiceplayer.dao.AudioFileDao;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/files")
public class AudioFileController {
    private final AudioFileDao audioPlayerService;

    public AudioFileController(AudioFileDao audioPlayerService) {
        this.audioPlayerService = audioPlayerService;
    }

    @GetMapping("/list")
    public FileListResponse listFiles() {
        return audioPlayerService.search(new SearchParams.Builder()
                .withQuery("name contains 'track' and mimeType contains 'audio'")
                .withFields("nextPageToken, files(*)")
                .forEntityType(EntityType.FILE).build());
    }
}
