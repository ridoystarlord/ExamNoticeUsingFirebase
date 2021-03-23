package com.ridoy.examnoticeusingfirebase.ModelClass;

public class MessageModel {

    private String msgid,message,senderid,imageUrl,msgstatus;
    private long timestamp;
    private int feeling=-1;

    public MessageModel(String message, String senderid, String msgstatus, long timestamp) {
        this.message = message;
        this.senderid = senderid;
        this.msgstatus = msgstatus;
        this.timestamp = timestamp;
    }

    /*public MessageModel(String message, String senderid, long timestamp) {
        this.message = message;
        this.senderid = senderid;
        this.timestamp = timestamp;
    }*/

    public MessageModel() {
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getFeeling() {
        return feeling;
    }

    public void setFeeling(int feeling) {
        this.feeling = feeling;
    }

    public String getMsgstatus() {
        return msgstatus;
    }

    public void setMsgstatus(String msgstatus) {
        this.msgstatus = msgstatus;
    }
}
