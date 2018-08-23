package ulukmyrzategin.auapp;

import android.app.Application;
import android.content.Context;

import ulukmyrzategin.auapp.data.NetworkBuilder;
import ulukmyrzategin.auapp.data.VacanciesService;
import ulukmyrzategin.auapp.data.db.SQLiteHelper;


/**
 * Created by $TheSusanin on 21.08.2018.
 */
public class StartApplication extends Application {

    private VacanciesService mService;
    private SQLiteHelper mSQLiteHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        mService = NetworkBuilder.initService();
        mSQLiteHelper = new SQLiteHelper(getApplicationContext());
    }

    public static StartApplication getApp(Context context) {
        return (StartApplication) context.getApplicationContext();
    }

    public VacanciesService getService() {
        return mService;
    }

    public SQLiteHelper getSQLiteHelper() {
         return mSQLiteHelper;
    }
}
