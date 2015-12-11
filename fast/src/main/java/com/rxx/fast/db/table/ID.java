package com.rxx.fast.db.table;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ID属性注解，没有配置注解，则找_id,然后查找id
 * User: RanCQ
 * Date: 2015/9/17
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ID {
    public String name() default "";
}
