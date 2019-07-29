package vn.techkids.freemusic11.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import vn.techkids.freemusic11.R;
import vn.techkids.freemusic11.adapters.ViewPagerAdapter;
import vn.techkids.freemusic11.databases.TopSongModel;
import vn.techkids.freemusic11.events.OnClickTopSongEvent;
import vn.techkids.freemusic11.fragments.MainPlayerFragment;
import vn.techkids.freemusic11.notification.MusicNotification;
import vn.techkids.freemusic11.utils.MusicHandler;
import vn.techkids.freemusic11.utils.Utils;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.tab)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.rl_mini)
    RelativeLayout rlMini;
    @BindView(R.id.sb_mini)
    SeekBar seekBar;
    @BindView(R.id.iv_song)
    ImageView ivSong;
    @BindView(R.id.tv_singer)
    TextView tvSinger;
    @BindView(R.id.tv_song)
    TextView tvSong;
    @BindView(R.id.fb_mini)
    FloatingActionButton fbPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);

        setupUI();
    }

    @Subscribe(sticky = true)

    public void onReceivedTopSong(OnClickTopSongEvent onClickTopSongEvent) {
        TopSongModel topSongModel = onClickTopSongEvent.topSongModel;
        rlMini.setVisibility(View.VISIBLE);

        if (topSongModel.smallImage != null) {
            Picasso.with(this).load(topSongModel.smallImage).transform(
                    new CropCircleTransformation()).into(ivSong);
            MusicHandler.getSearchSong(topSongModel, this);
        } else {
            Picasso.with(this).load(topSongModel.offlineImage).transform(
                    new CropCircleTransformation()).into(ivSong);
            MusicHandler.playMusic(this, topSongModel);
        }

        tvSinger.setText(topSongModel.singer);
        tvSong.setText(topSongModel.song);

        MusicHandler.updateUIRealtime(seekBar, fbPlay, ivSong, null, null);

    }

    private void setupUI() {
        ButterKnife.bind(this);

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_dashboard_black_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_favorite_black_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_file_download_black_24dp));

        tabLayout.getTabAt(0).getIcon().setAlpha(255);
        tabLayout.getTabAt(1).getIcon().setAlpha(100);
        tabLayout.getTabAt(2).getIcon().setAlpha(100);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setAlpha(255);
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setAlpha(100);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        seekBar.setPadding(0, 0, 0, 0);

        fbPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicHandler.playPause();
            }
        });

        rlMini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openFragment(getSupportFragmentManager(), R.id.rl_main_player,
                        new MainPlayerFragment());
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            super.onBackPressed();
        } else {
            moveTaskToBack(true);
        }
    }
}
