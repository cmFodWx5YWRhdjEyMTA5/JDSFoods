package com.jdsfoods.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankResponse {

    @SerializedName("flag")
    @Expose
    public Integer flag;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public BankData getBankData() {
        return bankData;
    }

    public void setBankData(BankData bankData) {
        this.bankData = bankData;
    }

    @SerializedName("data")
    @Expose
    public BankData bankData;

    public class BankData {


        @SerializedName("account_no")
        @Expose
        public String accountNo;

        public String getAccountNo() {
            return accountNo;
        }

        public void setAccountNo(String accountNo) {
            this.accountNo = accountNo;
        }

        public String getIfscCode() {
            return ifscCode;
        }

        public void setIfscCode(String ifscCode) {
            this.ifscCode = ifscCode;
        }

        public String getNameAccount() {
            return nameAccount;
        }

        public void setNameAccount(String nameAccount) {
            this.nameAccount = nameAccount;
        }

        @SerializedName("ifsc_code")
        @Expose
        public String ifscCode;


        @SerializedName("name_account")
        @Expose
        public String nameAccount;
    }
}
