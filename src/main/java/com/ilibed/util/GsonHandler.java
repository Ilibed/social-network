package com.ilibed.util;

import com.google.gson.Gson;

public class GsonHandler {
    public static <T> T fromJson(Class<T> outClass, String data){
        Gson gson = new Gson();
        return gson.fromJson(data, outClass);
    }

    public static <T> String toJson(T object){
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}
