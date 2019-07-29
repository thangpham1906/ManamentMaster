package vn.techkids.freemusic11.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListener;
import com.thin.downloadmanager.ThinDownloadManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import vn.techkids.freemusic11.R;
import vn.techkids.freemusic11.databases.TopSongModel;

/**
 * Created by qklahpita on 12/14/17.
 */

public class DownloadHandler {
    private static final String TAG = "DownloadHandler";
    public static void downloadSong(final Context context, TopSongModel topSongModel) {
        topSongModel.url = "https://aredir.nixcdn.com/NhacCuaTui952/ChamKheTimEmMotChutThoiCover-BuiHaMy-5232467.mp3?st=6fAbonumtA6IUeSM1yNL5g&e=1513240883";
        Log.e("ThangPham","topsonng = "+topSongModel.url);
        DownloadRequest downloadRequest = new DownloadRequest(Uri.parse(topSongModel.url))
                .setDestinationURI(Uri.parse(context.getExternalCacheDir().toString() + "/"
                        + topSongModel.song +"---"+ topSongModel.singer +".mp3"))
                .setDownloadContext(context)
                .setDownloadListener(new DownloadStatusListener() {
                    @Override
                    public void onDownloadComplete(int id) {
                        Toast.makeText(context, "Download completed", Toast.LENGTH_SHORT).show();
                        Log.e("ThangPham","download thanh cong");
                    }

                    @Override
                    public void onDownloadFailed(int id, int errorCode, String errorMessage) {
                        Toast.makeText(context, "Download failed", Toast.LENGTH_SHORT).show();
                        Log.e("ThangPham","download that bai " + errorMessage);
                        Log.d(TAG, "onDownloadFailed: " + errorMessage);
                    }

                    @Override
                    public void onProgress(int id, long totalBytes, long downloadedBytes, int progress) {

                    }
                });

        ThinDownloadManager thinDownloadManager = new ThinDownloadManager();
        thinDownloadManager.add(downloadRequest);
    }

    public static List<TopSongModel> getTopSongs(Context context) {
        List<TopSongModel> downloadedSongs = new ArrayList<>();

        File file = new File(context.getExternalCacheDir().toString());

        for (File f : file.listFiles()) {
            if (f.isFile()) {
                String name = f.getName();
                String nameWithoutMp3 = name.substring(0, name.lastIndexOf("."));
                String[] songInfo = nameWithoutMp3.split("---");

                TopSongModel downloadedSong = new TopSongModel(
                        songInfo[0],
                        songInfo[1],
                        context.getExternalCacheDir().toString() + "/" + name,
                        R.drawable.offline_music);

                downloadedSongs.add(downloadedSong);
            }
        }

        return downloadedSongs;
    }

    public static boolean checkIfDownloaded(TopSongModel topSongModel, Context context) {
        List<TopSongModel> topSongModels = getTopSongs(context);
        for (TopSongModel topSongModel1 : topSongModels) {
            if ((topSongModel1.song + topSongModel1.singer).equals(
                    topSongModel.song + topSongModel.singer)) {
                return true;
            }
        }
        return false;
    }
}
