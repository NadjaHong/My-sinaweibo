package org.nadja.android.weibo.util;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {

    /**
     * 表情TextView帮助文件
     */
    public static void formatContent(TextView textView) {
        SpannableString spannableString = new SpannableString(textView.getText());
        Pattern pattern = Pattern.compile("@[^\\s:锛歖+[:锛歕\\s]|\\[[^0-9]{1,4}\\]");
        Matcher matcher = pattern.matcher(spannableString);
        while (matcher.find()) {
            String match=matcher.group();
            if(match.startsWith("@")){ 
                spannableString.setSpan(new ForegroundColorSpan(0xff0077ff),
                        matcher.start(), matcher.end(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

    }

}
