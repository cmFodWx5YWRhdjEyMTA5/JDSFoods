package com.jdsfoods.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SaleDetailResponse {

    @SerializedName("flag")
    @Expose
    public Integer flag;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public SaleDetail getSaleDetail() {
        return saleDetail;
    }

    public void setSaleDetail(SaleDetail saleDetail) {
        this.saleDetail = saleDetail;
    }

    @SerializedName("data")
    @Expose
    public SaleDetail saleDetail;

    public class SaleDetail {
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
        @SerializedName("total_discount")
        @Expose
        private String totalDiscount;
        @SerializedName("shipping")
        @Expose
        private String shipping;
        @SerializedName("sale_items")
        @Expose
        public List<SalePayment> saleItems;

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

        public String getTotalDiscount() {
            return totalDiscount;
        }

        public void setTotalDiscount(String totalDiscount) {
            this.totalDiscount = totalDiscount;
        }

        public String getShipping() {
            return shipping;
        }

        public void setShipping(String shipping) {
            this.shipping = shipping;
        }

        public List<SalePayment> getSaleItems() {
            return saleItems;
        }

        public void setSaleItems(List<SalePayment> saleItems) {
            this.saleItems = saleItems;
        }
    }
}
