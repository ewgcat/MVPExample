package com.yijian.person.model.http.convert;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;


public class JSONObjectConverter implements Converter<ResponseBody, JSONObject> {

    public static final JSONObjectConverter INSTANCE = new JSONObjectConverter();

    @Override
    public JSONObject convert(ResponseBody responseBody) throws IOException {
        JSONObject jsonObject = null;
        try {
            String result = responseBody.string();
            jsonObject = new JSONObject(result);
            return jsonObject;
        } catch (JSONException e) {
            Log.i("JSONObjectConverter", e.toString());
        }
        return jsonObject;
    }
}
