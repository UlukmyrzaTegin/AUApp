package ulukmyrzategin.auapp.ui.featured;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import java.util.ArrayList;

import ulukmyrzategin.auapp.R;
import ulukmyrzategin.auapp.StartApplication;
import ulukmyrzategin.auapp.data.db.SQLiteHelper;
import ulukmyrzategin.auapp.data.model.VacanciesModel;
import ulukmyrzategin.auapp.ui.BaseActivity;

/**
 * Created by $TheSusanin on 22.08.2018.
 */
public class FeaturedActivity extends BaseActivity {

    private SQLiteHelper mSQLiteHelper;
    private ArrayList<VacanciesModel> mVacanciesModels;

    @Override
    protected int getViewLayout() {
        return R.layout.activity_featured;
    }

    @Override
    protected int getToolvarId() {
        return R.id.toolbar;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewLayout());

        getToolbar(getResources().getString(R.string.elected), true);

        mSQLiteHelper = StartApplication.getApp(getApplicationContext()).getSQLiteHelper();
        mVacanciesModels = mSQLiteHelper.getFeaturedVacancies();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void readyFragments() {
        if (mVacanciesModels.size() ==0) {
            switchFragment(new EmptyFragment());
        } else {
            switchFragment(new FeaturedFragment());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        readyFragments();
    }
}
