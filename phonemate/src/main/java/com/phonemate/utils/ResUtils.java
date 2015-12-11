package com.phonemate.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/11/12 10:56
 * 修改人：colorful
 * 修改时间：15/11/12 10:56
 * 修改备注：
 */
public class ResUtils {

    public static  int  getColor(Context context,int id){
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.M){
            return context.getResources().getColor(id,context.getTheme());
        }else{
            return context.getResources().getColor(id);
        }
    }

    public static Drawable getDrawable(Context context,int id){
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.KITKAT_WATCH){
            return context.getResources().getDrawable(id,context.getTheme());
        }else{
            return context.getResources().getDrawable(id);
        }

    }

    public static float dp2px(float dpValue,Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    public static float sp2px(float spValue,Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
