package ulukmyrzategin.auapp.ui;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

import ulukmyrzategin.auapp.BuildConfig;
import ulukmyrzategin.auapp.ui.main.PagerStateAdapter;
import ulukmyrzategin.auapp.R;
import ulukmyrzategin.auapp.data.model.TabPagerItem;
import ulukmyrzategin.auapp.ui.featured.FeaturedActivity;
import ulukmyrzategin.auapp.ui.suitable.SuitableFragment;
import ulukmyrzategin.auapp.ui.vacancies.VacanciesFragment;

/**
 * Created by $TheSusanin on 22.08.2018.
 */
public abstract class BaseActivity extends AppCompatActivity implements Drawer.OnDrawerItemClickListener {

    protected abstract int getViewLayout();
    protected abstract int getToolvarId();

    private Drawer mDrawer;
    private Toolbar mToolbar;
    private AccountHeader mAccountHeader;
    private ProfileDrawerItem mProfileDrawerItem;
    private PrimaryDrawerItem mFeaturedItem, mExitItem;

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private ArrayList<TabPagerItem> mTabs;

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        setContentView(getViewLayout());
    }

    protected void getDrawer() {

        mProfileDrawerItem = new ProfileDrawerItem()
                .withName(R.string.header)
                .withEmail(String.format(getResources().getString(R.string.version_program), BuildConfig.VERSION_NAME))
                .withIcon(R.drawable.ic_logo);

        mAccountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.colorPurpleLight)
                .addProfiles(mProfileDrawerItem)
                .withSelectionListEnabledForSingleProfile(false)
                .build();

        mFeaturedItem = new PrimaryDrawerItem()
                .withName(R.string.selected_vacancies)
                .withIdentifier(1)
                .withIcon(R.drawable.ic_star_black_24dp);

        mExitItem = new PrimaryDrawerItem()
                .withName(R.string.exit)
                .withIdentifier(2)
                .withIcon(R.drawable.ic_exit_to_app_black_24dp);

        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withOnDrawerItemClickListener(this)
                .withAccountHeader(mAccountHeader)
                .withSelectedItem(-1)
                .withToolbar(mToolbar)
                .addDrawerItems(mFeaturedItem, new DividerDrawerItem(), mExitItem)
                .build();
    }

    protected void getToolbar(String title, boolean back) {
        mToolbar = findViewById(getToolvarId());
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(back);
    }

    protected void getTablayout() {
        mTabs = new ArrayList<>();

        mTabs.add(new TabPagerItem(new VacanciesFragment(), getResources().getString(R.string.vacancies_of_the_day)));
        mTabs.add(new TabPagerItem(new SuitableFragment(), getResources().getString(R.string.suitable)));

        mViewPager = findViewById(R.id.viewPager);
        mTabLayout = findViewById(R.id.tabLayout);

        PagerStateAdapter adapter = new PagerStateAdapter(getSupportFragmentManager(), mTabs);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

        switch ((int) drawerItem.getIdentifier()){
            case 1:
             startActivity(new Intent(this, FeaturedActivity.class));
              mDrawer.setSelection(-1);
                break;

            case 2:
                finish();
                break;

        }
        return false;
    }

    protected void switchFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}
