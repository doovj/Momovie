package com.doovj.momovie;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.doovj.momovie.favoritmovie.FavoritFragment;
import com.doovj.momovie.nowplayingmovie.NowPlayingFragment;
import com.doovj.momovie.upcomingmovie.UpcomingFragment;


public class PagerAdapter extends FragmentPagerAdapter {
    private Context context;
    public static int PAGE_COUNT = 3;
    // private String judulTab[] = new String[] {"Now playing", "Upcoming"};

    PagerAdapter(FragmentManager manager, Context c) {
        super(manager);
        context = c;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NowPlayingFragment();
            case 1:
                return new UpcomingFragment();
            case 2:
                return new FavoritFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.tabnow_playing);
            case 1:
                return context.getString(R.string.tabupcoming_movies);
            case 2:
                return context.getString(R.string.tabfavorit_movies);
        }
        return null;
        //return judulTab[position];
    }
}
