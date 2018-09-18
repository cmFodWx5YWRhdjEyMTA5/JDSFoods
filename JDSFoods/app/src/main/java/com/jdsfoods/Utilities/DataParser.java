package com.jdsfoods.Utilities;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.jdsfoods.Response.PaymentResponse;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

public class DataParser implements JsonDeserializer<PaymentResponse.MethodData> {


    @Override
    public PaymentResponse.MethodData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        PaymentResponse.MethodData result = new PaymentResponse.MethodData();


        try {
            final LinkedHashMap<String, PaymentResponse.MethodData.KeyList> map = readServiceUrlMap(json.getAsJsonObject());

            if(map != null) {
                result.data = map;
            }

        }catch (JsonSyntaxException ex){
            return null;
        }

        return result;
    }


    private LinkedHashMap<String, PaymentResponse.MethodData.KeyList> readServiceUrlMap(final JsonObject jsonObject) throws JsonSyntaxException {

        if(jsonObject == null) {
            return null;
        }
        Gson gson = new Gson();

        LinkedHashMap<String, PaymentResponse.MethodData.KeyList> products = new LinkedHashMap<>();

        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {

            String key = entry.getKey();
            PaymentResponse.MethodData.KeyList value = gson.fromJson(entry.getValue(), PaymentResponse.MethodData.KeyList.class);
            products.put(key, value);
        }
        return products;
    }}