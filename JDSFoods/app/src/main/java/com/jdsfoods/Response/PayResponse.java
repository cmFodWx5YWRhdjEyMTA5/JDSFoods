package com.jdsfoods.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PayResponse {

    @SerializedName("flag")
    @Expose
    public Integer flag;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public PaymentUrl getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(PaymentUrl paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    @SerializedName("payment_url")
    @Expose
    public PaymentUrl paymentUrl;

    public class PaymentUrl {

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public String getOnlinePaymentStatus() {
            return onlinePaymentStatus;
        }

        public void setOnlinePaymentStatus(String onlinePaymentStatus) {
            this.onlinePaymentStatus = onlinePaymentStatus;
        }

        public String getPaymentUrl() {
            return paymentUrl;
        }

        public void setPaymentUrl(String paymentUrl) {
            this.paymentUrl = paymentUrl;
        }

        @SerializedName("transaction_id")
        @Expose
        public String transactionId;

        @SerializedName("online_payment_status")
        @Expose
        public String onlinePaymentStatus;

        @SerializedName("payment_url")
        @Expose
        public String paymentUrl;

    }
}
