package ulukmyrzategin.auapp.ui.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ulukmyrzategin.auapp.R;
import ulukmyrzategin.auapp.ui.BaseFragmentDialog;

/**
 * Created by $TheSusanin on 23.08.2018.
 */
public class DialogTelephoneFragment extends BaseFragmentDialog implements AdapterView.OnItemClickListener {
    private ListView mListViewTelephone;
    private TelephoneAdapter mTelephoneAdapter;
    private ArrayList<String> mTelephones = new ArrayList<>();
    private String mTelephone;
    private Intent mIntentDial;

    @Override
    protected int getViewLayout() {
        return R.layout.fragment_dialog_telephone;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        removeDialogToolbar();
        initialize(view);
    }

    private void initialize(View view) {
        mListViewTelephone = view.findViewById(R.id.listViewTelephone);
        mTelephoneAdapter = new TelephoneAdapter(getContext(), bundleTelephone());
        mListViewTelephone.setAdapter(mTelephoneAdapter);
        mListViewTelephone.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
        mTelephone = (String) parent.getItemAtPosition(i);
        dialPhone(mTelephone);
    }

    private ArrayList<String> bundleTelephone() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mTelephones = bundle.getStringArrayList("telephones");
        }
        return  mTelephones;
    }

    private void dialPhone(String telephone) {
        mIntentDial = new Intent(Intent.ACTION_DIAL);
        mIntentDial.setData(Uri.parse("tel:" + telephone));
        startActivity(mIntentDial);
    }
}
