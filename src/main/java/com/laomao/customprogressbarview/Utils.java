package com.laomao.customprogressbarview;

import android.content.res.Resources;

/**
 * Created by dengyuan3 on 2017/9/8.
 */

public class Utils {
    public static int px2dip(int pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static float dip2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (dipValue * scale + 0.5f);
    }
}
