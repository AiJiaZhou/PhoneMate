package com.rxx.fast.db.table;

import com.rxx.fast.utils.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *数据库每个列属性
 * User: RanCQ
 * Date: 2015/9/17
 */
public class ColumnPropertie {
    /**属性名称,非主键的属性名和字段名一致*/
    private String fieldName;
    /**对应的字段名*/
    private String columnName;
//    /**字段对应值*/
//    private String columnValue;
    /**属性名对应的类型*/
    private Class<?> dataType;
    /**实体类对应字段的属性*/
    private Field field;
    /**get方法*/
    private Method get;
    /**set方法*/
    private Method set;

    public ColumnPropertie(Field field) {
        this.field = field;
        fieldName=field.getName();
        columnName=fieldName;
        dataType=field.getType();
    }

    public ColumnPropertie(Field field,String columnName) {
        this.field = field;
        this.columnName=columnName;
        fieldName=field.getName();
        dataType=field.getType();
    }

    /**
     * 执行set方法
     * @param entity
     * @param value
     */
    public void setValue(Object entity,Object value){
        if(entity==null){
            throw new RuntimeException("set "+fieldName+" value error,the model is null");
        }
        if(value!=null){
            try {
                if (dataType == String.class) {//String类型
                    set.invoke(entity, value.toString());

                }else if(dataType==Integer.class || dataType==int.class){//int Integer
                    set.invoke(entity,Integer.parseInt(value.toString()));

                }else if(dataType==Float.class || dataType==float.class){//Float float
                    set.invoke(entity, Float.parseFloat(value.toString()));

                }else if(dataType==Double.class || dataType==double.class){//double Double
                    set.invoke(entity,Double.parseDouble(value.toString()));

                }else if(dataType==Long.class || dataType==long.class){//Long long
                    set.invoke(entity, Long.parseLong(value.toString()));

                }else if(dataType==boolean.class || dataType==Boolean.class){//boolean Boolan
                    set.invoke(entity,"1".equals(value.toString()));

                }else if(dataType==java.util.Date.class || dataType==java.sql.Date.class) {//date
                    set.invoke(entity, FieldUtils.stringToDateTime(value.toString()));
                }else {
                    set.invoke(entity,value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            try {
                field.setAccessible(true);
                field.set(entity, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**执行get方法,获取值*/
    public <T> T getValue(Object obj){
        if(obj != null && get != null) {
            try {
                if(dataType == java.util.Date.class || dataType ==java.sql.Date.class){
                    return (T) FieldUtils.SDF.format(get.invoke(obj));
                }else{
                    return (T) get.invoke(obj);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

//    public String getColumnValue() {
//        return columnValue;
//    }
//
//    public void setColumnValue(String columnValue) {
//        this.columnValue = columnValue;
//    }

    public Class<?> getDataType() {
        return dataType;
    }

    public void setDataType(Class<?> dataType) {
        this.dataType = dataType;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Method getGet() {
        return get;
    }

    public void setGet(Method get) {
        this.get = get;
    }

    public Method getSet() {
        return set;
    }

    public void setSet(Method set) {
        this.set = set;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}

