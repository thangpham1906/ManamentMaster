package vn.techkids.freemusic11.notification;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import vn.techkids.freemusic11.utils.MusicHandler;

/**
 * Created by qklahpita on 12/9/17.
 */

public class MusicService extends Service {
    private static final String TAG = "MusicService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        MusicHandler.playPause();

        return START_NOT_STICKY;
    }

//    @Override
//    public void onTaskRemoved(Intent rootIntent) {
//        super.onTaskRemoved(rootIntent);
//        Log.d(TAG, "onTaskRemoved: ");
//
//        MusicNotification.builder.setOngoing(false);
//        MusicNotification.notificationManager.cancelAll();
//    }

}
