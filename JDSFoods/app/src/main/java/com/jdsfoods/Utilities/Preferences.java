package com.jdsfoods.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by dikhong on 26-07-2018.
 */

public class Preferences {
    private String appName = "AnyApp";
    private static final String TAG = "Preferences";
    private SharedPreferences userPrefs;
    public static final String USER_DETAILS = "user_details";
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "username";
    public static final String FIRST_NAME = "firstname";
    public static final String LAST_NAME = "lastname";
    public static final String EMAIL = "email";
    public static final String MOBILE = "mobile";
    public static final String USER_PHOTO_PATH = "userphoto_path";
    public static final String AUTH_KEY = "auth_key";
    public static final String IS_ACTIVE = "is_active";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String IS_LOGGEDIN = "islogged";
    public static final String DEVICE_TOKEN = "device_token";
    public static final String USER_TYPE = "user_type";
    public static final String UNIQUE_ID = "unique_id";
    public static final String COMPANY = "company";
    public static final String GROUP_ID = "group_id";
    public static final String QUOTE_ID = "quote_id";
    public static final String IS_CREDIT = "is_credit";
    public static final String PAYMENT_TERM = "payment_term";
    public static final String PRICE_VISIBLE = "price_visible";
    public static final String ADDRESS = "address";

    public String userId;
    public String userName;
    public String firstName;
    public String lastName;
    public String email;
    public String Mobile;
    public String userPhotoPath;
    public String authKey;
    public String isActive;
    public String latitude;
    public String longitude;
    public String device_token;
    public String uniqueId;
    public String company;
    public String groupId;
    public String quoteId;
    public String isCredit;
    public String paymentTerm;
    public String priceVisible;
    public String address;


    public String getAddress() {
        return userPrefs.getString(ADDRESS, null);
    }

    public void setAddress(String address) {
        userPrefs.edit().putString(ADDRESS, address).commit();
        this.address = address;
    }


    public String getPriceVisible() {
        return userPrefs.getString(PRICE_VISIBLE, null);
    }

    public void setPriceVisible(String priceVisible) {
        userPrefs.edit().putString(PRICE_VISIBLE, priceVisible).commit();
        this.priceVisible = priceVisible;
    }

    public String getIsCredit() {
        return userPrefs.getString(IS_CREDIT, null);
    }

    public void setIsCredit(String isCredit) {
        userPrefs.edit().putString(IS_CREDIT, isCredit).commit();
        this.isCredit = isCredit;
    }

    public String getPaymentTerm() {
        return userPrefs.getString(PAYMENT_TERM, null);
    }

    public void setPaymentTerm(String paymentTerm) {
        userPrefs.edit().putString(PAYMENT_TERM, paymentTerm).commit();
        this.paymentTerm = paymentTerm;
    }

    public String getQuoteId() {
        return userPrefs.getString(QUOTE_ID, null);
    }

    public void setQuoteId(String quoteId) {
        userPrefs.edit().putString(QUOTE_ID, quoteId).commit();
        this.quoteId = quoteId;
    }

    public String getGroupId() {
        return userPrefs.getString(GROUP_ID, null);
    }

    public void setGroupId(String groupId) {
        userPrefs.edit().putString(GROUP_ID, groupId).commit();
        this.groupId = groupId;
    }


    public String getCompany() {
        return userPrefs.getString(COMPANY, null);
    }

    public void setCompany(String company) {
        userPrefs.edit().putString(COMPANY, company).commit();
        this.company = company;
    }


    public String getUniqueId() {
        return userPrefs.getString(UNIQUE_ID, null);
    }

    public void setUniqueId(String uniqueId) {
        userPrefs.edit().putString(UNIQUE_ID, uniqueId).commit();
        this.uniqueId = uniqueId;
    }

    public String getUserType() {
        return userPrefs.getString(USER_TYPE, null);
    }

    public void setUserType(String userType) {
        userPrefs.edit().putString(USER_TYPE, userType).commit();
        this.userType = userType;
    }

    public String userType;


