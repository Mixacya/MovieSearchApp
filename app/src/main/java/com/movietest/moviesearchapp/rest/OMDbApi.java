package com.movietest.moviesearchapp.rest;

import com.movietest.moviesearchapp.BuildConfig;
import com.movietest.moviesearchapp.db.dto.MovieInfoDTO;
import com.movietest.moviesearchapp.db.dto.ParentSearch;
import com.movietest.moviesearchapp.db.dto.SearchDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OMDbApi {

    @GET("?type=movie")
    Call<ParentSearch> searchList(@Query("apikey") final String apiKey, @Query("s") final String search, @Query("page") final int page);

    @GET("?plot=full")
    Call<MovieInfoDTO> movieInformation(@Query("apikey") final String apiKey, @Query("i") final String omdbId);

//http://www.omdbapi.com/?apikey=9679f5dc&i=tt0372784&plot=full
}
