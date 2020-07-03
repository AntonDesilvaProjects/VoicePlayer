package com.voiceplayer.dao;

import com.voiceplayer.common.googledrive.model.File;
import com.voiceplayer.common.googledrive.model.FileListResponse;
import com.voiceplayer.common.googledrive.model.SearchParams;

public interface AudioFileDao {
    File get(String id);
    FileListResponse search(SearchParams params);
}
