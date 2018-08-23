package ulukmyrzategin.auapp.ui.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import ulukmyrzategin.auapp.R;
import ulukmyrzategin.auapp.ui.BaseActivity;

/**
 * Created by $TheSusanin on 22.08.2018.
 */
public class SearchActivity extends BaseActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewLayout());

        getToolbar(getResources().getString(R.string.search), true);

        switchFragment(new SearchListFragment());
    }

    @Override
    protected int getViewLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected int getToolvarId() {
        return R.id.toolbar;
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
}