    public String getLatitude() {
        return userPrefs.getString(LATITUDE, null);
    }

    public void setLatitude(String latitude) {
        userPrefs.edit().putString(LATITUDE, latitude).commit();
        this.latitude = latitude;
    }

    public String getLongitude() {
        return userPrefs.getString(LONGITUDE, null);
    }

    public void setLongitude(String longitude) {
        userPrefs.edit().putString(LONGITUDE, longitude).commit();
        this.longitude = longitude;
    }

    private static Preferences instance;

    public static Preferences getInstance() {
        if (instance == null)
            instance = new Preferences();

        return instance;
    }

    private Preferences() {
        initPrefs();
    }

    private void initPrefs() {
        if (null != userPrefs) return;
        userPrefs = JDSFood.getInstance().getSharedPreferences(USER_DETAILS, Context.MODE_PRIVATE);
    }

    public String getDevice_token() {
        return userPrefs.getString(DEVICE_TOKEN, null);
    }

    public void setDevice_token(String device_token) {
        Log.d(TAG, "setDevice_token: Storing device token" + device_token);
        userPrefs.edit().putString(DEVICE_TOKEN, device_token).commit();
        this.device_token = device_token;
    }

    public String getUserId() {
        return userPrefs.getString(USER_ID, null);
    }

    public void setUserId(String userId) {
        userPrefs.edit().putString(USER_ID, userId).commit();
        this.userId = userId;
    }

    public String getUserName() {
        return userPrefs.getString(USER_NAME, null);
    }

    public void setUserName(String userName) {
        userPrefs.edit().putString(USER_NAME, userName).commit();
        this.userName = userName;
    }

    public String getFirstName() {
        return userPrefs.getString(FIRST_NAME, null);
    }

    public void setFirstName(String firstName) {
        userPrefs.edit().putString(FIRST_NAME, firstName).commit();
        this.firstName = firstName;
    }


    public String getLastName() {
        return userPrefs.getString(LAST_NAME, null);
    }

    public void setLastName(String lastName) {
        userPrefs.edit().putString(LAST_NAME, lastName).commit();
        this.lastName = lastName;
    }

    public String getEmail() {
        return userPrefs.getString(EMAIL, null);
    }

    public void setEmail(String email) {
        userPrefs.edit().putString(EMAIL, email).commit();
        this.email = email;
    }



    public String getMobile() {
        return userPrefs.getString(MOBILE, null);
    }

    public void setMobile(String mobile) {
        userPrefs.edit().putString(MOBILE, mobile).commit();
        Mobile = mobile;
    }


    public String getUserPhotoPath() {
        return userPrefs.getString(USER_PHOTO_PATH, null);
    }

    public void setUserPhotoPath(String userPhotoPath) {
        Log.d(TAG, "setUserPhotoPath: " + userPhotoPath);
        userPrefs.edit().putString(USER_PHOTO_PATH, userPhotoPath).commit();
        this.userPhotoPath = userPhotoPath;
    }


    public String getAuthKey() {
        return userPrefs.getString(AUTH_KEY, null);
    }

    public void setAuthKey(String authKey) {
        userPrefs.edit().putString(AUTH_KEY, authKey).commit();
        this.authKey = authKey;
    }


    public String getIsActive() {
        return userPrefs.getString(IS_ACTIVE, null);
    }

    public void setIsActive(String isActive) {
        userPrefs.edit().putString(IS_ACTIVE, isActive).commit();
        this.isActive = isActive;
    }


    public boolean isLogIn() {
        return userPrefs.getBoolean(IS_LOGGEDIN, false);
    }

    public void setLogIn(boolean logIn) {
        userPrefs.edit().putBoolean(IS_LOGGEDIN, logIn).commit();
    }

    public void clearQuote() {
        userPrefs.edit().remove("QUOTE_ID").commit();
    }

    public void clearUserDetails() {
        String tmp = getDevice_token();

        userPrefs.edit().clear().commit();
        if (tmp != null) {
            setDevice_token(tmp);
        }
    }
}

