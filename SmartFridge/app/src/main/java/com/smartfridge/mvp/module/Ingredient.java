package com.smartfridge.mvp.module;

/**
 * Created by admin on 2017/9/20.
 * 食材模型
 */

public class Ingredient {
    private String id;
    private String img;//图片
    private String name;//名字
    private float weightList;//重量

    public Ingredient(String id, String img, String name, float weightList) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.weightList = weightList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getWeightList() {
        return weightList;
    }

    public void setWeightList(float weightList) {
        this.weightList = weightList;
    }
}
