package ulukmyrzategin.auapp.ui.vacancies;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import ulukmyrzategin.auapp.R;
import ulukmyrzategin.auapp.StartApplication;
import ulukmyrzategin.auapp.data.db.SQLiteHelper;
import ulukmyrzategin.auapp.data.model.VacanciesModel;
import ulukmyrzategin.auapp.utils.AndroidUtils;

/**
 * Created by $TheSusanin on 22.08.2018.
 */
public class VacanciesAdapter extends RecyclerView.Adapter<VacanciesAdapter.ViewHolder> {

    private ArrayList<VacanciesModel> mModelArrayList;
    private VacanciesModel mVacanciesModel;
    private VacanciesAdapterCallback mCallback;
    private SQLiteHelper mSQLiteHelper;
    private Context mContext;
    private boolean mIsWatched;
    private Set<Integer> mCheckBoxSet = new HashSet<>();
    private Set<Integer> mWatchedSet = new HashSet<>();

    public VacanciesAdapter(Context context, ArrayList<VacanciesModel> arrayList, VacanciesAdapterCallback callback, boolean isWatched) {
        mContext = context;
        mModelArrayList = arrayList;
        mCallback = callback;
        mIsWatched = isWatched;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_vacancies, parent, false);
        mSQLiteHelper = StartApplication.getApp(parent.getContext()).getSQLiteHelper();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        mVacanciesModel = mModelArrayList.get(position);
        updateData(holder, mVacanciesModel);

        checkBoxController(holder, position);

        holder.mCheckBoxFeatured.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.mCheckBoxFeatured.isChecked()) {
                    mSQLiteHelper.saveFeaturedVacancies(mModelArrayList.get(position), true);
                    AndroidUtils.showLongToast(mContext, mContext.getResources().getString(R.string.added_to_featured));
                } else {
                    mSQLiteHelper.deleteFeaturedVacancy(mModelArrayList.get(position).getPid());
                    AndroidUtils.showLongToast(mContext, mContext.getResources().getString(R.string.deleted_from_featured));
                }
            }
        });
        if (mIsWatched) {
            relativeWatchedController(holder, position);
        }

        holder.mCardViewVacancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onVacanciesClicked(mModelArrayList, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewProfession, mTextViewDate, mTextViewHeader, mTextViewSalary;
        private CheckBox mCheckBoxFeatured;
        private RelativeLayout mRelativeWatched;
        private CardView mCardViewVacancy;

        public ViewHolder(View view) {
            super(view);
            mTextViewProfession = view.findViewById(R.id.textViewProfession);
            mTextViewDate = view.findViewById(R.id.textViewDate);
            mTextViewHeader = view.findViewById(R.id.textViewHeader);
            mTextViewSalary = view.findViewById(R.id.textViewSalary);
            mCheckBoxFeatured = view.findViewById(R.id.checkBoxFeatured);
            mRelativeWatched = view.findViewById(R.id.relativeWatched);
            mCardViewVacancy = view.findViewById(R.id.cardViewVacancy);
        }
    }

    private void updateData(ViewHolder holder, VacanciesModel vacanciesModel){
        setTextViewProfession(holder);
        holder.mTextViewDate.setText(formatTextViewDate(vacanciesModel.getData()));
        holder.mTextViewHeader.setText(vacanciesModel.getHeader());
        setTextViewSalary(holder);
    }

    private void checkBoxController(ViewHolder holder, final int position) {
        holder.mCheckBoxFeatured.setOnCheckedChangeListener(null);
        holder.mCheckBoxFeatured.setChecked(mCheckBoxSet.contains(position));
        holder.mCheckBoxFeatured.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckBoxSet.add(position);
                } else {
                    mCheckBoxSet.remove(position);
                }
            }
        });
        if (mSQLiteHelper.getFeaturedVacancy(mVacanciesModel.getPid())) {
            holder.mCheckBoxFeatured.setChecked(true);
        }
    }

    private void relativeWatchedController(ViewHolder holder, final  int position) {
        holder.mRelativeWatched.setOnFocusChangeListener(null);
        holder.mRelativeWatched.setVisibility(mWatchedSet.contains(position) ? View.VISIBLE : View.GONE);
        if (mSQLiteHelper.isWatchedVacancies(mModelArrayList.get(position).getPid())) {
            holder.mRelativeWatched.setVisibility(View.VISIBLE);
        }
        holder.mRelativeWatched.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    mWatchedSet.add(position);
                } else {
                    mWatchedSet.remove(position);
                }
            }
        });
    }

    private void setTextViewProfession(ViewHolder viewHolder) {
        if (mVacanciesModel.getProfession().equals("Не определено")) {
            viewHolder.mTextViewProfession.setText(mVacanciesModel.getHeader());
        } else {
            viewHolder.mTextViewProfession.setText(mVacanciesModel.getProfession());
        }
    }

    private void setTextViewSalary(ViewHolder viewHolder) {
        if (mVacanciesModel.getSalary().equals("")) {
            viewHolder.mTextViewSalary.setText(R.string.treaty);
        } else {
            viewHolder.mTextViewSalary.setText(mVacanciesModel.getSalary());
        }
    }

    private String formatTextViewDate(String textDate) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "HH:mm dd MMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern, Locale.getDefault());
        Date date;
        String output = null;
        try {
            date = inputFormat.parse(textDate);
            output = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output;
    }
}
