package vn.techkids.freemusic11.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.techkids.freemusic11.R;
import vn.techkids.freemusic11.adapters.TopSongAdapter;
import vn.techkids.freemusic11.utils.DownloadHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadFragment extends Fragment {
    @BindView(R.id.rv_downloaded)
    RecyclerView rvDownloaded;

    public DownloadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_download, container, false);
        ButterKnife.bind(this, view);
        Log.e("ThangPham","DownloadFragment");
        TopSongAdapter downloadedSongs = new TopSongAdapter(getContext(),
                DownloadHandler.getTopSongs(getContext()));

        rvDownloaded.setAdapter(downloadedSongs);
        rvDownloaded.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

}
