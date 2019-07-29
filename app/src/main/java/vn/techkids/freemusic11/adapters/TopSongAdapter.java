package vn.techkids.freemusic11.adapters;

import android.content.Context;
import android.support.annotation.BinderThread;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import vn.techkids.freemusic11.R;
import vn.techkids.freemusic11.databases.TopSongModel;
import vn.techkids.freemusic11.events.OnClickTopSongEvent;
import vn.techkids.freemusic11.notification.MusicNotification;

/**
 * Created by qklahpita on 11/25/17.
 */

public class TopSongAdapter extends RecyclerView.Adapter<TopSongAdapter.TopSongViewHolder> {
    private Context context;
    private List<TopSongModel> topSongModels;

    public TopSongAdapter(Context context, List<TopSongModel> topSongModels) {
        this.context = context;
        this.topSongModels = topSongModels;
    }

    @Override
    public TopSongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_list_top_song, parent, false);

        return new TopSongViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TopSongViewHolder holder, int position) {
        holder.setData(topSongModels.get(position));
    }

    @Override
    public int getItemCount() {
        return topSongModels.size();
    }

    public class TopSongViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_song)
        ImageView ivSong;
        @BindView(R.id.tv_song)
        TextView tvSong;
        @BindView(R.id.tv_singer)
        TextView tvSinger;

        View view;

        public TopSongViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            view = itemView;
        }

        public void setData(final TopSongModel topSongModel) {
            if (topSongModel.smallImage != null) {
                Picasso.with(context).load(topSongModel.smallImage)
                        .transform(new CropCircleTransformation()).into(ivSong);
            } else {
                Picasso.with(context).load(topSongModel.offlineImage)
                        .transform(new CropCircleTransformation()).into(ivSong);
            }

            tvSong.setText(topSongModel.song);
            tvSinger.setText(topSongModel.singer);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().postSticky
                            (new OnClickTopSongEvent(topSongModel));

                }
            });
        }
    }
}
