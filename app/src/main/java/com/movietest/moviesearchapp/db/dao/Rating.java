package com.movietest.moviesearchapp.db.dao;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Rating extends RealmObject {

    @PrimaryKey
    private long realmId;
    private String source;
    private String value;

    public String getSource() {
        return source;
    }

    public void setSource(final String source) {
        this.source = source;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}
