package vn.techkids.freemusic11.networks;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by qklahpita on 11/25/17.
 */

public class TopSongsResponseJSON {
    public FeedJSON feed;

    public class FeedJSON {
        public List<EntryJSON> entry;

        public class EntryJSON {
            @SerializedName("im:name")
            public NameJSON name;

            @SerializedName("im:image")
            public List<ImageJSON> image;

            @SerializedName("im:artist")
            public ArtistJSON artist;

            public class NameJSON {
                public String label;
            }

            public class ImageJSON {
                public String label;
            }

            public class ArtistJSON {
                public String label;
            }
        }
    }
}
