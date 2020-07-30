package com.voiceplayer.service.impl;

import com.google.common.collect.Lists;
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
        return Optional.ofNullable(fileListResponse.getFiles())
                .orElse(Lists.newArrayList())
                .stream()
                .map(AudioFile::from)
                .collect(Collectors.toList());
    }

    /**
     *  Converts the passed in AudioFileSearchParams object to SearchParams that can be used
     *  by Google Drive Service
     * */
    private SearchParams buildSearchParams(AudioFileSearchParams params) {
        final StringBuilder queryBuilder = new StringBuilder("mimeType contains 'audio'");
        queryBuilder.append(" and ");
        queryBuilder.append("name contains '").append(params.getFileName()).append("'");
        // we are also including the artist name as part of the file name as most music files
        // typical have this
        final String artists = Optional.ofNullable(params.getArtists())
                .orElse(new HashSet<>())
                .stream()
                .map(artistName -> String.format(" name contains '%s' ", artistName))
                .collect(Collectors.joining(" or "));

        if (StringUtils.isNotEmpty(artists)) {
            queryBuilder.append(" and ").append(" ( ").append(artists).append(" ) ");
        }
        return new SearchParams.Builder()
                .withQuery(queryBuilder.toString())
                .withFields("nextPageToken, files(*)")
                .forEntityType(EntityType.FILE).build();
    }
}
