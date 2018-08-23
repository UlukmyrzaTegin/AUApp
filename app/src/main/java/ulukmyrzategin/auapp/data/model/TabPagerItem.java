package ulukmyrzategin.auapp.data.model;

import android.support.v4.app.Fragment;

/**
 * Created by $TheSusanin on 20.08.2018.
 */
public class TabPagerItem {

    private final Fragment mFragment;
    private final CharSequence title;


    public TabPagerItem(Fragment fragment, CharSequence title) {
        mFragment = fragment;
        this.title = title;
    }

    public Fragment getFragment() {
        return  mFragment;
    }

    public CharSequence getTitle() {
        return title;
    }
}
