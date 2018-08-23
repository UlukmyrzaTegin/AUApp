package ulukmyrzategin.auapp.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import ulukmyrzategin.auapp.data.model.TabPagerItem;

/**
 * Created by $TheSusanin on 20.08.2018.
 */
public class PagerStateAdapter extends FragmentStatePagerAdapter{
    private ArrayList<TabPagerItem> mTabs;

    public PagerStateAdapter(FragmentManager fm, ArrayList<TabPagerItem> mTabs) {
        super(fm);
        this.mTabs = mTabs;
    }

    @Override
    public Fragment getItem(int position) {
        return mTabs.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return mTabs.size();
    }

    public CharSequence getPageTitle(int position) {
        return mTabs.get(position).getTitle();
    }
}
