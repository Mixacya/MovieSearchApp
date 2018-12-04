package com.movietest.moviesearchapp.db.dto;

import com.google.gson.annotations.SerializedName;
import com.movietest.moviesearchapp.db.dao.Search;

import java.util.List;

public class ParentSearch {

    @SerializedName("Search")
    private List<SearchDTO> searchList;
    @SerializedName("Response")
    private String response;

    public List<SearchDTO> getSearchList() {
        return searchList;
    }

    public void setSearchList(final List<SearchDTO> searchList) {
        this.searchList = searchList;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(final String response) {
        this.response = response;
    }

}
