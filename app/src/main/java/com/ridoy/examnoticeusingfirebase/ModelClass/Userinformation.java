package com.ridoy.examnoticeusingfirebase.ModelClass;

public class Userinformation {
    private String uId,name,phoneNumber,imageUrl,sscpoint,sscyear,hscpoint,hscyear,loginstatus,paymentRequestStatus;
    int currentpoint,totalpoint,totalearn;

    public Userinformation(String uId, String name, String phoneNumber, String imageUrl) {
        this.uId = uId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.imageUrl = imageUrl;
    }

    public Userinformation() {
    }

    public String getPaymentRequestStatus() {
        return paymentRequestStatus;
    }

    public void setPaymentRequestStatus(String paymentRequestStatus) {
        this.paymentRequestStatus = paymentRequestStatus;
    }

    public int getCurrentpoint() {
        return currentpoint;
    }

    public void setCurrentpoint(int currentpoint) {
        this.currentpoint = currentpoint;
    }

    public int getTotalpoint() {
        return totalpoint;
    }

    public void setTotalpoint(int totalpoint) {
        this.totalpoint = totalpoint;
    }

    public int getTotalearn() {
        return totalearn;
    }

    public void setTotalearn(int totalearn) {
        this.totalearn = totalearn;
    }


    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSscpoint() {
        return sscpoint;
    }

    public void setSscpoint(String sscpoint) {
        this.sscpoint = sscpoint;
    }

    public String getSscyear() {
        return sscyear;
    }

    public void setSscyear(String sscyear) {
        this.sscyear = sscyear;
    }

    public String getHscpoint() {
        return hscpoint;
    }

    public void setHscpoint(String hscpoint) {
        this.hscpoint = hscpoint;
    }

    public String getHscyear() {
        return hscyear;
    }

    public void setHscyear(String hscyear) {
        this.hscyear = hscyear;
    }

    public String getLoginstatus() {
        return loginstatus;
    }

    public void setLoginstatus(String loginstatus) {
        this.loginstatus = loginstatus;
    }
}
