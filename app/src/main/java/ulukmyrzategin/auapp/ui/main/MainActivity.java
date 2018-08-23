package ulukmyrzategin.auapp.ui.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import ulukmyrzategin.auapp.R;
import ulukmyrzategin.auapp.ui.BaseActivity;
import ulukmyrzategin.auapp.ui.search.DialogSearchFragment;

public class MainActivity extends BaseActivity {

    DialogSearchFragment mSearchfragment;

    @Override
    protected int getViewLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected int getToolvarId() {
        return R.id.toolbar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewLayout());

        getToolbar(getResources().getString(R.string.vacancies), false);
        getDrawer();
        getTablayout();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        mSearchfragment = new DialogSearchFragment();
        mSearchfragment.show(getSupportFragmentManager(), "fragment");
        return super.onOptionsItemSelected(item);
    }
}
