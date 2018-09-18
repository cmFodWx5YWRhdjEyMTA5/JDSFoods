package com.jdsfoods.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedHashMap;

public class PaymentResponse {

    @SerializedName("flag")
    @Expose
    public Integer flag;

    @SerializedName("counter")
    @Expose
    public Integer counter;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public MethodData getMethodData() {
        return methodData;
    }

    public void setMethodData(MethodData methodData) {
        this.methodData = methodData;
    }

    @SerializedName("method")
    @Expose
    public MethodData methodData;

    public static class MethodData {

        public LinkedHashMap<String, KeyList> getData() {
            return data;
        }

        public void setData(LinkedHashMap<String, KeyList> data) {
            this.data = data;
        }

        public LinkedHashMap<String, KeyList> data;

        public class KeyList {


            @SerializedName("id")
            @Expose
            public String id;


            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public Amount getAmount() {
                return amount;
            }

            public void setAmount(Amount amount) {
                this.amount = amount;
            }


            public String getResource() {
                return resource;
            }

            public void setResource(String resource) {
                this.resource = resource;
            }

            @SerializedName("description")

            @Expose
            public String description;

            @SerializedName("amount")
            @Expose
            public Amount amount;

            @SerializedName("image")
            @Expose
            public ImageData image;


            public ImageData getImage() {
                return image;
            }

            public void setImage(ImageData image) {
                this.image = image;
            }

            @SerializedName("resource")
            @Expose

            public String resource;

            private class Amount {

                @SerializedName("minimum")
                @Expose
                public String minimum;

                public String getMinimum() {
                    return minimum;
                }

                public void setMinimum(String minimum) {
                    this.minimum = minimum;
                }

                public String getMaximum() {
                    return maximum;
                }

                public void setMaximum(String maximum) {
                    this.maximum = maximum;
                }

                @SerializedName("maximum")
                @Expose
                public String maximum;
            }

            public class ImageData {


                @SerializedName("normal")
                @Expose
                public String normal;

                public String getNormal() {
                    return normal;
                }

                public void setNormal(String normal) {
                    this.normal = normal;
                }

                public String getBigger() {
                    return bigger;
                }

                public void setBigger(String bigger) {
                    this.bigger = bigger;
                }

                @SerializedName("bigger")
                @Expose
                public String bigger;
            }
        }
    }
}
