package com.yakin.uilab.adjust.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yakin.watchdog.WatchDog;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * <com.yakin.uilab.adjust.widget.MarkTextView
 *         android:layout_width="wrap_content"
 *         android:layout_height="wrap_content"
 *         android:text="[:drawable:icon:][:raw:icon:][:assets:icon.png:][:file:/sdcard/icon.png:]"/>
 *
 *  Or
 *
 *  MarkTextView textView;
 *  ...
 *  textView.setMarkText("[:drawable:icon:][:raw:icon:][:assets:icon.png:][:file:/sdcard/icon.png:]");
 *
 *  [:drawable:icon:]           :   Load icon resources from res/dravable
 *  [:raw:icon:]                :   Load icon resources from res/raw
 *  [:assets:icon:]             :   Load icon resources from assets
 *  [:file:/sdcard/icon.png:]   :   Load icon resources from local path
 */
@SuppressLint("AppCompatCustomView")
public class MarkTextView extends TextView {

    // [:prefix:filename:]
    private final String REGEX_START = "\\[:";
    private final String REGEX_END = ":\\]";
    private final String REGEX_CONTENT = "\\w+:(\\/?\\w+)+(\\.\\w+)?";
    private final Pattern pattern = Pattern.compile(REGEX_START + REGEX_CONTENT + REGEX_END);

    private final String PREFIX_DRAWABLE = "drawable:";
    private final String PREFIX_RAW = "raw:";
    private final String PREFIX_ASSETS = "assets:";
    private final String PREFIX_FILE = "file:";

    public MarkTextView(Context context) {
        super(context);
    }

    public MarkTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        CharSequence text = getText();
        if(!TextUtils.isEmpty(text)) {
            setMarkText(text);
        }
    }

    public void setMarkText(CharSequence text) {
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String tag = matcher.group();
            WatchDog.getLogger().d("tag:%s", tag);
            tag = tag.replaceAll(REGEX_START + "|" + REGEX_END, "");
            Drawable drawable = null;
            if (tag.startsWith(PREFIX_DRAWABLE)) {
                int resId = getResIdByName("drawable", tag.substring(PREFIX_DRAWABLE.length()));
                drawable = ContextCompat.getDrawable(getContext(), resId);
            } else if(tag.startsWith(PREFIX_RAW)) {
                int resId = getResIdByName("raw", tag.substring(PREFIX_RAW.length()));
                drawable = ContextCompat.getDrawable(getContext(), resId);
            } else if(tag.startsWith(PREFIX_ASSETS)) {
                drawable = getDrawableFromAsserts(tag.substring(PREFIX_ASSETS.length()));
            } else if(tag.startsWith(PREFIX_FILE)) {
                drawable = getDrawableFromLocal(tag.substring(PREFIX_FILE.length()));
            }
            if(drawable != null) {
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                spannable.setSpan(new ImageSpan(drawable), matcher.start(), matcher.end(), ImageSpan.ALIGN_BASELINE);
            }
        }
        super.setText(spannable);
    }

    private int getResIdByName(String fType, String fName){
        return getResources().getIdentifier(fName, fType, getContext().getPackageName());
    }

    private Drawable getDrawableFromAsserts(String fileName) {
        try {
            InputStream is = getResources().getAssets().open(fileName);
            return Drawable.createFromStream(is, null);
        } catch (OutOfMemoryError e) {
            WatchDog.getLogger().e(e, "read % failed", fileName);
        } catch (Exception e) {
            WatchDog.getLogger().e(e, "read % failed", fileName);
        }
        return null;
    }

    private Drawable getDrawableFromLocal(String filePath) {
        try {
            InputStream is = new FileInputStream(filePath);
            return Drawable.createFromStream(is, null);
        } catch (OutOfMemoryError e) {
            WatchDog.getLogger().e(e, "read %s failed", filePath);
        } catch (Exception e) {
            WatchDog.getLogger().e(e, "read %s failed", filePath);
        }
        return null;
    }
}
