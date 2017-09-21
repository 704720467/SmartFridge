package com.smartfridge.util;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class TextUtil {
    public static void setTextViewHint(TextView tv, String hint, int size) {
        SpannableString ss = new SpannableString(hint);
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size, true);
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setHint(new SpannedString(ss));
    }

    public static void setEditTextHint(EditText et, String hint, int size) {
        SpannableString ss = new SpannableString(hint);
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size, true);
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        et.setHint(new SpannedString(ss));
    }

    /**
     * 将字符全角化。 即将所有的数字、字母及标点全部转为全角字符， 使它们与汉字同占两个字节，这样就可以避免由于占位导致的排版混乱问题
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 将大于1万的数据给为：1.*万
     *
     * @return
     */
    public static String numberToString(long number) {
        StringBuilder sb = new StringBuilder();
        if (number >= 10000) {
            float count = (float) number / 10000;
            DecimalFormat decimalFormat = new DecimalFormat("0.0");
            sb.append(decimalFormat.format(count));
            sb.append("万");
        } else {
            sb.append(number);
        }
        return sb.toString();
    }

    /**
     * 给TextView设置内容
     *
     * @param textView 目标控件
     * @param str      目标内容
     */
    public static void setStringToTextView(TextView textView, String str) {
        if (textView != null && !TextUtils.isEmpty(str)) {
            textView.setText(str);
        }
    }
}
