package com.rxx.fast.view;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * view注解相关
 * User: RanCQ
 * Date: 2015/10/9
 */
@Target(ElementType.FIELD)//
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewInject {
     int id() default -1;
     boolean click() default false;
     boolean longClick() default false;
     boolean itemClick() default false;
     boolean itemLongClick() default false;
}
