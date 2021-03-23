package com.ridoy.examnoticeusingfirebase.SharedPrefManager;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreManager {
    private static SharedPreManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USER_PHONE = "userphone";
    private static final String KEY_USER_UID = "useruid";
    private static final String KEY_USER_SSCPOINT = "sscpoint";
    private static final String KEY_USER_SSCYEAR = "sscyear";
    private static final String KEY_USER_HSCPOINT = "hscpoint";
    private static final String KEY_USER_HSCYEAR = "hscyear";
    private static final String KEY_USER_IMAGEURL = "imageurl";

    private static final String KEY_USER_CURRENTSCORE = "currentpoint";
    private static final String KEY_USER_TOTALSCORE = "totalpoint";
    private static final String KEY_USER_TOTALEARN = "totalearn";

    private static final String KEY_USER_LOGIN_STATUS = "loginstatus";
    //private static final String KEY_USER_PAYMENTREQUEST_STATUS = "paymentrequeststatus";


    private SharedPreManager(Context context) {
        mCtx = context;

    }

    public static synchronized SharedPreManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPreManager(context);
        }
        return mInstance;
    }


    public boolean usersignup(String uid, String name, String phone, String imageUrl) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USER_UID, uid);
        editor.putString(KEY_USERNAME, name);
        editor.putString(KEY_USER_PHONE, phone);
        editor.putString(KEY_USER_IMAGEURL, imageUrl);

        editor.apply();

        return true;
    }

    public boolean userlogin(String uid, String name, String phone, String imageUrl, String sscpoint, String sscyear,
                             String hscpoint, String hscyear, String loginstatus, int currentscore, int totalscore,
                             int totalearn) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USER_UID, uid);
        editor.putString(KEY_USERNAME, name);
        editor.putString(KEY_USER_PHONE, phone);
        editor.putString(KEY_USER_IMAGEURL, imageUrl);
        editor.putString(KEY_USER_SSCPOINT, sscpoint);
        editor.putString(KEY_USER_SSCYEAR, sscyear);
        editor.putString(KEY_USER_HSCPOINT, hscpoint);
        editor.putString(KEY_USER_HSCYEAR, hscyear);
        editor.putString(KEY_USER_LOGIN_STATUS, loginstatus);
        //editor.putString(KEY_USER_PAYMENTREQUEST_STATUS, paymentStatus);
        editor.putInt(KEY_USER_CURRENTSCORE, currentscore);
        editor.putInt(KEY_USER_TOTALSCORE, totalscore);
        editor.putInt(KEY_USER_TOTALEARN, totalearn);

        editor.apply();

        return true;
    }


    public boolean userInformationUpdate(String name, String sscpoint, String sscyear, String hscpoint, String hscyear) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USERNAME, name);
        editor.putString(KEY_USER_SSCPOINT, sscpoint);
        editor.putString(KEY_USER_SSCYEAR, sscyear);
        editor.putString(KEY_USER_HSCPOINT, hscpoint);
        editor.putString(KEY_USER_HSCYEAR, hscyear);

        editor.apply();

        return true;
    }

    public boolean userInformationUpdate(String name, String sscpoint, String sscyear, String hscpoint, String hscyear,
                                         String loginstatus, int currentscore, int totalscore,
                                         int totalearn) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USERNAME, name);
        editor.putString(KEY_USER_SSCPOINT, sscpoint);
        editor.putString(KEY_USER_SSCYEAR, sscyear);
        editor.putString(KEY_USER_HSCPOINT, hscpoint);
        editor.putString(KEY_USER_HSCYEAR, hscyear);
        editor.putString(KEY_USER_LOGIN_STATUS, loginstatus);
        //editor.putString(KEY_USER_PAYMENTREQUEST_STATUS, paymentRequest);
        editor.putInt(KEY_USER_CURRENTSCORE, currentscore);
        editor.putInt(KEY_USER_TOTALSCORE, totalscore);
        editor.putInt(KEY_USER_TOTALEARN, totalearn);

        editor.apply();

        return true;
    }

    public boolean userInformationUpdate(String loginstatus, int currentscore, int totalscore, int totalearn) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USER_LOGIN_STATUS, loginstatus);
        editor.putInt(KEY_USER_CURRENTSCORE, currentscore);
        editor.putInt(KEY_USER_TOTALSCORE, totalscore);
        editor.putInt(KEY_USER_TOTALEARN, totalearn);
        //editor.putString(KEY_USER_PAYMENTREQUEST_STATUS, paymentRequest);
        editor.apply();

        return true;
    }


    public boolean userPointsUpdate(int currentscore, int totalscore) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_USER_CURRENTSCORE, currentscore);
        editor.putInt(KEY_USER_TOTALSCORE, totalscore);

        editor.apply();

        return true;
    }

    public boolean userPointsUpdate(int currentscore) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_USER_CURRENTSCORE, currentscore);
        //editor.putString(KEY_USER_PAYMENTREQUEST_STATUS, paymentRequest);
        editor.apply();

        return true;
    }

    public boolean logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getUsername() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public String getUserUid() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_UID, null);
    }

    public String getUserphone() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_PHONE, null);
    }

    public String getUsersscpoint() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_SSCPOINT, null);
    }

    public String getUserhscpoint() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_HSCPOINT, null);
    }

    public String getUsersscyear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_SSCYEAR, null);
    }

    public String getUserhscyear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_HSCYEAR, null);
    }

    public String getUserImageUrl() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_IMAGEURL, null);
    }

    public int getUsercurrentScore() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_USER_CURRENTSCORE, 0);
    }

    public int getUsertotalScore() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_USER_TOTALSCORE, 0);
    }

    public int getUsertotalEarn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_USER_TOTALEARN, 0);
    }

    public String getUserloginstatus() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_LOGIN_STATUS, null);
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USERNAME, null) != null){
            return true;
        }
        return false;
    }

}
