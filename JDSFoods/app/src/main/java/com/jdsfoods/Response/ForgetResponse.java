package com.jdsfoods.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dikhong on 26-07-2018.
 */

public class ForgetResponse {
    @SerializedName("flag")
    @Expose
    public Integer flag;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("data")
    @Expose
    public String data;

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    @SerializedName("quote_id")
    @Expose
    public String quoteId;

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
}
