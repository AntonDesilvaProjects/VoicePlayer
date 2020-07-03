package com.voiceplayer.common.googledrive.model;

/**
 * https://developers.google.com/drive/api/v3/ref-search-terms
 * https://developers.google.com/drive/api/v3/search-files
 * */
public class AudioFileSearchParams {
    private String query;
    private String spaces;
    private String pageToken;
    private String fieldsQuery;

    private AudioFileSearchParams(String query, String spaces, String pageToken, String fieldsQuery) {
        this.query = query;
        this.spaces = spaces;
        this.pageToken = pageToken;
        this.fieldsQuery = fieldsQuery;
    }

    public static class AudioFileSearchBuilder {
        private String query;
        private String spaces;
        private String pageToken;
        private String fieldsQuery;

        public AudioFileSearchBuilder withQuery(String query) {
            this.query = query;
            return this;
        }
        public AudioFileSearchBuilder inSpaces(String spaces) {
            this.spaces = spaces;
            return this;
        }
        public AudioFileSearchBuilder inPage(String pageToken) {
            this.pageToken = pageToken;
            return this;
        }
        public AudioFileSearchBuilder withFields(String fieldsQuery) {
            this.fieldsQuery = fieldsQuery;
            return this;
        }
        public AudioFileSearchParams build() {
            return new AudioFileSearchParams(query, spaces, pageToken, fieldsQuery);
        }
    }

    public String getQuery() {
        return query;
    }

    public String getSpaces() {
        return spaces;
    }

    public String getPageToken() {
        return pageToken;
    }

    public String getFieldsQuery() {
        return fieldsQuery;
    }
}
