package com.jdsfoods.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dikhong on 27-07-2018.
 */

public class ProfileResponse {
    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public ProfileData getProfileData() {
        return profileData;
    }

    public void setProfileData(ProfileData profileData) {
        this.profileData = profileData;
    }

    @SerializedName("flag")
    @Expose
    public Integer flag;

    @SerializedName("user_data")
    @Expose

    public ProfileData profileData;

    @SerializedName("message")
    @Expose
    public String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class ProfileData {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("new_password")
        @Expose
        private String newPassword;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("company")
        @Expose
        private String company;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("postal_code")
        @Expose
        private String postalCode;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("deposit_amount")
        @Expose
        private String depositAmount;
        @SerializedName("award_points")
        @Expose
        private String awardPoints;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("customer_number")
        @Expose
        private String customerNumber;
        @SerializedName("BTW_Number")
        @Expose
        private String bTWNumber;
        @SerializedName("Payment_Terms")
        @Expose
        private String paymentTerms;
        @SerializedName("IBAN_rekeningnummer")
        @Expose
        private String iBANRekeningnummer;
        @SerializedName("Swift_code/BIC")
        @Expose
        private String swiftCodeBIC;
        @SerializedName("Language")
        @Expose
        private String language;
        @SerializedName("Client_managed_by")
        @Expose
        private String clientManagedBy;
        @SerializedName("monday_from_time")
        @Expose
        private String mondayFromTime;
        @SerializedName("monday_to_time")
        @Expose
        private String mondayToTime;
        @SerializedName("tuesday_from_time")
        @Expose
        private String tuesdayFromTime;
        @SerializedName("tuesday_to_time")
        @Expose
        private String tuesdayToTime;
        @SerializedName("wednesday_from_time")
        @Expose
        private String wednesdayFromTime;
        @SerializedName("wednesday_to_time")
        @Expose
        private String wednesdayToTime;
        @SerializedName("thursday_from_time")
        @Expose
        private String thursdayFromTime;
        @SerializedName("thursday_to_time")
        @Expose
        private String thursdayToTime;
        @SerializedName("friday_from_time")
        @Expose
        private String fridayFromTime;
        @SerializedName("friday_to_time")
        @Expose
        private String fridayToTime;
        @SerializedName("saturday_from_time")
        @Expose
        private String saturdayFromTime;
        @SerializedName("saturday_to_time")
        @Expose
        private String saturdayToTime;
        @SerializedName("sunday_from_time")
        @Expose
        private String sundayFromTime;
        @SerializedName("sunday_to_time")
        @Expose
        private String sundayToTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
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

        public String getDepositAmount() {
            return depositAmount;
        }

        public void setDepositAmount(String depositAmount) {
            this.depositAmount = depositAmount;
        }

        public String getAwardPoints() {
            return awardPoints;
        }

        public void setAwardPoints(String awardPoints) {
            this.awardPoints = awardPoints;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCustomerNumber() {
            return customerNumber;
        }

        public void setCustomerNumber(String customerNumber) {
            this.customerNumber = customerNumber;
        }

        public String getBTWNumber() {
            return bTWNumber;
        }

        public void setBTWNumber(String bTWNumber) {
            this.bTWNumber = bTWNumber;
        }

        public String getPaymentTerms() {
            return paymentTerms;
        }

        public void setPaymentTerms(String paymentTerms) {
            this.paymentTerms = paymentTerms;
        }

        public String getIBANRekeningnummer() {
            return iBANRekeningnummer;
        }

        public void setIBANRekeningnummer(String iBANRekeningnummer) {
            this.iBANRekeningnummer = iBANRekeningnummer;
        }

        public String getSwiftCodeBIC() {
            return swiftCodeBIC;
        }

        public void setSwiftCodeBIC(String swiftCodeBIC) {
            this.swiftCodeBIC = swiftCodeBIC;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getClientManagedBy() {
            return clientManagedBy;
        }

        public void setClientManagedBy(String clientManagedBy) {
            this.clientManagedBy = clientManagedBy;
        }

        public String getMondayFromTime() {
            return mondayFromTime;
        }

        public void setMondayFromTime(String mondayFromTime) {
            this.mondayFromTime = mondayFromTime;
        }

        public String getMondayToTime() {
            return mondayToTime;
        }

        public void setMondayToTime(String mondayToTime) {
            this.mondayToTime = mondayToTime;
        }

        public String getTuesdayFromTime() {
            return tuesdayFromTime;
        }

        public void setTuesdayFromTime(String tuesdayFromTime) {
            this.tuesdayFromTime = tuesdayFromTime;
        }

        public String getTuesdayToTime() {
            return tuesdayToTime;
        }

        public void setTuesdayToTime(String tuesdayToTime) {
            this.tuesdayToTime = tuesdayToTime;
        }

        public String getWednesdayFromTime() {
            return wednesdayFromTime;
        }

        public void setWednesdayFromTime(String wednesdayFromTime) {
            this.wednesdayFromTime = wednesdayFromTime;
        }

        public String getWednesdayToTime() {
            return wednesdayToTime;
        }

        public void setWednesdayToTime(String wednesdayToTime) {
            this.wednesdayToTime = wednesdayToTime;
        }

        public String getThursdayFromTime() {
            return thursdayFromTime;
        }

        public void setThursdayFromTime(String thursdayFromTime) {
            this.thursdayFromTime = thursdayFromTime;
        }

        public String getThursdayToTime() {
            return thursdayToTime;
        }

        public void setThursdayToTime(String thursdayToTime) {
            this.thursdayToTime = thursdayToTime;
        }

        public String getFridayFromTime() {
            return fridayFromTime;
        }

        public void setFridayFromTime(String fridayFromTime) {
            this.fridayFromTime = fridayFromTime;
        }

        public String getFridayToTime() {
            return fridayToTime;
        }

        public void setFridayToTime(String fridayToTime) {
            this.fridayToTime = fridayToTime;
        }

        public String getSaturdayFromTime() {
            return saturdayFromTime;
        }

        public void setSaturdayFromTime(String saturdayFromTime) {
            this.saturdayFromTime = saturdayFromTime;
        }

        public String getSaturdayToTime() {
            return saturdayToTime;
        }

        public void setSaturdayToTime(String saturdayToTime) {
            this.saturdayToTime = saturdayToTime;
        }

        public String getSundayFromTime() {
            return sundayFromTime;
        }

        public void setSundayFromTime(String sundayFromTime) {
            this.sundayFromTime = sundayFromTime;
        }

        public String getSundayToTime() {
            return sundayToTime;
        }

        public void setSundayToTime(String sundayToTime) {
            this.sundayToTime = sundayToTime;
        }

    }
}
