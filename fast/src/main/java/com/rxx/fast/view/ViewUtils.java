package com.rxx.fast.view;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

import java.lang.reflect.Field;

/**
 * User: RanCQ
 * Date: 2015/10/9
 */
public class ViewUtils {

    public static void initViews(Activity activity) {
        initViews(activity, activity.getWindow().getDecorView());
    }

    /**
     * 遍历当前属性，并和xml绑定
     *
     * @param injectedSource
     * @param sourceView
     */
    public static void initViews(Object injectedSource, View sourceView) {
        //获取所有属性
        Field[] fields = injectedSource.getClass().getDeclaredFields();
        //遍历
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    //已经绑定属性
                    if (field.get(injectedSource) != null) {
                        continue;
                    }

                    //绑定xml
                    ViewInject viewInject = field.getAnnotation(ViewInject.class);
                    if (viewInject != null) {
                        int viewId = viewInject.id();
                        if (viewId == -1) {
                            throw new RuntimeException("Bing view failed," + field.getName()
                                    + "'s id is invalid");
                        }
                        //绑定控件
                        View view = sourceView.findViewById(viewId);
                        field.set(injectedSource, view);
                        //添加事件
                        if (viewInject.click()) {
                            if (injectedSource instanceof View.OnClickListener) {
                                view.setOnClickListener((View.OnClickListener) injectedSource);
                            } else {
                                throw new RuntimeException("setOnClickListener failed,View.OnClickListener  invalid");
                            }
                        }
                        if (viewInject.longClick()) {
                            if (injectedSource instanceof View.OnLongClickListener) {
                                view.setOnLongClickListener((View.OnLongClickListener) injectedSource);
                            } else {
                                throw new RuntimeException("setOnClickListener failed,View.OnLongClickListener  invalid");
                            }
                        }
                        if (viewInject.itemClick()) {
                            if (injectedSource instanceof AdapterView.OnItemClickListener) {
                                if (view instanceof AdapterView) {
                                    ((AdapterView) view).setOnItemClickListener((AdapterView.OnItemClickListener) injectedSource);
                                } else {
                                    throw new RuntimeException("setOnItemClickListener failed,the " + field.getName() + " isn't AdapterView");
                                }
                            } else {
                                throw new RuntimeException("setOnItemClickListener failed,AdapterView.OnItemClickListener  invalid");
                            }

                        }
                        if (viewInject.itemLongClick()) {

                            if (injectedSource instanceof AdapterView.OnItemLongClickListener) {
                                if (view instanceof AdapterView) {
                                    ((AdapterView) view).setOnItemLongClickListener((AdapterView.OnItemLongClickListener) injectedSource);
                                } else {
                                    throw new RuntimeException("setOnItemLongClickListener failed,the " + field.getName() + " isn't AdapterView");
                                }
                            } else {
                                throw new RuntimeException("setOnItemLongClickListener failed,AdapterView.OnItemLongClickListener  invalid");
                            }
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
