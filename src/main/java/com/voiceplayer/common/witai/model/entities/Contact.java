package com.voiceplayer.common.witai.model.entities;

public class Contact extends Entity {
    private boolean suggested;

    public boolean isSuggested() {
        return suggested;
    }

    public Contact setSuggested(boolean suggested) {
        this.suggested = suggested;
        return this;
    }
}
