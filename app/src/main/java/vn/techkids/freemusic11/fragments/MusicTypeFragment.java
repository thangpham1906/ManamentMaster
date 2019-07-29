package vn.techkids.freemusic11.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.techkids.freemusic11.R;
import vn.techkids.freemusic11.adapters.MusicTypeAdapter;
import vn.techkids.freemusic11.databases.DatabaseHandler;
import vn.techkids.freemusic11.databases.MusicTypeModel;
import vn.techkids.freemusic11.networks.MusicInterface;
import vn.techkids.freemusic11.networks.MusicTypesResponseJSON;
import vn.techkids.freemusic11.networks.RetrofitInstance;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicTypeFragment extends Fragment {
    @BindView(R.id.rv_music_types)
    RecyclerView rvMusicType;

    private List<MusicTypeModel> musicTypeModelList = new ArrayList<>();
    private Context context;
    private MusicTypeAdapter musicTypeAdapter;

    public MusicTypeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music_type, container, false);
        Log.e("ThangPham","MusicTypeFragment");
        ButterKnife.bind(this, view);
        context = getContext();

        musicTypeAdapter = new MusicTypeAdapter(musicTypeModelList, getContext());
        rvMusicType.setAdapter(musicTypeAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                context,
                2
        );
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position % 3 == 0 ? 2 : 1) ;
            }
        });
        rvMusicType.setLayoutManager(gridLayoutManager);
        loadData();
//        if (DatabaseHandler.getMusicTypes().size() == 0) {
//            loadData();
//        } else {
//            musicTypeModelList.addAll(DatabaseHandler.getMusicTypes());
////            musicTypeModelList = DatabaseHandler.getMusicTypes();
//            //clear, remove, add, addAll
//            musicTypeAdapter.notifyDataSetChanged();
//        }

        return view;
    }

    private void loadData() {

        MusicInterface musicInterface =
                RetrofitInstance.getInstance().create(MusicInterface.class);
        musicInterface.getMusicTypes().enqueue(new Callback<MusicTypesResponseJSON>() {
            @Override
            public void onResponse(Call<MusicTypesResponseJSON> call, Response<MusicTypesResponseJSON> response) {
                List<MusicTypesResponseJSON.SubObjectJSON> list
                        = response.body().subgenres;
                for (MusicTypesResponseJSON.SubObjectJSON subObjectJSON : list) {
                    MusicTypeModel musicTypeModel = new MusicTypeModel();
                    musicTypeModel.id = subObjectJSON.id;
                    musicTypeModel.key = subObjectJSON.translation_key;
                    if(subObjectJSON.translation_key.equals("all"))
                    {
                        Log.e("ThangPham","all");
                    }
                    if(subObjectJSON.translation_key.equals("all ")){
                        Log.e("ThangPham","all222");
                    }
                    musicTypeModel.imageID = context.getResources().getIdentifier(
                            "genre_x2_" + musicTypeModel.id,
                            "raw",
                            context.getPackageName()
                    );

                    musicTypeModelList.add(musicTypeModel);
                    DatabaseHandler.addMusicType(musicTypeModel);
                }
                musicTypeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MusicTypesResponseJSON> call, Throwable t) {
                Toast.makeText(context, "No connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
