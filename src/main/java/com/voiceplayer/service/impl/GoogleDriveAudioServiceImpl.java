package com.voiceplayer.service.impl;

import com.voiceplayer.common.googledrive.model.EntityType;
import com.voiceplayer.common.googledrive.model.FileListResponse;
import com.voiceplayer.common.googledrive.model.SearchParams;
import com.voiceplayer.dao.AudioFileDao;
import com.voiceplayer.model.AudioFile;
import com.voiceplayer.model.AudioFileSearchParams;
import com.voiceplayer.service.AudioPlayerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class GoogleDriveAudioServiceImpl implements AudioPlayerService {
    private final AudioFileDao audioFileDao;

    public GoogleDriveAudioServiceImpl(@Qualifier("googleAudioFileDaoImpl") AudioFileDao audioFileDao) {
        this.audioFileDao = audioFileDao;
    }

    @Override
    public List<AudioFile> search(AudioFileSearchParams params) {
        FileListResponse fileListResponse = audioFileDao.search(buildSearchParams(params));
        return new ArrayList<>();
    }

    private SearchParams buildSearchParams(AudioFileSearchParams params) {
        final StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("name contains ").append(params.getFileName());
        // we are also including the artist name as part of the file name although this isn't
        // necessarily true - it will work most of the time
        final String artists = Optional.ofNullable(params.getArtists())
                .orElse(new HashSet<>())
                .stream()
                .map(artistName -> String.format(" name contains %s ", artistName))
                .collect(Collectors.joining(" or "));

        if (StringUtils.isNotEmpty(artists)) {
            queryBuilder.append(" and ").append(" ( ").append(artists).append(" ) ");
        }
        return new SearchParams.Builder()
                .withQuery(queryBuilder.toString())
                .forEntityType(EntityType.FILE).build();
    }
}
