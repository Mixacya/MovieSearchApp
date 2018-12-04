package com.movietest.moviesearchapp.db.dto;

import com.google.gson.annotations.SerializedName;
import com.movietest.moviesearchapp.db.dao.MovieInfo;
import com.movietest.moviesearchapp.db.dao.Rating;

import java.util.ArrayList;
import java.util.List;


public class MovieInfoDTO {

    @SerializedName("Title")
    private String title;
    @SerializedName("Year")
    private String year;
    @SerializedName("Released")
    private String release;
    @SerializedName("Director")
    private String director;
    @SerializedName("Plot")
    private String plot;
    @SerializedName("Poster")
    private String poster;
    @SerializedName("imdbID")
    private String imdbID;
    @SerializedName("Ratings")
    private List<RatingDTO> ratingList;

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

//    public String getRated() {
//        return rated;
//    }
//
//    public void setRated(final String rated) {
//        this.rated = rated;
//    }

    public String getRelease() {
        return release;
    }

    public void setRelease(final String release) {
        this.release = release;
    }

//    public String getRuntime() {
//        return runtime;
//    }
//
//    public void setRuntime(final String runtime) {
//        this.runtime = runtime;
//    }
//
//    public String getGenre() {
//        return genre;
//    }
//
//    public void setGenre(final String genre) {
//        this.genre = genre;
//    }

    public String getDirector() {
        return director;
    }

    public void setDirector(final String director) {
        this.director = director;
    }

//    public String getWriter() {
//        return writer;
//    }
//
//    public void setWriter(final String writer) {
//        this.writer = writer;
//    }
//
//    public String getActors() {
//        return actors;
//    }
//
//    public void setActors(final String actors) {
//        this.actors = actors;
//    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(final String plot) {
        this.plot = plot;
    }

//    public String getLanguage() {
//        return language;
//    }
//
//    public void setLanguage(final String language) {
//        this.language = language;
//    }
//
//    public String getCountry() {
//        return country;
//    }
//
//    public void setCountry(final String country) {
//        this.country = country;
//    }
//
//    public String getAwards() {
//        return awards;
//    }
//
//    public void setAwards(final String awards) {
//        this.awards = awards;
//    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(final String poster) {
        this.poster = poster;
    }

//    public String getMetascore() {
//        return metascore;
//    }

//    public void setMetascore(final String metascore) {
//        this.metascore = metascore;
//    }
//
//    public String getImdbRating() {
//        return imdbRating;
//    }
//
//    public void setImdbRating(final String imdbRating) {
//        this.imdbRating = imdbRating;
//    }
//
//    public String getImdbVotes() {
//        return imdbVotes;
//    }
//
//    public void setImdbVotes(final String imdbVotes) {
//        this.imdbVotes = imdbVotes;
//    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(final String imdbID) {
        this.imdbID = imdbID;
    }

//    public String getType() {
//        return type;
//    }
//
//    public void setType(final String type) {
//        this.type = type;
//    }
//
//    public String getDvd() {
//        return dvd;
//    }
//
//    public void setDvd(final String dvd) {
//        this.dvd = dvd;
//    }
//
//    public String getBoxOffice() {
//        return boxOffice;
//    }
//
//    public void setBoxOffice(final String boxOffice) {
//        this.boxOffice = boxOffice;
//    }
//
//    public String getProduction() {
//        return production;
//    }
//
//    public void setProduction(final String production) {
//        this.production = production;
//    }
//
//    public String getWebsite() {
//        return website;
//    }
//
//    public void setWebsite(final String website) {
//        this.website = website;
//    }
//
//    public String getResponse() {
//        return response;
//    }
//
//    public void setResponse(final String response) {
//        this.response = response;
//    }

    public List<RatingDTO> getRatingList() {
        return ratingList;
    }

    public void setRatingList(final List<RatingDTO> ratingList) {
        this.ratingList = ratingList;
    }

    public static MovieInfoDTO fromMovieInfo(final MovieInfo movieInfo) {
        final MovieInfoDTO dto = new MovieInfoDTO();
        dto.setDirector(movieInfo.getDirector());
        dto.setImdbID(movieInfo.getImdbID());
        dto.setPlot(movieInfo.getPlot());
        dto.setRelease(movieInfo.getRelease());
        dto.setPoster(movieInfo.getPoster());;
        dto.setRatingList(makeRatingList(movieInfo.getRatingList()));
        dto.setTitle(movieInfo.getTitle());
        dto.setYear(movieInfo.getYear());
        return dto;
    }

    private static List<RatingDTO> makeRatingList(final String line) {
        final List<RatingDTO> ratingDTOList = new ArrayList<>();
        RatingDTO rating = new RatingDTO();
        rating.setValue(line);
        ratingDTOList.add(rating);
        return ratingDTOList;
    }
}
