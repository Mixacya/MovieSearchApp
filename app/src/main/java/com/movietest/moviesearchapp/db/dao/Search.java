package com.movietest.moviesearchapp.db.dao;

import com.movietest.moviesearchapp.db.dto.SearchDTO;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Search extends RealmObject {

    @PrimaryKey
    private long realmId;
    private String title;
    private String year;
    private String imdbID;
    private String type;
    private String poster;

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(final String year) {
        this.year = year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(final String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(final String poster) {
        this.poster = poster;
    }

}
