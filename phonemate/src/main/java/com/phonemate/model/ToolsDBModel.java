package com.phonemate.model;

/**
 * 本地小工具保存在数据库中对应实体类
 * User: RanCQ
 * Date: 2015/9/22
 */
public class ToolsDBModel {

    /**对应数据库id*/
    private int id;
    /**名称*/
    private String name;
    /**完整类名*/
    private String className;
    /**完整类名*/
    private String packName;
    /**图标ID*/
    private int drawableIcon;
    /**类型*/
    private int type;

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getDrawableIcon() {
        return drawableIcon;
    }

    public void setDrawableIcon(int drawableIcon) {
        this.drawableIcon = drawableIcon;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ToolsDBModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", className='" + className + '\'' +
                ", drawableIcon=" + drawableIcon +
                ", type=" + type +
                '}';
    }

}
