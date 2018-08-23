package ulukmyrzategin.auapp.ui.featured;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import ulukmyrzategin.auapp.R;
import ulukmyrzategin.auapp.ui.BaseFragment;

/**
 * Created by $TheSusanin on 22.08.2018.
 */
public class EmptyFragment extends BaseFragment {
    @Override
    protected int getViewLayout() {
        return R.layout.fragment_empty;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
