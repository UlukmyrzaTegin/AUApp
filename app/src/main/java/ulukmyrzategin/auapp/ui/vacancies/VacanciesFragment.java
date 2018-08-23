package ulukmyrzategin.auapp.ui.vacancies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ulukmyrzategin.auapp.R;
import ulukmyrzategin.auapp.StartApplication;
import ulukmyrzategin.auapp.data.VacanciesService;
import ulukmyrzategin.auapp.data.db.SQLiteHelper;
import ulukmyrzategin.auapp.data.model.VacanciesModel;
import ulukmyrzategin.auapp.ui.BaseFragment;
import ulukmyrzategin.auapp.utils.AndroidUtils;

/**
 * Created by $TheSusanin on 22.08.2018.
 */
public class VacanciesFragment extends BaseFragment implements VacanciesAdapterCallback, SwipeRefreshLayout.OnRefreshListener {

    private VacanciesService mService;
    private RecyclerView mRecyclerView;
    private VacanciesAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Context mContext;
    private ArrayList<VacanciesModel> mVacanciesModels = new ArrayList<>();
    private int mPage =1;
    private FrameLayout mProgressBar;
    private SQLiteHelper mSQLiteHelper;


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
        setSwipyRefreshLayout(view);
        internetChecking();

    }

    private void getRecyclerView(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setSwipyRefreshLayout(View view) {

        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void getVacancies() {

        mProgressBar.setVisibility(View.VISIBLE);

        mService = StartApplication.getApp(mContext).getService();
        mService.postVacancies("ulukmyrza", "html", "20", String.valueOf(mPage))
                .enqueue(new Callback<List<VacanciesModel>>() {
                    @Override
                    public void onResponse(Call<List<VacanciesModel>> call, Response<List<VacanciesModel>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (mPage == 1) {
                                mSQLiteHelper.saveListWithoutInternet(response.body());
                            }

                            mVacanciesModels.addAll(response.body());
                            mAdapter = new VacanciesAdapter(mContext, mVacanciesModels, VacanciesFragment.this, true);
                            mRecyclerView.setAdapter(mAdapter);
                        }
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<List<VacanciesModel>> call, Throwable t) {
                        AndroidUtils.showLongToast(mContext, t.getMessage());
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void internetChecking() {
        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                getVacancies();
            } else {
                if (mVacanciesModels != null && mSQLiteHelper.getListWithoutInternet() != null) {
                    mVacanciesModels = mSQLiteHelper.getListWithoutInternet();
                    mAdapter = new VacanciesAdapter(mContext, mVacanciesModels, VacanciesFragment.this, true);
                    mRecyclerView.setAdapter(mAdapter);
                }

                AndroidUtils.showLongToast(mContext, getResources().getString(R.string.no_internet));
            }
        }
    }

    @Override
    public void onRefresh() {
        mPage++;
        getVacancies();
        mSwipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onVacanciesClicked(ArrayList<VacanciesModel> vacanciesModels, int position) {
        startActivity(new Intent(mContext, VacanciesAboutActivity.class).putParcelableArrayListExtra("vacancy_models", vacanciesModels).putExtra("position", position));
    }
}
