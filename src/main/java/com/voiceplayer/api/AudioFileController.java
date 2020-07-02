package com.voiceplayer.api;

import com.voiceplayer.domain.AudioFileSearchParams;
import com.voiceplayer.service.AudioPlayerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/files")
public class AudioFileController {
    private final AudioPlayerService audioPlayerService;

    public AudioFileController(AudioPlayerService audioPlayerService) {
        this.audioPlayerService = audioPlayerService;
    }

    @GetMapping("/list")
    public List<String> listFiles() {
        audioPlayerService.search(new AudioFileSearchParams.AudioFileSearchBuilder().withQuery("name contains 'based on'").build());
        return null;
    }
}
