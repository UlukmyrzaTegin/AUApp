package ulukmyrzategin.auapp.ui.customs;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by $TheSusanin on 22.08.2018.
 */
public class TextFontLight extends AppCompatTextView {
    public TextFontLight(Context context) {
        super(context);
        initLight();
    }

    public TextFontLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLight();
    }

    public TextFontLight(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLight();
    }

    private void initLight() {
        if (!isInEditMode()) {
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Light.ttf");
            setTypeface(typeface);
        }
    }
}
