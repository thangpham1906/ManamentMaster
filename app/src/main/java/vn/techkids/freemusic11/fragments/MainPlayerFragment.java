package vn.techkids.freemusic11.fragments;


import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import vn.techkids.freemusic11.R;
import vn.techkids.freemusic11.databases.TopSongModel;
import vn.techkids.freemusic11.events.OnClickTopSongEvent;
import vn.techkids.freemusic11.utils.DownloadHandler;
import vn.techkids.freemusic11.utils.MusicHandler;
import vn.techkids.freemusic11.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainPlayerFragment extends Fragment {

    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_singer_name)
    TextView tvSinger;
    @BindView(R.id.tv_song_name)
    TextView tvSong;
    @BindView(R.id.iv_download)
    ImageView ivDownload;
    @BindView(R.id.iv_song)
    ImageView ivSong;
    @BindView(R.id.tv_duration_time_song)
    TextView tvDuration;
    @BindView(R.id.tv_current_time_song)
    TextView tvCurrent;
    @BindView(R.id.sb_main)
    SeekBar sbMain;
    @BindView(R.id.iv_pre)
    ImageView ivPre;
    @BindView(R.id.iv_next)
    ImageView ivNext;
    @BindView(R.id.fb_play)
    FloatingActionButton fbPlay;

    TopSongModel topSongModel;

    public MainPlayerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_player, container, false);

        setupUI(view);
        EventBus.getDefault().register(this);

        return view;
    }

    private void setupUI(View view) {

        ButterKnife.bind(this, view);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        fbPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicHandler.playPause();
            }
        });

        ivDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DownloadHandler.checkIfDownloaded(topSongModel, getContext())){
                    Toast.makeText(getContext(), "This song has been downloaded!", Toast.LENGTH_SHORT).show();
                } else {
                    DownloadHandler.downloadSong(getContext(), topSongModel);
                    Toast.makeText(getContext(), "Downloading...", Toast.LENGTH_SHORT).show();
                    ivDownload.setImageAlpha(100);
                }

            }
        });
    }

    @Subscribe(sticky = true)
    public void onMiniPlayerClicked(OnClickTopSongEvent onClickTopSongEvent) {
        topSongModel = onClickTopSongEvent.topSongModel;

        tvSong.setText(topSongModel.song);
        tvSinger.setText(topSongModel.singer);

        if (topSongModel.smallImage != null) {
            Picasso.with(getContext()).load(topSongModel.smallImage)
                    .transform(new CropCircleTransformation()).into(ivSong);
        } else {
            Picasso.with(getContext()).load(topSongModel.offlineImage)
                    .transform(new CropCircleTransformation()).into(ivSong);
        }

        if (DownloadHandler.checkIfDownloaded(topSongModel, getContext())){
            ivDownload.setImageAlpha(100);
        } else {
            ivDownload.setImageAlpha(255);
        }

        MusicHandler.updateUIRealtime(sbMain, fbPlay, ivSong, tvCurrent, tvDuration);
    }
}
