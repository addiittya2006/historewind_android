package xyz.addiittya.historewind.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import xyz.addiittya.historewind.fragment.ItemFragment;

/**
 * Created by addiittya on 13/03/17.
 */

public class ScreenPagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUM_PAGES = 10;

    public ScreenPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new ItemFragment();
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
