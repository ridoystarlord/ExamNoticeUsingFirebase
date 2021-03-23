package com.ridoy.examnoticeusingfirebase.ModelClass;

public class UniversityNamesModel {
    String universityName,universityImageUrl,universityStaus;

    public UniversityNamesModel() {
    }

    public UniversityNamesModel(String universityName, String universityImageUrl, String universityStaus) {
        this.universityName = universityName;
        this.universityImageUrl = universityImageUrl;
        this.universityStaus = universityStaus;
    }

    public String getUniversityStaus() {
        return universityStaus;
    }

    public void setUniversityStaus(String universityStaus) {
        this.universityStaus = universityStaus;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getUniversityImageUrl() {
        return universityImageUrl;
    }

    public void setUniversityImageUrl(String universityImageUrl) {
        this.universityImageUrl = universityImageUrl;
    }
}
