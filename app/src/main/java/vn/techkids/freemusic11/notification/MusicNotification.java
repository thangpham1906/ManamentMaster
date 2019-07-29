package vn.techkids.freemusic11.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import vn.techkids.freemusic11.R;
import vn.techkids.freemusic11.activities.MainActivity;
import vn.techkids.freemusic11.databases.TopSongModel;
import vn.techkids.freemusic11.utils.MusicHandler;

/**
 * Created by qklahpita on 12/9/17.
 */
//TODO: check noti when swipe app
public class MusicNotification {
    private static final String TAG = "MusicNotification";

    private static RemoteViews remoteViews;
    public static NotificationCompat.Builder builder;
    public static NotificationManager notificationManager;
    public static String CANCEL_NOTIFICATION_ID = "5";
    public static String NOTIFICATION_CHANNEL_ID = "NOTIFICATION_CHANNEL_ID";
    public static String NOTIFICATION_CHANNEL_NAME = "NOTIFICATION_CHANNEL_NAME";

    private static final int NOTIFICATION_ID = 1;

    public static void setupNotification(Context context, TopSongModel topSongModel) {

        remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.notification_layout);
        remoteViews.setTextViewText(R.id.tv_song, topSongModel.song);
        remoteViews.setTextViewText(R.id.tv_singer, topSongModel.singer);
        remoteViews.setImageViewResource(R.id.iv_play, R.drawable.ic_pause_black_24dp);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        builder = new NotificationCompat.Builder(context);


        notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

//Notification Channel
        CharSequence channelName = NOTIFICATION_CHANNEL_NAME;
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel notificationChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(notificationChannel);

        }

        builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_music_note_black_24dp)
               // .setDeleteIntent(createOnDismissedIntent(context, NOTIFICATION_ID))
                .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})

                .setSound(null)
                .setChannelId(NOTIFICATION_CHANNEL_ID)
                .setContent(remoteViews)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setOngoing(true);

//        builder.setSmallIcon(R.drawable.ic_music_note_black_24dp)
//                .setContent(remoteViews)
//                .setContentIntent(pendingIntent)
//                .setOngoing(true);

        if (topSongModel.smallImage != null) {
            Picasso.with(context).load(topSongModel.smallImage).transform(new CropCircleTransformation())
                    .into(remoteViews, R.id.iv_song, NOTIFICATION_ID, builder.build());
        } else {
            Picasso.with(context).load(topSongModel.offlineImage).transform(new CropCircleTransformation())
                    .into(remoteViews, R.id.iv_song, NOTIFICATION_ID, builder.build());
        }
        setOnClickPlayPause(context);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public static void updateNotification() {
        if (!MusicHandler.hybridMediaPlayer.isPlaying()) {
            remoteViews.setImageViewResource(R.id.iv_play, R.drawable.ic_play_arrow_black_24dp);
            builder.setOngoing(false);
        } else {
            remoteViews.setImageViewResource(R.id.iv_play, R.drawable.ic_pause_black_24dp);
            builder.setOngoing(true);
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private static void setOnClickPlayPause(Context context) {

        Intent intent = new Intent(context, MusicService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

        remoteViews.setOnClickPendingIntent(R.id.iv_play, pendingIntent);
    }
//    private static PendingIntent createOnDismissedIntent(Context context, int notificationId) {
//        Intent intent = new Intent(context, MusicService.class);
//        intent.setAction(CANCEL_NOTIFICATION_ID);
//        PendingIntent pendingIntent =
//                PendingIntent.getService(context.getApplicationContext(),notificationId, intent, 0);
//        return pendingIntent;
//    }
}
