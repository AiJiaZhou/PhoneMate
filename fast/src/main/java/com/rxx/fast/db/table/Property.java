package com.rxx.fast.db.table;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 属性配置
 * User: RanCQ
 * Date: 2015/9/25
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Property {
    /**对应的数据库列名*/
    public String column() default "";
    /**默认值*/
    public String defaultValue() default "";
    /**是否保存该属性*/
    public boolean isSave() default  true;
}
