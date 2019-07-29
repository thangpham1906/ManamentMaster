package vn.techkids.freemusic11.networks;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by LapTop on 11/15/2017.
 */

public interface    MusicInterface {
    @GET("api")
    Call<MusicTypesResponseJSON> getMusicTypes();

    @GET("https://itunes.apple.com/us/rss/topsongs/limit=50/genre={id}/explicit=true/json")
    Call<TopSongsResponseJSON> getTopSongs(@Path("id") String id);

    //https://tk-gx.herokuapp.com/api/audio?search_term=.....
    //link: https://stackoverflow.com/questions/36730086/retrofit-2-url-query-parameter
    @GET("https://tk-gx.herokuapp.com/api/audio")
    Call<SearchResponseJSON> getSearchSong(@Query("search_terms") String search);
}
