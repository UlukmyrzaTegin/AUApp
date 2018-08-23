package ulukmyrzategin.auapp.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import ulukmyrzategin.auapp.R;
import ulukmyrzategin.auapp.StartApplication;
import ulukmyrzategin.auapp.data.db.SQLiteHelper;
import ulukmyrzategin.auapp.data.model.SearchsModel;
import ulukmyrzategin.auapp.ui.BaseFragmentDialog;

/**
 * Created by $TheSusanin on 22.08.2018.
 */
public class DialogSearchFragment extends BaseFragmentDialog implements View.OnClickListener {

    private RadioGroup mRadioGroupRegimeFirst, mRadioGroupRegimeSecond, mRadioGroupSalaryFirst, mRadioGroupSalarySecond;
    private RadioButton mRadioButtonRegimeAny, mRadioButtonSalaryAny, mCheckedRadioButtonRegime, mCheckedRadioButtonSalary;
    private Button mButtonReset, mButtonSearch;
    private SearchsModel mSearchsModel = new SearchsModel();
    private SQLiteHelper mSQLiteHelper;
    private boolean mFlagRegime = true;
    private boolean mFlagSalary = true;

    @Override
    protected int getViewLayout() {
        return R.layout.fragment_dialog_search;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSQLiteHelper = StartApplication.getApp(view.getContext()).getSQLiteHelper();
        if (mSQLiteHelper != null) {
            mSearchsModel = mSQLiteHelper.getRadioButtons();
        }

        removeDialogToolbar();
        initialize(view);

        mRadioButtonRegimeAny.setChecked(true);
        mRadioButtonSalaryAny.setChecked(true);
    }

    private void initialize(View view) {
        mRadioGroupRegimeFirst = view.findViewById(R.id.radioGroupRegimeFirst);
        mRadioGroupRegimeSecond = view.findViewById(R.id.radioGroupRegimeSecond);
        mRadioGroupSalaryFirst = view.findViewById(R.id.radioGroupSalaryFirst);
        mRadioGroupSalarySecond = view.findViewById(R.id.radioGroupSalarySecond);

        mRadioButtonRegimeAny = view.findViewById(R.id.radioButtonRegimeAny);
        mRadioButtonSalaryAny = view.findViewById(R.id.radioButtonSalaryAny);

        mButtonReset = view.findViewById(R.id.buttonReset);
        mButtonSearch = view.findViewById(R.id.buttonSearch);

        mRadioGroupRegimeFirst.setOnCheckedChangeListener(radioGroupRegimeFirstListener);
        mRadioGroupRegimeSecond.setOnCheckedChangeListener(radioGroupRegimeSecondListener);
        mRadioGroupSalaryFirst.setOnCheckedChangeListener(radioGroupSalaryFirstListener);
        mRadioGroupSalarySecond.setOnCheckedChangeListener(radioGroupSalarySecondListener);

        mButtonReset.setOnClickListener(this);
        mButtonSearch.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.buttonReset:
                resetButtons();
                break;

            case R.id.buttonSearch:
                startActivity(new Intent(getContext(), SearchActivity.class));
                dismiss();
                break;
        }

    }

    private void resetButtons() {
        mRadioGroupRegimeFirst.clearCheck();
        mRadioGroupRegimeSecond.clearCheck();
        mRadioGroupSalaryFirst.clearCheck();
        mRadioGroupSalarySecond.clearCheck();
        mRadioButtonRegimeAny.setChecked(true);
        mRadioButtonSalaryAny.setChecked(true);
    }

    RadioGroup.OnCheckedChangeListener radioGroupRegimeFirstListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if((group.getCheckedRadioButtonId() != -1) && mFlagRegime){
                mFlagRegime = false;
                mRadioGroupRegimeSecond.clearCheck();
                mCheckedRadioButtonRegime = group.findViewById(checkedId);
            }
            else{
                mFlagRegime = true;
            }
        }
    };

    RadioGroup.OnCheckedChangeListener radioGroupRegimeSecondListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if((group.getCheckedRadioButtonId() != -1) && mFlagRegime){
                mFlagRegime = false;
                mRadioGroupRegimeFirst.clearCheck();
                mCheckedRadioButtonRegime = group.findViewById(checkedId);
            }
            else{
                mFlagRegime = true;
            }
        }
    };

    RadioGroup.OnCheckedChangeListener radioGroupSalaryFirstListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if((group.getCheckedRadioButtonId() != -1) && mFlagSalary){
                mFlagSalary = false;
                mRadioGroupSalarySecond.clearCheck();
                mCheckedRadioButtonSalary = group.findViewById(checkedId);
            }
            else {
                mFlagSalary = true;
            }
        }
    };

    RadioGroup.OnCheckedChangeListener radioGroupSalarySecondListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if((group.getCheckedRadioButtonId() != -1) && mFlagSalary){
                mFlagSalary = false;
                mRadioGroupSalaryFirst.clearCheck();
                mCheckedRadioButtonSalary = group.findViewById(checkedId);
            }
            else {
                mFlagSalary = true;
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();

        mSearchsModel.setRegime(mCheckedRadioButtonRegime.getText().toString());
        mSearchsModel.setSalary(mCheckedRadioButtonSalary.getText().toString());
        mSQLiteHelper.saveRadioButtons(mSearchsModel);
    }
}
