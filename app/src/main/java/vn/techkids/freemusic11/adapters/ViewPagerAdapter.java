package vn.techkids.freemusic11.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import vn.techkids.freemusic11.fragments.DownloadFragment;
import vn.techkids.freemusic11.fragments.FavouriteFragment;
import vn.techkids.freemusic11.fragments.MusicTypeFragment;
import vn.techkids.freemusic11.fragments.NhapFragment;

/**
 * Created by qklahpita on 11/18/17.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new MusicTypeFragment();
            case 1: return new FavouriteFragment();
            case 2: return new DownloadFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
