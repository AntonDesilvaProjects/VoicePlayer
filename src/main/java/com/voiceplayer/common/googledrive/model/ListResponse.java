package com.voiceplayer.common.googledrive.model;

public class ListResponse {
    private String kind;
    private String nextPageToken;
    private boolean incompleteSearch;
    private Error error;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public boolean isIncompleteSearch() {
        return incompleteSearch;
    }

    public void setIncompleteSearch(boolean incompleteSearch) {
        this.incompleteSearch = incompleteSearch;
    }

    public Error getError() {
        return error;
    }

    public ListResponse setError(Error error) {
        this.error = error;
        return this;
    }
}
