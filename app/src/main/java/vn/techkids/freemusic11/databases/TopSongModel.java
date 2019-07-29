package vn.techkids.freemusic11.databases;

/**
 * Created by qklahpita on 11/25/17.
 */

public class TopSongModel {
    public String song;
    public String singer;
    public String smallImage;
    public String url;
    public String largeImage;

    public int offlineImage;

    public TopSongModel(String song, String singer, String url, int offlineImage) {
        this.song = song;
        this.singer = singer;
        this.url = url;
        this.offlineImage = offlineImage;
    }

    public TopSongModel() {
    }
}
