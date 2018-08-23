package ulukmyrzategin.auapp.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ulukmyrzategin.auapp.R;
import ulukmyrzategin.auapp.StartApplication;
import ulukmyrzategin.auapp.data.VacanciesService;
import ulukmyrzategin.auapp.data.db.SQLiteHelper;
import ulukmyrzategin.auapp.data.model.VacanciesModel;
import ulukmyrzategin.auapp.ui.BaseFragment;
import ulukmyrzategin.auapp.ui.vacancies.VacanciesAboutActivity;
import ulukmyrzategin.auapp.ui.vacancies.VacanciesAdapter;
import ulukmyrzategin.auapp.ui.vacancies.VacanciesAdapterCallback;
import ulukmyrzategin.auapp.utils.AndroidUtils;

/**
 * Created by $TheSusanin on 22.08.2018.
 */
public class SearchListFragment extends BaseFragment implements VacanciesAdapterCallback, SwipeRefreshLayout.OnRefreshListener {

    private VacanciesService mService;
    private RecyclerView mRecyclerView;
    private VacanciesAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Context mContext;
    private ArrayList<VacanciesModel> mVacanciesModels = new ArrayList<>();
    private int mPage = 1;
    private FrameLayout mProgressBar;
    private SQLiteHelper mSQLiteHelper;
    private String mSalary, mTerm;


    @Override
    protected int getViewLayout() {
        return R.layout.fragment_vacancies;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() != null) {
            mContext = getActivity().getApplicationContext();
        }

        mProgressBar = view.findViewById(R.id.progressBar);
        mSQLiteHelper = StartApplication.getApp(mContext).getSQLiteHelper();

        getRecyclerView(view);
        setSwipeRefreshLayout(view);
        getVacancies();
    }

    private void getRecyclerView(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setSwipeRefreshLayout(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void getVacancies() {
        mProgressBar.setVisibility(View.VISIBLE);

        mSalary = mSQLiteHelper.getRadioButtons().getSalary();
        mTerm = mSQLiteHelper.getRadioButtons().getRegime();

        mService = StartApplication.getApp(mContext).getService();
        mService.postSearchingVacancies("ulukmyrza", "html", "20", String.valueOf(mPage), mSalary, mTerm)
                .enqueue(new Callback<ArrayList<VacanciesModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<ArrayList<VacanciesModel>> call, @NonNull Response<ArrayList<VacanciesModel>> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            mVacanciesModels.addAll(response.body());
                            mAdapter = new VacanciesAdapter(mContext, mVacanciesModels, SearchListFragment.this, true);
                            mRecyclerView.setAdapter(mAdapter);
                        }
                        mProgressBar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure( @NonNull Call<ArrayList<VacanciesModel>> call, @NonNull Throwable t) {

                        AndroidUtils.showLongToast(mContext, t.getMessage());
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void onVacanciesClicked(final ArrayList<VacanciesModel> vacanciesModels, final int position) {

        startActivity(new Intent(mContext, VacanciesAboutActivity.class).putParcelableArrayListExtra("vacancy_models", vacanciesModels).putExtra("position", position));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        mPage++;
        getVacancies();
        mSwipeRefreshLayout.setRefreshing(false);

    }
}
