package ulukmyrzategin.auapp.ui.vacancies;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import ulukmyrzategin.auapp.R;
import ulukmyrzategin.auapp.StartApplication;
import ulukmyrzategin.auapp.data.db.SQLiteHelper;
import ulukmyrzategin.auapp.data.model.VacanciesModel;
import ulukmyrzategin.auapp.ui.BaseActivity;
import ulukmyrzategin.auapp.ui.details.DialogTelephoneFragment;
import ulukmyrzategin.auapp.utils.AndroidUtils;
import ulukmyrzategin.auapp.utils.Constants;
import ulukmyrzategin.auapp.utils.PermissionUtils;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_NEUTRAL;
import static android.content.DialogInterface.BUTTON_POSITIVE;

/**
 * Created by $TheSusanin on 22.08.2018.
 */
public class VacanciesAboutActivity extends BaseActivity implements View.OnClickListener, DialogInterface.OnClickListener {

    private TextView mTextViewHeader, mTextViewProfile, mTextViewDate, mTextViewSalary, mTextViewSiteAddress, mTextViewTelephone, mTextViewBody;
    private LinearLayout mButtonPrevious, mButtonNext;
    private CheckBox mCheckBoxElected;
    private RelativeLayout mButtonCall;
    private FrameLayout mFrameLayoutPrevious, mFrameLayoutNext;
    private TextView mTextViewPrevious, mTextViewNext;
    private VacanciesModel mVacanciesModel;
    private ArrayList<VacanciesModel> mModelArrayList;
    private DialogTelephoneFragment mDialogTelephoneFragment;
    private int mPosition;
    private ArrayList<String> mTelephones;
    private String mTelephone, mVacancyTelephone;
    private SQLiteHelper mSQLiteHelper;
    private Intent mIntentCall, mIntentDial;

    @Override
    protected int getViewLayout() {
        return R.layout.activity_vacancies_about;
    }

    @Override
    protected int getToolvarId() {
        return R.id.toolbar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewLayout());

        getToolbar(getResources().getString(R.string.vacancies), true);
        mSQLiteHelper = StartApplication.getApp(getApplicationContext()).getSQLiteHelper();

        initialize();
        getIntentData();
        showButtons();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonPrevious:
                previousVacancy();
                break;

            case R.id.buttonNext:
                nextVacancy();
                break;

