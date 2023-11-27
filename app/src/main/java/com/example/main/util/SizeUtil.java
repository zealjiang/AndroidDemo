package com.example.main.util;

import android.content.res.Resources;

public class SizeUtil {

    public static int dp2px(final float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
