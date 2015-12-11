package com.rxx.fast.db.table;

import com.rxx.fast.utils.ClassUtils;
import com.rxx.fast.utils.FieldUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

/**
 * 数据库表属性
 * User: RanCQ
 * Date: 2015/9/16
 */
public class TableInfo{
    /**数据库表名称*/
    private String tableName;
    /**数据库表对应实体类名*/
    private String className;
    /**ID属性*/
    private ColumnPropertie id;

    public ColumnPropertie getId() {
        return id;
    }

    public void setId(ColumnPropertie id) {
        this.id = id;
    }

    /**其余属性,实体类的属性名以及数据库的列名一致*/
    public final HashMap<String, ColumnPropertie> propertyMap = new HashMap<String, ColumnPropertie>();

    /**数据库实体类对应类名为键，TableInfo为值*/
    private static final HashMap<String, TableInfo> tableInfoMap = new HashMap<String, TableInfo>();

    /**根据实体类创建TableInfo数据*/
    public final static TableInfo getTableInfo(Class<?> clazz){
        if(clazz==null){
            throw  new RuntimeException("create TableInfo error,the clazz is null");
        }
        TableInfo tableInfo=tableInfoMap.get(clazz.getName());
        if(tableInfo==null){
            //初始化比TableIfo表属性
            tableInfo=new TableInfo();
            tableInfo.setClassName(clazz.getName());
            tableInfo.setTableName(ClassUtils.getTableName(clazz));

            //初始化字段属性
            List<Field> fields=ClassUtils.getFields(clazz);
            if(fields==null||fields.size()==0){//没有属性，抛出异常
                    throw new RuntimeException("this model["+clazz+"] has no field");
            }else{
                for(Field field:fields){//遍历所有属性
                    ColumnPropertie columnPropertie=null;
                    if(field.getAnnotation(Property.class)!=null && !field.getAnnotation(Property.class).isSave()) {
                        continue;
                    }
                        if (tableInfo.id == null && (field.getAnnotation(ID.class) != null || "_id".equals(field.getName())
                                || "id".equals(field.getName()))) {//是ID属性
                            if (field.getAnnotation(ID.class) != null && field.getAnnotation(ID.class).name().trim().length() > 0) {//配置了ID注解
                                columnPropertie = new ColumnPropertie(field, field.getAnnotation(ID.class).name().trim());
                            } else {
                                columnPropertie = new ColumnPropertie(field);
                            }
                            columnPropertie.setSet(FieldUtils.getFieldSetMethod(clazz, field));//
                            columnPropertie.setGet(FieldUtils.getFieldGetMethod(clazz, field));
                            tableInfo.id = columnPropertie;
                        } else {//非ID属性
                            columnPropertie = new ColumnPropertie(field);
                            columnPropertie.setSet(FieldUtils.getFieldSetMethod(clazz, field));
                            columnPropertie.setGet(FieldUtils.getFieldGetMethod(clazz, field));
                            tableInfo.propertyMap.put(columnPropertie.getColumnName(), columnPropertie);
                        }

                }
            }
        }
        if(tableInfo==null){
            throw  new RuntimeException("the class[\"+clazz+\"]'s table is null");
        }

        if(tableInfo.id==null){
            throw new RuntimeException("the class["+clazz+"]'s idField is null , \n you can define _id,id property or use annotation @id to solution this exception");
        }
        tableInfoMap.put(clazz.getName(),tableInfo);
        return tableInfo;
    }


    private TableInfo(){}

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTableName() {
        return tableName;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
