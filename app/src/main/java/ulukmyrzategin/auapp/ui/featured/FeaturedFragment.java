package ulukmyrzategin.auapp.ui.featured;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import ulukmyrzategin.auapp.R;
import ulukmyrzategin.auapp.StartApplication;
import ulukmyrzategin.auapp.data.db.SQLiteHelper;
import ulukmyrzategin.auapp.data.model.VacanciesModel;
import ulukmyrzategin.auapp.ui.BaseFragment;
import ulukmyrzategin.auapp.ui.vacancies.VacanciesAboutActivity;
import ulukmyrzategin.auapp.ui.vacancies.VacanciesAdapter;
import ulukmyrzategin.auapp.ui.vacancies.VacanciesAdapterCallback;

/**
 * Created by $TheSusanin on 22.08.2018.
 */
public class FeaturedFragment extends BaseFragment implements VacanciesAdapterCallback {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private SQLiteHelper mSQLiteHelper;
    private ArrayList<VacanciesModel> mVacanciesModels = new ArrayList<>();
    private VacanciesAdapter mAdapter;

    @Override
    protected int getViewLayout() {
        return R.layout.fragment_featured;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() != null) {
            mContext = getActivity().getApplicationContext();
        }
        mSQLiteHelper = StartApplication.getApp(mContext).getSQLiteHelper();

        getRecyclerView(view);
    }

    private void getRecyclerView(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onVacanciesClicked(ArrayList<VacanciesModel> vacanciesModels, int position) {
        startActivity(new Intent(mContext, VacanciesAboutActivity.class).putParcelableArrayListExtra("vacancy_models", vacanciesModels).putExtra("position", position));
    }

    @Override
    public void onResume() {
        super.onResume();

        getVacancies();
    }

    private void getVacancies(){
        mVacanciesModels = mSQLiteHelper.getFeaturedVacancies();
        mAdapter = new VacanciesAdapter(mContext, mVacanciesModels, FeaturedFragment.this, false);
        mRecyclerView.setAdapter(mAdapter);
    }
}
