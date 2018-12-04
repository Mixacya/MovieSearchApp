package com.movietest.moviesearchapp.db.dao;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MovieInfo extends RealmObject {

    @PrimaryKey
    private long realmId;
    private String title;
    private String year;
    private String release;
    private String imdbID;
    private String poster;
    private String director;
    private String plot;
    private String rating;

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

    public String getRelease() {
        return release;
    }

    public void setRelease(final String release) {
        this.release = release;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(final String imdbID) {
        this.imdbID = imdbID;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(final String poster) {
        this.poster = poster;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(final String director) {
        this.director = director;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(final String plot) {
        this.plot = plot;
    }

    public String getRatingList() {
        return rating;
    }

    public void setRating(final String rating) {
            this.rating = rating;
    }
}
