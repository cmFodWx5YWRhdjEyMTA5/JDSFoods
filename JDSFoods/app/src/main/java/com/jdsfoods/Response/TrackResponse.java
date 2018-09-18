package com.jdsfoods.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dikhong on 31-07-2018.
 */

public class TrackResponse {

    @SerializedName("flag")
    @Expose
    public Integer flag;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public TrackOrder getTrackOrder() {
        return trackOrder;
    }

    public void setTrackOrder(TrackOrder trackOrder) {
        this.trackOrder = trackOrder;
    }

    @SerializedName("track_order")
    @Expose
    public TrackOrder trackOrder;

    public class TrackOrder {

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getSaleId() {
            return saleId;
        }

        public void setSaleId(String saleId) {
            this.saleId = saleId;
        }

        public Integer getOrderConfirmed() {
            return orderConfirmed;
        }

        public void setOrderConfirmed(Integer orderConfirmed) {
            this.orderConfirmed = orderConfirmed;
        }

        public Integer getOrderPlaced() {
            return orderPlaced;
        }

        public void setOrderPlaced(Integer orderPlaced) {
            this.orderPlaced = orderPlaced;
        }

        public Integer getOrderDelivered() {
            return orderDelivered;
        }

        public void setOrderDelivered(Integer orderDelivered) {
            this.orderDelivered = orderDelivered;
        }

        public Integer getOrderShip() {
            return orderShip;
        }

        public void setOrderShip(Integer orderShip) {
            this.orderShip = orderShip;
        }

        @SerializedName("date")

        @Expose
        public String date;

        @SerializedName("sale_id")
        @Expose
        public String saleId;

        @SerializedName("order_confirmed")
        @Expose
        public Integer orderConfirmed;

        @SerializedName("order_placed")
        @Expose
        public Integer orderPlaced;

        @SerializedName("order_delivered")
        @Expose
        public Integer orderDelivered;

        @SerializedName("order_ship")
        @Expose
        public Integer orderShip;

    }
}
