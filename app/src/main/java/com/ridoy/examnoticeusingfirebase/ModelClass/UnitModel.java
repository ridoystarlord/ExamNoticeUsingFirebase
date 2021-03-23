package com.ridoy.examnoticeusingfirebase.ModelClass;

public class UnitModel {
    String unitName, unitUrl;

    public UnitModel() {
    }

    public UnitModel(String unitName, String unitUrl) {
        this.unitName = unitName;
        this.unitUrl = unitUrl;
    }

    public String getUnitUrl() {
        return unitUrl;
    }

    public void setUnitUrl(String unitUrl) {
        this.unitUrl = unitUrl;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
