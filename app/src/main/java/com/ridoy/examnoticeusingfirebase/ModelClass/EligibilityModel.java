package com.ridoy.examnoticeusingfirebase.ModelClass;

public class EligibilityModel {

    private String universityname,imageurl;;
    private int r_sscyear,r_hscyear;
    private Float r_sscpoint,r_hscpoint;

    public EligibilityModel(String universityname, String imageurl, int r_sscyear, int r_hscyear, Float r_sscpoint, Float r_hscpoint) {
        this.universityname = universityname;
        this.imageurl = imageurl;
        this.r_sscyear = r_sscyear;
        this.r_hscyear = r_hscyear;
        this.r_sscpoint = r_sscpoint;
        this.r_hscpoint = r_hscpoint;
    }

    public EligibilityModel() {
    }

    public String getUniversityname() {
        return universityname;
    }

    public void setUniversityname(String universityname) {
        this.universityname = universityname;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public int getR_sscyear() {
        return r_sscyear;
    }

    public void setR_sscyear(int r_sscyear) {
        this.r_sscyear = r_sscyear;
    }

    public int getR_hscyear() {
        return r_hscyear;
    }

    public void setR_hscyear(int r_hscyear) {
        this.r_hscyear = r_hscyear;
    }

    public Float getR_sscpoint() {
        return r_sscpoint;
    }

    public void setR_sscpoint(Float r_sscpoint) {
        this.r_sscpoint = r_sscpoint;
    }

    public Float getR_hscpoint() {
        return r_hscpoint;
    }

    public void setR_hscpoint(Float r_hscpoint) {
        this.r_hscpoint = r_hscpoint;
    }
}
