package com.voiceplayer.common.googledrive.model;

/**
 * https://developers.google.com/drive/api/v3/ref-search-terms
 * https://developers.google.com/drive/api/v3/search-files
 * */
public class SearchParams {
    private String query;
    private String spaces;
    private String pageToken;
    private String fieldsQuery;
    private EntityType entityType;

    private SearchParams(EntityType entityType, String query, String spaces, String pageToken, String fieldsQuery) {
        this.entityType = entityType;
        this.query = query;
        this.spaces = spaces;
        this.pageToken = pageToken;
        this.fieldsQuery = fieldsQuery;
    }

    public static class Builder {
        private String query;
        private String spaces;
        private String pageToken;
        private String fieldsQuery;
        private EntityType entityType;

        public Builder withQuery(String query) {
            this.query = query;
            return this;
        }
        public Builder inSpaces(String spaces) {
            this.spaces = spaces;
            return this;
        }
        public Builder inPage(String pageToken) {
            this.pageToken = pageToken;
            return this;
        }
        public Builder withFields(String fieldsQuery) {
            this.fieldsQuery = fieldsQuery;
            return this;
        }
        public Builder forEntityType(EntityType entityType) {
            this.entityType = entityType;
            return this;
        }
        public SearchParams build() {
            return new SearchParams(entityType, query, spaces, pageToken, fieldsQuery);
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

    public EntityType getEntityType() {
        return entityType;
    }
}
