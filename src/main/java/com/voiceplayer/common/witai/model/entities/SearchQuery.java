package com.voiceplayer.common.witai.model.entities;

public class SearchQuery extends Entity {
    private boolean suggested;

    public boolean isSuggested() {
        return suggested;
    }

    public SearchQuery setSuggested(boolean suggested) {
        this.suggested = suggested;
        return this;
    }
}
