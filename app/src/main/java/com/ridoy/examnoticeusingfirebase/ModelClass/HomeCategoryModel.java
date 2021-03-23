package com.ridoy.examnoticeusingfirebase.ModelClass;

public class HomeCategoryModel {
    String catName,catimageUrl;

    public HomeCategoryModel() {
    }

    public HomeCategoryModel(String catName, String catimageUrl) {
        this.catName = catName;
        this.catimageUrl = catimageUrl;
    }

    public HomeCategoryModel(String catName) {
        this.catName = catName;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatimageUrl() {
        return catimageUrl;
    }

    public void setCatimageUrl(String catimageUrl) {
        this.catimageUrl = catimageUrl;
    }
}
