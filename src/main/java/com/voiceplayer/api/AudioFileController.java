package com.voiceplayer.api;

import com.voiceplayer.common.googledrive.model.EntityType;
import com.voiceplayer.common.googledrive.model.File;
import com.voiceplayer.common.googledrive.model.FileListResponse;
import com.voiceplayer.common.googledrive.model.SearchParams;
import com.voiceplayer.dao.AudioFileDao;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/files")
public class AudioFileController {
    private final AudioFileDao audioPlayerService;

    public AudioFileController(AudioFileDao audioPlayerService) {
        this.audioPlayerService = audioPlayerService;
    }

    @GetMapping
    public FileListResponse listFiles() {
        return audioPlayerService.search(new SearchParams.Builder()
                .withQuery("name contains 'track' and mimeType contains 'audio'")
                .withFields("nextPageToken, files(*)")
                .forEntityType(EntityType.FILE).build());
    }

    @GetMapping("/{fileId}")
    public File getFile(@PathVariable("fileId") String fileId) {
        return audioPlayerService.get(fileId);
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> download(@PathVariable("fileId") String fileId) {
        final File file = audioPlayerService.getFileContent(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_TYPE, file.getMimeType())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .body(file.getFileContent());
    }
}
