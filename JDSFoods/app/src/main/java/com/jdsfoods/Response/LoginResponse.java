package com.jdsfoods.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dikhong on 26-07-2018.
 */

public class LoginResponse {
    @SerializedName("flag")
    @Expose
    public Integer flag;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("item_counter")
    @Expose
    public String itemCounter;

    public String getItemCounter() {
        return itemCounter;
    }

    public void setItemCounter(String itemCounter) {
        this.itemCounter = itemCounter;
    }

    @SerializedName("user_data")
    @Expose

    public UserData userData;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public class UserData {

        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("name")
        @Expose
        public String name;

        @SerializedName("company")
        @Expose
        public String company;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPriceVisibleOnApp() {
            return priceVisibleOnApp;
        }

        public void setPriceVisibleOnApp(String priceVisibleOnApp) {
            this.priceVisibleOnApp = priceVisibleOnApp;
        }

        public String getQuoteId() {
            return quoteId;
        }

        public void setQuoteId(String quoteId) {
            this.quoteId = quoteId;
        }

        public String getAuthToken() {
            return authToken;
        }

        public void setAuthToken(String authToken) {
            this.authToken = authToken;
        }

        @SerializedName("address")
        @Expose
        public String address;

        @SerializedName("city")
        @Expose
        public String city;

        @SerializedName("state")
        @Expose
        public String state;

        @SerializedName("postal_code")
        @Expose
        public String postalCode;

        @SerializedName("phone")
        @Expose
        public String phone;

        @SerializedName("email")
        @Expose
        public String email;

        @SerializedName("price_visible_on_app")
        @Expose
        public String priceVisibleOnApp;

        @SerializedName("quote_id")
        @Expose
        public String quoteId;

        @SerializedName("auth_token")
        @Expose
        public String authToken;

        public Integer getIsCredit() {
            return isCredit;
        }

        public void setIsCredit(Integer isCredit) {
            this.isCredit = isCredit;
        }

        public String getPaymentTerm() {
            return paymentTerm;
        }

        public void setPaymentTerm(String paymentTerm) {
            this.paymentTerm = paymentTerm;
        }

        @SerializedName("is_credit")
        @Expose

        public Integer isCredit;

        @SerializedName("payment_term")
        @Expose
        public String paymentTerm;
    }
}
