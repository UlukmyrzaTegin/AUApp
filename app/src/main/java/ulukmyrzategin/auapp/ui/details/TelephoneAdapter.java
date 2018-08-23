package ulukmyrzategin.auapp.ui.details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ulukmyrzategin.auapp.R;

/**
 * Created by $TheSusanin on 23.08.2018.
 */
public class TelephoneAdapter extends BaseAdapter {

    private ArrayList<String> mArrayList;
    private Context mContext;

    public TelephoneAdapter(Context context, ArrayList<String> arrayList) {
        this.mContext = context;
        this.mArrayList = arrayList;
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return mArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_list_telephone, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(view);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        String telephone = (String) getItem(i);
        viewHolder.mTextViewDialogTelephone.setText(telephone);

        return view;
    }

    private class ViewHolder{
        private TextView mTextViewDialogTelephone;

        public ViewHolder(View view) {
            mTextViewDialogTelephone = view.findViewById(R.id.textViewDialogTelephone);
        }
    }
}
