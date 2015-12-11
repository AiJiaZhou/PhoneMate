package com.rxx.fast.db.sqlite;

import java.util.ArrayList;
import java.util.List;

/**
 * sql语句实体类
 * User: RanCQ
 * Date: 2015/9/16
 */
public class SqlInfo {
    /**sql语句*/
    private String sql;
    /**sql语句?的匹配参数*/
    private ArrayList<Object> bindArgs;

    public SqlInfo() {
       this. bindArgs=new ArrayList<>();
    }

    public String getSql() {
        return sql;
    }
    public Object[] getBindArgsAsArray(){
        if(bindArgs!=null){
            return bindArgs.toArray();
        }else{
            return null;
        }
    }

    public String [] getBindArgsAsStringArray(){
        if(bindArgs!=null&&bindArgs.size()>0){
            String [] strings=new String[bindArgs.size()];
            int i=0;
            for(Object o:bindArgs){
                strings[i++]=o.toString();
            }
            return strings;
        }else{
            return null;
        }
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public ArrayList<Object> getBindArgs() {
        return bindArgs;
    }

    public void setBindArgs(ArrayList<Object> bindArgs) {
        this.bindArgs = bindArgs;
    }

    public void addValue(Object value){
        if(bindArgs==null){
            bindArgs=new ArrayList<>();
        }
        bindArgs.add(value);
    }

    public void addAllValue(List value){
        if(bindArgs==null){
            bindArgs=new ArrayList<>();
        }
        bindArgs.addAll(value);
    }

    @Override
    public String toString() {
        return "SqlInfo{" +
                "sql='" + sql + '\'' +
                ", bindArgs=" + bindArgs +
                '}';
    }
}
