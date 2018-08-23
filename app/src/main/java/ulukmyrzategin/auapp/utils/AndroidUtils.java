package ulukmyrzategin.auapp.utils;

import android.content.Context;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

/**
 * Created by $TheSusanin on 22.08.2018.
 */
public class AndroidUtils {

    public static void showLongToast(Context context, String message) {
        showToast(context, message, Toast.LENGTH_LONG);
    }

    private static void showToast(Context context, String message, int length) {
        Toasty.normal(context, message, length).show();
    }
}
