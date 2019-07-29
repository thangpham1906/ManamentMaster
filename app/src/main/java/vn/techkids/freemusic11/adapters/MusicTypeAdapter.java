package vn.techkids.freemusic11.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.techkids.freemusic11.R;
import vn.techkids.freemusic11.activities.MainActivity;
import vn.techkids.freemusic11.databases.MusicTypeModel;
import vn.techkids.freemusic11.events.OnClickMusicTypeEvent;
import vn.techkids.freemusic11.fragments.DownloadFragment;
import vn.techkids.freemusic11.fragments.TopSongFragment;
import vn.techkids.freemusic11.utils.Utils;

/**
 * Created by qklahpita on 11/18/17.
 */

public class MusicTypeAdapter extends RecyclerView.Adapter<MusicTypeAdapter.MusicTypeViewHolder> {
    List<MusicTypeModel> musicTypeModelList;
    Context context;

    public MusicTypeAdapter(List<MusicTypeModel> musicTypeModelList, Context context) {
        this.musicTypeModelList = musicTypeModelList;
        this.context = context;
    }

    //create view
    @Override
    public MusicTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_music_type, parent, false);

        return new MusicTypeViewHolder(view);
    }

    //set data
    @Override
    public void onBindViewHolder(MusicTypeViewHolder holder, int position) {
        holder.setData(musicTypeModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return musicTypeModelList.size();
    }

    public class MusicTypeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_music_type) ImageView imageView;
        @BindView(R.id.tv_music_type) TextView textView;

        View view;

        public MusicTypeViewHolder(View itemView) {
            super(itemView);

            view = itemView;
            ButterKnife.bind(this, itemView);
        }

        public void setData(final MusicTypeModel musicTypeModel) {
            Picasso.with(context).load(musicTypeModel.imageID).into(imageView);
            textView.setText(musicTypeModel.key);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.openFragment( ((MainActivity) context).getSupportFragmentManager(),
                            R.id.rl_main,
                            new TopSongFragment());

                    EventBus.getDefault().postSticky(new OnClickMusicTypeEvent(musicTypeModel));
                }
            });
        }
    }


}
