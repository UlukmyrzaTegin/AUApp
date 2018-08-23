package ulukmyrzategin.auapp.ui.customs;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by $TheSusanin on 22.08.2018.
 */
public class TextFontBold extends AppCompatTextView {

    public TextFontBold(Context context) {
        super(context);
        initBolt();
    }

    public TextFontBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        initBolt();
    }

    public TextFontBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBolt();
    }


    private void initBolt() {
        if (!isInEditMode()) {

            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Bold.ttf");
            setTypeface(typeface);
        }
    }
}
