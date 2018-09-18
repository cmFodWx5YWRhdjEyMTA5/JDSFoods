package com.jdsfoods.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryResponse implements Parcelable{

    @SerializedName("flag")
    @Expose
    public Integer flag;

    protected HistoryResponse(Parcel in) {
        if (in.readByte() == 0) {
            flag = null;
        } else {
            flag = in.readInt();
        }
    }

    public static final Creator<HistoryResponse> CREATOR = new Creator<HistoryResponse>() {
        @Override
        public HistoryResponse createFromParcel(Parcel in) {
            return new HistoryResponse(in);
        }

        @Override
        public HistoryResponse[] newArray(int size) {
            return new HistoryResponse[size];
        }
    };

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public List<SaleData> getSaleDataList() {
        return saleDataList;
    }

    public void setSaleDataList(List<SaleData> saleDataList) {
        this.saleDataList = saleDataList;
    }

    @SerializedName("data")

    @Expose
    List<SaleData> saleDataList;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (flag == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(flag);
        }
    }

    public static class SaleData implements Parcelable{
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("reference_no")
        @Expose
        private String referenceNo;
        @SerializedName("biller")
        @Expose
        private String biller;
        @SerializedName("customer")
        @Expose
        private String customer;
        @SerializedName("sale_status")
        @Expose
        private String saleStatus;
        @SerializedName("grand_total")
        @Expose
        private String grandTotal;
        @SerializedName("paid")
        @Expose
        private String paid;
        @SerializedName("balance")
        @Expose
        private String balance;
        @SerializedName("payment_status")
        @Expose
        private String paymentStatus;
        @SerializedName("attachment")
        @Expose
        private String attachment;
        @SerializedName("return_id")
        @Expose
        private String returnId;
        @SerializedName("total_items")
        @Expose
        private String totalItems;

        protected SaleData(Parcel in) {
            id = in.readString();
            date = in.readString();
            referenceNo = in.readString();
            biller = in.readString();
            customer = in.readString();
            saleStatus = in.readString();
            grandTotal = in.readString();
            paid = in.readString();
            balance = in.readString();
            paymentStatus = in.readString();
            attachment = in.readString();
            returnId = in.readString();
            totalItems = in.readString();
        }

        public static final Creator<SaleData> CREATOR = new Creator<SaleData>() {
            @Override
            public SaleData createFromParcel(Parcel in) {
                return new SaleData(in);
            }

            @Override
            public SaleData[] newArray(int size) {
                return new SaleData[size];
            }
        };

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

        public String getBiller() {
            return biller;
        }

        public void setBiller(String biller) {
            this.biller = biller;
        }

        public String getCustomer() {
            return customer;
        }

        public void setCustomer(String customer) {
            this.customer = customer;
        }

        public String getSaleStatus() {
            return saleStatus;
        }

        public void setSaleStatus(String saleStatus) {
            this.saleStatus = saleStatus;
        }

        public String getGrandTotal() {
            return grandTotal;
        }

        public void setGrandTotal(String grandTotal) {
            this.grandTotal = grandTotal;
        }

        public String getPaid() {
            return paid;
        }

        public void setPaid(String paid) {
            this.paid = paid;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }

        public String getReturnId() {
            return returnId;
        }

        public void setReturnId(String returnId) {
            this.returnId = returnId;
        }

        public String getTotalItems() {
            return totalItems;
        }

        public void setTotalItems(String totalItems) {
            this.totalItems = totalItems;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(date);
            dest.writeString(referenceNo);
            dest.writeString(biller);
            dest.writeString(customer);
            dest.writeString(saleStatus);
            dest.writeString(grandTotal);
            dest.writeString(paid);
            dest.writeString(balance);
            dest.writeString(paymentStatus);
            dest.writeString(attachment);
            dest.writeString(returnId);
            dest.writeString(totalItems);
        }
    }
}
