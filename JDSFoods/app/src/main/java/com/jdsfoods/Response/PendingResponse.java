package com.jdsfoods.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dikhong on 30-07-2018.
 */

public class PendingResponse {
    @SerializedName("flag")
    @Expose
    public Integer flag;

    @SerializedName("pending")
    @Expose
    List<Pending> pendingList;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public List<Pending> getPendingList() {
        return pendingList;
    }

    public void setPendingList(List<Pending> pendingList) {
        this.pendingList = pendingList;
    }

    public List<Partial> getPartialList() {
        return partialList;
    }

    public void setPartialList(List<Partial> partialList) {
        this.partialList = partialList;
    }

    @SerializedName("partial")
    @Expose
    List<Partial> partialList;

    public class Pending {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("reference_no")
        @Expose
        private String referenceNo;
        @SerializedName("total")
        @Expose
        private String total;
        @SerializedName("product_tax")
        @Expose
        private String productTax;
        @SerializedName("total_tax")
        @Expose
        private String totalTax;
        @SerializedName("grand_total")
        @Expose
        private String grandTotal;
        @SerializedName("pending_amount")
        @Expose
        private String pendingAmount;
        @SerializedName("payment_status")
        @Expose
        private String paymentStatus;
        @SerializedName("paid")
        @Expose
        private String paid;
        @SerializedName("total_items")
        @Expose
        private String totalItems;
        @SerializedName("sale_payments")
        @Expose
        private List<SalePayment> salePayments;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getReferenceNo() {
            return referenceNo;
        }

        public void setReferenceNo(String referenceNo) {
            this.referenceNo = referenceNo;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getProductTax() {
            return productTax;
        }

        public void setProductTax(String productTax) {
            this.productTax = productTax;
        }

        public String getTotalTax() {
            return totalTax;
        }

        public void setTotalTax(String totalTax) {
            this.totalTax = totalTax;
        }

        public String getGrandTotal() {
            return grandTotal;
        }

        public void setGrandTotal(String grandTotal) {
            this.grandTotal = grandTotal;
        }

        public String getPendingAmount() {
            return pendingAmount;
        }

        public void setPendingAmount(String pendingAmount) {
            this.pendingAmount = pendingAmount;
        }

        public String getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public String getPaid() {
            return paid;
        }

        public void setPaid(String paid) {
            this.paid = paid;
        }

        public String getTotalItems() {
            return totalItems;
        }

        public void setTotalItems(String totalItems) {
            this.totalItems = totalItems;
        }

        public List<SalePayment> getSalePayments() {
            return salePayments;
        }

        public void setSalePayments(List<SalePayment> salePayments) {
            this.salePayments = salePayments;
        }
    }

    public class Partial {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("reference_no")
        @Expose
        private String referenceNo;
        @SerializedName("total")
        @Expose
        private String total;
        @SerializedName("product_tax")
        @Expose
        private String productTax;
        @SerializedName("total_tax")
        @Expose
        private String totalTax;
        @SerializedName("grand_total")
        @Expose
        private String grandTotal;
        @SerializedName("pending_amount")
        @Expose
        private String pendingAmount;
        @SerializedName("payment_status")
        @Expose
        private String paymentStatus;
        @SerializedName("paid")
        @Expose
        private String paid;
        @SerializedName("total_items")
        @Expose
        private String totalItems;
        @SerializedName("sale_payments")
        @Expose
        private List<SalePayment> salePayments;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getReferenceNo() {
            return referenceNo;
        }

        public void setReferenceNo(String referenceNo) {
            this.referenceNo = referenceNo;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getProductTax() {
            return productTax;
        }

        public void setProductTax(String productTax) {
            this.productTax = productTax;
        }

        public String getTotalTax() {
            return totalTax;
        }

        public void setTotalTax(String totalTax) {
            this.totalTax = totalTax;
        }

        public String getGrandTotal() {
            return grandTotal;
        }

        public void setGrandTotal(String grandTotal) {
            this.grandTotal = grandTotal;
        }

        public String getPendingAmount() {
            return pendingAmount;
        }

        public void setPendingAmount(String pendingAmount) {
            this.pendingAmount = pendingAmount;
        }

        public String getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public String getPaid() {
            return paid;
        }

        public void setPaid(String paid) {
            this.paid = paid;
        }

        public String getTotalItems() {
            return totalItems;
        }

        public void setTotalItems(String totalItems) {
            this.totalItems = totalItems;
        }

        public List<SalePayment> getSalePayments() {
            return salePayments;
        }

        public void setSalePayments(List<SalePayment> salePayments) {
            this.salePayments = salePayments;
        }
    }
}
