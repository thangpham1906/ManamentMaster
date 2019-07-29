package vn.techkids.freemusic11.databases;

import android.util.Log;
import android.widget.Toast;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by qklahpita on 12/16/17.
 */

public class DatabaseHandler {
    private static Realm realm = Realm.getDefaultInstance();

    public static void addMusicType(MusicTypeModel musicTypeModel) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(musicTypeModel);
        realm.commitTransaction();
    }

    public static List<MusicTypeModel> getMusicTypes() {
        return realm.where(MusicTypeModel.class).findAll();
    }

    public static void updateFavourite(MusicTypeModel musicTypeModel) {
        realm.beginTransaction();
        musicTypeModel.isFavourite = !musicTypeModel.isFavourite;
        realm.copyToRealmOrUpdate(musicTypeModel);
        realm.commitTransaction();
    }

    public static List<MusicTypeModel> getFavourites() {

        RealmResults<MusicTypeModel> result = realm.where(MusicTypeModel.class)
                                    .equalTo("isFavourite",true)
                                    .findAll();

        return result;

    }
}
