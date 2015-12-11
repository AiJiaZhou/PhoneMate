package com.rxx.fast.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Field工具类
 * User: RanCQ
 * Date: 2015/9/18
 */
public class FieldUtils {

    /**时间转换格式*/
    public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**时间字符串转换为时间格式*/
    public static Date stringToDateTime(String strDate) {
        if (strDate != null) {
            try {
                return SDF.parse(strDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取属性的getXXX()方法
     * @param clazz
     * @param field
     * @return
     */
    public static Method getFieldGetMethod(Class clazz,Field field){
        Method method=null;
        String mn=null;
        String fieldName=field.getName();
        if(field.getType()==Boolean.class||field.getType()==boolean.class){//是boolean类型
            mn = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            try {
                method =clazz.getDeclaredMethod(mn);//getBooleanA booleanA
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                try {
                    method =clazz.getDeclaredMethod(fieldName);//isBooleanA isBooleanA
                } catch (NoSuchMethodException ex) {
                    ex.printStackTrace();
                    try {
                        //booleanA isBooleanA setIsLock
                        method =clazz.getDeclaredMethod("is" + fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1) );
                    } catch (NoSuchMethodException exx) {
                        exx.printStackTrace();
                    }
                }
            }

        }else {//非boolean类型
            mn = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            try {
                method =clazz.getDeclaredMethod(mn);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        if(method==null){
            throw  new RuntimeException("the class["+clazz+"]'s"+ field+" Without getXXX() method");
        }
        return method;
    }

    /**
     *获取属性的getXXX()方法
     * @param clazz
     * @param fieldName
     * @return
     */
    public static Method getFieldGetMethod(Class clazz,String fieldName){
        Field field=null;
        try {
            field = clazz.getField(fieldName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        if(field==null){
            throw  new RuntimeException("the class[\"+clazz+\"] Without this "+fieldName);
        }
        return getFieldSetMethod(clazz,field);
    }
    /**
     * 获取属性的setXXX()方法
     * @param clazz
     * @param field
     * @return
     */
    public static Method getFieldSetMethod(Class clazz,Field field){
        Method method=null;
        String fn = field.getName();
        String mn = "set" + fn.substring(0, 1).toUpperCase() + fn.substring(1);
        try {
            method= clazz.getDeclaredMethod(mn, field.getType());
        } catch (NoSuchMethodException e) {
            if(field.getType() == boolean.class||field.getType() == Boolean.class){
                method= getBooleanFieldSetMethod(clazz, field);
            }
        }

        if(method==null){
            throw  new RuntimeException("the class["+clazz+"]'s"+ field+" Without setXXX() method");
        }
        return method;
    }

    /**
     * 获取属性的setXXX()方法
     * @param clazz
     * @param fieldName
     * @return
     */
    public static Method getFieldMethod(Class clazz,String fieldName){
        Field field=null;
        try {
            field=clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        if(field==null){
            throw  new RuntimeException("the class[\"+clazz+\"] Without this "+fieldName);
        }

        return getFieldSetMethod(clazz, field);
    }

    /**
     *获取以is开通的boolean属性的set方法
     * @param clazz
     * @param f
     * @return
     */
    public static Method getBooleanFieldSetMethod(Class<?> clazz, Field f) {
        String fn = f.getName();
        String mn;
        if(isIsStart(f.getName())){
            mn = "set" + fn.substring(2, 3).toUpperCase() + fn.substring(3);
        }else{
            mn = "set" + fn.substring(0, 1).toUpperCase() + fn.substring(1);
        }
        try {
            return clazz.getDeclaredMethod(mn, f.getType());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断属性是否是is开头
     * @param fieldName
     * @return
     */
    private static boolean isIsStart(String fieldName){
        if(fieldName==null || fieldName.trim().length()==0)
            return false;
        //is开头，并且is之后第一个字母是大写 比如 isAdmin
        return fieldName.startsWith("is") && !Character.isLowerCase(fieldName.charAt(2));
    }

}
