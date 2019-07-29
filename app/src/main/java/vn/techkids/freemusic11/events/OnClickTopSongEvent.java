package vn.techkids.freemusic11.events;

import vn.techkids.freemusic11.databases.TopSongModel;

/**
 * Created by qklahpita on 11/29/17.
 */

public class OnClickTopSongEvent {
    public TopSongModel topSongModel;

    public OnClickTopSongEvent(TopSongModel topSongModel) {
        this.topSongModel = topSongModel;
    }
}
