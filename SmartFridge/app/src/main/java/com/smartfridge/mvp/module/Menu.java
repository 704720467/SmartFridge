package com.smartfridge.mvp.module;

/**
 * Created by admin on 2017/9/20.
 * 菜谱模型
 */

public class Menu {
    private String id;
    private String name;//名字
    private float weightList;//重量
    private String img;//图片
    private String introduce;

    public Menu(String id, String img, String name) {
        this.id = id;
        this.img = img;
        this.name = name;
        introduce = "鱼香肉丝（英文：Stir-fried Pork Strips in Fish Sauce），是一道特色传统名菜，以鱼香味调味而得名，属川菜。相传灵感来自老菜泡椒肉丝，民国年间由四川籍厨师创制而成[1]  。";
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

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
