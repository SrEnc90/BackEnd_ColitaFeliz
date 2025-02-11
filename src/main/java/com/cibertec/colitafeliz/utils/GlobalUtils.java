package com.cibertec.colitafeliz.utils;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GlobalUtils {

    GlobalUtils() {

    }

    public static ResponseEntity<String> getResponseEntity(String message, HttpStatus status) {
        String jsonMessage = "{\"message\":\"" + message + "\"}";
        return ResponseEntity.status(status).body(jsonMessage);
    }

    public static String getUUID() {
        Date date = new Date();
        long time = date.getTime();
        return "COMPROBANTE-" + time;
    }

    public static JSONArray getJsonArrayFromString(String data) throws JSONException {
        JSONArray jsonArray = new JSONArray(data);
        return jsonArray;
    }

    public static Map<String, Object> getMapFromJson(String data) {
        if(!Strings.isNullOrEmpty(data)) {
            return new Gson().fromJson(data, new TypeToken<Map<String, Object>>(){
            }.getType());
        }
        return new HashMap<>();
    }
}