            case R.id.buttonCall:
                pushTelephone();
                break;
        }
    }

    private void initialize() {
        mTextViewHeader = findViewById(R.id.textViewHeader);
        mTextViewProfile = findViewById(R.id.textViewProfile);
        mTextViewDate = findViewById(R.id.textViewDate);
        mTextViewSalary = findViewById(R.id.textViewSalary);
        mTextViewSiteAddress = findViewById(R.id.textViewSiteAddress);
        mTextViewTelephone = findViewById(R.id.textViewTelephone);
        mTextViewBody = findViewById(R.id.textViewBody);
        mCheckBoxElected = findViewById(R.id.checkBoxFeatured);

        mFrameLayoutPrevious = findViewById(R.id.frameLayoutPrevious);
        mFrameLayoutNext = findViewById(R.id.frameLayoutNext);
        mTextViewPrevious = findViewById(R.id.textViewPrevious);
        mTextViewNext = findViewById(R.id.textViewNext);

        mButtonPrevious = findViewById(R.id.buttonPrevious);
        mButtonNext = findViewById(R.id.buttonNext);
        mButtonCall = findViewById(R.id.buttonCall);

        mButtonPrevious.setOnClickListener(this);
        mButtonNext.setOnClickListener(this);
        mButtonCall.setOnClickListener(this);

    }

    private void showButtons() {
        if (mModelArrayList.size() == 2) {
            if (mPosition == 0) {
                setInvisiblePrevious();
                setVisibleNext();
                return;
            }

            if (mPosition == 1) {
                setVisiblePrevious();
                setInvisibleNext();
            }
        } else if (mModelArrayList.size() == 1) {
            setInvisiblePrevious();
            setInvisibleNext();
        } else {
            if (mPosition == 0) {
                setInvisiblePrevious();
                return;
            }
            if (mPosition == mModelArrayList.size() - 1) {
                setInvisibleNext();
            } else {
                setVisibleNext();
                setVisiblePrevious();
            }
        }

    }

    private void setInvisiblePrevious() {
        mFrameLayoutPrevious.setVisibility(View.INVISIBLE);
        mTextViewPrevious.setVisibility(View.INVISIBLE);
        mButtonPrevious.setClickable(false);
    }

    private void setInvisibleNext() {
        mFrameLayoutNext.setVisibility(View.INVISIBLE);
        mTextViewNext.setVisibility(View.INVISIBLE);
        mButtonNext.setClickable(false);
    }

    private void setVisiblePrevious() {
        mFrameLayoutPrevious.setVisibility(View.VISIBLE);
        mTextViewPrevious.setVisibility(View.VISIBLE);
        mButtonPrevious.setClickable(true);
    }

    private void setVisibleNext() {
        mFrameLayoutNext.setVisibility(View.VISIBLE);
        mTextViewNext.setVisibility(View.VISIBLE);
        mButtonNext.setClickable(true);
    }

    private void updateData() {
        mVacanciesModel = mModelArrayList.get(mPosition);
        mTextViewHeader.setText(mVacanciesModel.getHeader());
        mTextViewProfile.setText(mVacanciesModel.getProfile());
        mTextViewDate.setText(formatTextViewDate(mVacanciesModel.getData()));
        setTextViewSalary();
        mTextViewSiteAddress.setText(mVacanciesModel.getSiteAddress());
        setTextViewTelephone();
        mTextViewBody.setText(mVacanciesModel.getBody());
        mSQLiteHelper.saveWatchedVacancies(mVacanciesModel.getPid(), true);

        mCheckBoxElected.setChecked(mSQLiteHelper.getFeaturedVacancy(mVacanciesModel.getPid()));

        mCheckBoxElected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckBoxElected.isChecked()) {
                    mSQLiteHelper.saveFeaturedVacancies(mVacanciesModel, true);
                    AndroidUtils.showLongToast(getApplicationContext(), getResources().getString(R.string.added_to_featured));
                } else {
                    mSQLiteHelper.deleteFeaturedVacancy(mVacanciesModel.getPid());
                    AndroidUtils.showLongToast(getApplicationContext(), getResources().getString(R.string.deleted_from_featured));
                }
            }
        });
    }

    private void previousVacancy() {
        mPosition--;
        showButtons();
        updateData();
    }

    private void nextVacancy() {
        mPosition++;
        showButtons();
        updateData();
    }

    private void getIntentData() {
        if (getIntent() != null) {
            // mVacanciesModel = getIntent().getParcelableArrayListExtra("vacancy_models");
            mModelArrayList = getIntent().getParcelableArrayListExtra("vacancy_models");
            mPosition = getIntent().getIntExtra("position", 0);
            updateData();
        }
    }

    private void setTextViewSalary() {
        if (mVacanciesModel.getSalary().equals("")) {
            mTextViewSalary.setText(R.string.treaty);
        } else {
            mTextViewSalary.setText(mVacanciesModel.getSalary());
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

    private void setTextViewTelephone() {
        mVacancyTelephone = mVacanciesModel.getTelephone().trim();
        if (mVacancyTelephone.equals("")) {
            mTextViewTelephone.setText(R.string.undefined);
            mButtonCall.setVisibility(View.INVISIBLE);
        } else if (mVacancyTelephone.contains("@") && !mVacancyTelephone.contains(" ") && !mVacancyTelephone.contains(";")) {
            mButtonCall.setVisibility(View.INVISIBLE);
            mTextViewTelephone.setText(mVacanciesModel.getTelephone());
        } else {
            mTextViewTelephone.setText(mVacanciesModel.getTelephone());
            mButtonCall.setVisibility(View.VISIBLE);
        }
    }

    public void pushTelephone() {

        mTelephone = mVacanciesModel.getTelephone();
        mTelephones = new ArrayList<>();

        if (mTelephone.contains("; ")) {
            mTelephones = getListTelephone();
            mDialogTelephoneFragment = new DialogTelephoneFragment();

            Bundle bundle = new Bundle();
            bundle.putStringArrayList("telephones", mTelephones);
            mDialogTelephoneFragment.setArguments(bundle);
            mDialogTelephoneFragment.show(getSupportFragmentManager(), "telephone");
        } else if (mTelephone.contains("@") && mTelephone.contains(" ") && !mTelephone.contains(";")) {
            mTelephone = divideTelAndMail();
            callServiceAppPermissionForCalling();
        } else {
            mButtonCall.setVisibility(View.VISIBLE);
            callServiceAppPermissionForCalling();

        }
    }

    private void callPhone(String telephone) {
        mIntentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + telephone));
        PermissionUtils.getPermissionForCalling(VacanciesAboutActivity.this, VacanciesAboutActivity.this, mIntentCall);
    }

    private void dialPhone(String tel) {
        mIntentDial = new Intent(Intent.ACTION_DIAL);
        mIntentDial.setData(Uri.parse("tel:" + tel));
        startActivity(mIntentDial);
    }

    private void callServiceAppPermissionForCalling() {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.ask_permission))
                .setMessage(getResources().getString(R.string.give_permission))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.call), this)
                .setNeutralButton(getResources().getString(R.string.just_dial), this)
                .setNegativeButton(getResources().getString(R.string.no), this)
                .create()
                .show();
    }


    @Override
    public void onClick(DialogInterface di, int i) {
        switch (i) {
            case BUTTON_POSITIVE:
                callPhone(mTelephone);
                di.dismiss();
                break;

            case BUTTON_NEUTRAL:
                dialPhone(mTelephone);
                di.dismiss();
                break;

            case BUTTON_NEGATIVE:
                di.dismiss();
                break;
        }
    }

    private ArrayList<String> getListTelephone() {
        String[] splittingTelephone = mTelephone.split("; ");
        String[] splittingTelAndMail = new String[]{};
        ArrayList<String> telephonesList = new ArrayList<>();

        for (int i = 0; i < splittingTelephone.length; i++) {
            if (splittingTelephone[i].contains(" ")) {
                splittingTelAndMail = splittingTelephone[i].split(" ");
            } else if (!splittingTelephone[i].contains("@")) {
                telephonesList.add(splittingTelephone[i]);
            }
        }
        for (int i = 0; i < splittingTelAndMail.length; i++) {
            if (!splittingTelAndMail[i].contains("@")) {
                telephonesList.add(splittingTelAndMail[i]);
            }
        }
        return telephonesList;
    }

    private String divideTelAndMail() {
        String normalTelephone = "";
        String[] splitMail = mTelephone.split(" ");

        for (int i = 0; i < splitMail.length; i++) {
            if (!splitMail[i].contains("@")) {
                normalTelephone = splitMail[i];
            }
        }
        return normalTelephone;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case Constants.REQUEST_PHONE_CALL:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(mIntentCall);
                }
                break;
        }
    }
}
