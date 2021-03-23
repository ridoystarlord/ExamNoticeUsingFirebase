package com.ridoy.examnoticeusingfirebase.ModelClass;

public class PaymentRequestModel {

    String paymentMethod,paymentNumber,paymentPoint,userUid;

    public PaymentRequestModel() {
    }

    public PaymentRequestModel(String paymentMethod, String paymentNumber, String paymentPoint, String userUid) {
        this.paymentMethod = paymentMethod;
        this.paymentNumber = paymentNumber;
        this.paymentPoint = paymentPoint;
        this.userUid = userUid;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(String paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public String getPaymentPoint() {
        return paymentPoint;
    }

    public void setPaymentPoint(String paymentPoint) {
        this.paymentPoint = paymentPoint;
    }
}
