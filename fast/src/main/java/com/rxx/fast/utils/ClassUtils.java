package com.rxx.fast.utils;

import com.rxx.fast.db.table.ID;
import com.rxx.fast.db.table.Table;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * User: RanCQ
 * Date: 2015/9/16
 */
public class ClassUtils{
    /**
     * 根据实体类获取表名
     * @param clazz
     * @return 数据库表名
     */
    public static String getTableName(Class<?> clazz) {
        Table table = clazz.getAnnotation(Table.class);
        if(table == null || table.name().trim().length() == 0 ){
            //当没有注解的时候默认用类的名称作为表名,并把点（.）替换为下划线(_)
            return clazz.getName().replace('.', '_');
        }
        return table.name();
    }


    /**
     * 根据类获取ID 属性
     *
     * @param clazz
     * @return
     */
    public static Field getPrimaryKeyField(Class<?> clazz){
        //获取所以的属性
        Field [] fields=clazz.getDeclaredFields();
        Field idField=null;
        if(fields==null||fields.length==0){//没有属性，抛出异常
            throw new RuntimeException("this model["+clazz+"] has no field");
        }else{
            for(Field field:fields){
                if(field.getAnnotation(ID.class)!=null){
                    idField=field;
                    break;
                }
            }
            //优先查找_id
            if(idField==null){
                for(Field field:fields){
                    if("_id".equals(field.getName())){
                        idField=field;
                        break;
                    }
                }
            }
            //查找id
            if(idField==null){
                for(Field field:fields){
                    if("id".equals(field.getName())){
                        idField=field;
                        break;
                    }
                }
            }
        }
        return idField;
    }

    /**
     * 通过反射获取所有属性
     *
     * @param clazz
     * @return
     */
    public static List<Field> getFields(Class<?> clazz){
        return Arrays.asList(clazz.getDeclaredFields());
    }
}
