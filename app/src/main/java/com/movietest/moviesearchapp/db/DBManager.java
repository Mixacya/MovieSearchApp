package com.movietest.moviesearchapp.db;

import com.movietest.moviesearchapp.db.dao.MovieInfo;
import com.movietest.moviesearchapp.db.dao.Rating;
import com.movietest.moviesearchapp.db.dao.Search;
import com.movietest.moviesearchapp.db.dto.MovieInfoDTO;
import com.movietest.moviesearchapp.db.dto.SearchDTO;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;

public class DBManager {

    public static class DBSingletonHolder {
        public static DBManager instance = new DBManager();
    }

    public static DBManager getInstance() {
        return DBSingletonHolder.instance;
    }

    private <T extends RealmObject> long getMaxId(Class<T> clazz) {
        long id;
        final Realm realm = Realm.getDefaultInstance();
        final Number number = realm.where(clazz).max("realmId");
        id = number == null ? 0 : number.longValue() + 1;
        return id;
    }

    public <T extends RealmObject> T createRealmObject(Class<T> clazz) {
        return Realm.getDefaultInstance().createObject(clazz, getMaxId(clazz));
    }

    public List<SearchDTO> getSearchList() {
        final List<SearchDTO> searchList = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        final List<MovieInfo> movieInfoList = realm.copyToRealm(realm.where(MovieInfo.class).findAll());
        for (MovieInfo info : movieInfoList) {
            SearchDTO searchDTO = new SearchDTO();
            searchDTO.setImdbID(info.getImdbID());
            searchDTO.setPoster(info.getPoster());
            searchDTO.setTitle(info.getTitle());
            searchDTO.setType(info.getDirector());
            searchDTO.setYear(info.getRelease());
            searchList.add(searchDTO);
        }
        realm.commitTransaction();
        return searchList;
    }
//    public Search createSearchObject(final SearchDTO dto) {
//        Realm.getDefaultInstance().beginTransaction();
//        final Search search = createRealmObject(Search.class);
//        Realm.getDefaultInstance().commitTransaction();
//        if (dto != null) {
//            bindSearch(search, dto);
//        }
//        return search;
//    }

    public Rating createRating() {
        return createRealmObject(Rating.class);
    }

    public MovieInfo createMovieInfo(final MovieInfoDTO dto) {
        Realm.getDefaultInstance().beginTransaction();
        final MovieInfo movie = createRealmObject(MovieInfo.class);
        Realm.getDefaultInstance().commitTransaction();
        bindMovie(movie, dto);
        return movie;
    }

    public MovieInfo getSavedMovie(final String imdbId) {
        final MovieInfo movie = Realm.getDefaultInstance().where(MovieInfo.class)
                .equalTo("imdbID", imdbId)
                .findFirst();
        if (movie == null) {
            return null;
        }
        return Realm.getDefaultInstance().copyFromRealm(movie);
    }

//    private <T extends RealmObject> List<T> getObjectList(Class<T> clazz) {
//        return Realm.getDefaultInstance().where(clazz).findAll();
//    }


    private void bindMovie(final MovieInfo movie, final MovieInfoDTO dto) {
        if (dto != null) {
            Realm.getDefaultInstance().beginTransaction();
            movie.setTitle(dto.getTitle());
            movie.setYear(dto.getYear());
            movie.setImdbID(dto.getImdbID());
            movie.setRelease(dto.getRelease());
            movie.setPoster(dto.getPoster());
            movie.setDirector(dto.getDirector());
            movie.setPlot(dto.getPlot());
            movie.setRating(dto.getRatingList().get(0).getValue());
            Realm.getDefaultInstance().commitTransaction();
        }
    }


}
