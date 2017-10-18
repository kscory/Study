package com.example.kyung.mapandnetwork;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.kyung.mapandnetwork.addContents.TempFragment;
import com.example.kyung.mapandnetwork.hospitalmap.MapsActivity;

/**
 * Created by Kyung on 2017-10-18.
 */

public class FrgPagerAdapter extends FragmentStatePagerAdapter {

    private static final int COUNT=2;

    public FrgPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return new TempFragment();
            default:
                return new MapsActivity();
        }
    }

    @Override
    public int getCount() {
        return COUNT;
    }
}
