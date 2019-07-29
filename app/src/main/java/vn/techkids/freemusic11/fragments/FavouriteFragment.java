package vn.techkids.freemusic11.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.techkids.freemusic11.R;
import vn.techkids.freemusic11.adapters.MusicTypeAdapter;
import vn.techkids.freemusic11.databases.DatabaseHandler;
import vn.techkids.freemusic11.events.OnUpdateRvFav;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {

    @BindView(R.id.rv_fav)
    RecyclerView rvFav;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        ButterKnife.bind(this, view);
        Log.e("ThangPham","FavouriteFragment");
        rvFav.setAdapter(new MusicTypeAdapter(DatabaseHandler.getFavourites(), getContext()));
        rvFav.setLayoutManager(new GridLayoutManager(getContext(), 2));

        EventBus.getDefault().register(this);

        return view;
    }

    @Subscribe(sticky = true)
    public void update(OnUpdateRvFav onUpdateRvFav) {
        rvFav.setAdapter(new MusicTypeAdapter(DatabaseHandler.getFavourites(), getContext()));
    }
}
