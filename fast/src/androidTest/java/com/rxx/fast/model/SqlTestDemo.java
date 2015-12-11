package com.rxx.fast.model;

import com.rxx.fast.db.table.ID;
import com.rxx.fast.db.table.Table;

import java.util.Date;

/**
 * User: RanCQ
 * Date: 2015/9/18
 */
@Table(name = "SqlTestDemo")
public class SqlTestDemo {
    @ID(name = "id")
    private long id;
    private String name="nameTest";
    private int intA=11;
    private Integer intB=12;
    private float floatA=22.03f;
    private Float floatB=26.6F;
    private double doubleA=18.3;
    private Double doubleB=23.6;
    private Date date=new Date(System.currentTimeMillis());
    private Boolean booleanA=false;
    private boolean booleanB=true;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIntA() {
        return intA;
    }

    public void setIntA(int intA) {
        this.intA = intA;
    }

    public Integer getIntB() {
        return intB;
    }

    public void setIntB(Integer intB) {
        this.intB = intB;
    }

    public float getFloatA() {
        return floatA;
    }

    public void setFloatA(float floatA) {
        this.floatA = floatA;
    }

    public Float getFloatB() {
        return floatB;
    }

    public void setFloatB(Float floatB) {
        this.floatB = floatB;
    }

    public double getDoubleA() {
        return doubleA;
    }

    public void setDoubleA(double doubleA) {
        this.doubleA = doubleA;
    }

    public Double getDoubleB() {
        return doubleB;
    }

    public void setDoubleB(Double doubleB) {
        this.doubleB = doubleB;
    }

    public Date getDate() {
        return new Date(System.currentTimeMillis());
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getBooleanA() {
        return booleanA;
    }

    public void setBooleanA(Boolean booleanA) {
        this.booleanA = booleanA;
    }

    public boolean isBooleanB() {
        return booleanB;
    }

    public void setBooleanB(boolean booleanB) {
        this.booleanB = booleanB;
    }

    @Override
    public String toString() {
        return "SqlTestDemo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", intA=" + intA +
                ", intB=" + intB +
                ", floatA=" + floatA +
                ", floatB=" + floatB +
                ", doubleA=" + doubleA +
                ", doubleB=" + doubleB +
                ", date=" + date +
                ", booleanA=" + booleanA +
                ", booleanB=" + booleanB +
                '}';
    }
}
