package vn.techkids.freemusic11.events;

import vn.techkids.freemusic11.databases.MusicTypeModel;

/**
 * Created by qklahpita on 11/22/17.
 */

public class OnClickMusicTypeEvent {
    public MusicTypeModel musicTypeModel;

    public OnClickMusicTypeEvent(MusicTypeModel musicTypeModel) {
        this.musicTypeModel = musicTypeModel;
    }
}
