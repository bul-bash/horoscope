package com.github.bul_bash.horoscope;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.github.bul_bash.horoscope.data.Data;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return PageFragment.newInstance(i);
    }

    @Override
    public int getCount() {
        return Data.PAGE_COUNT;
    }

        @Override
        public CharSequence getPageTitle(int position) {
            String result;
            try {
                result = Data.getDates().get(5-position);
            }
            catch (Exception e){
                //TODO
                result = "=)";
            }
            return result;
        }
}
