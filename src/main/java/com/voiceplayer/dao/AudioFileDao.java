package com.voiceplayer.dao;

import com.voiceplayer.common.googledrive.model.File;
import com.voiceplayer.common.googledrive.model.FileListResponse;
import com.voiceplayer.common.googledrive.model.SearchParams;
import org.springframework.core.io.InputStreamResource;

public interface AudioFileDao {
    File get(String id);
    InputStreamResource getFileContent(String id);
    FileListResponse search(SearchParams params);
}
