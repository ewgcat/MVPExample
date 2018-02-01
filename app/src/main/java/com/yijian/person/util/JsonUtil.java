package com.yijian.person.util;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * desc:
 *
 * @author:nickming date:2016/1/26
 * time: 15:42
 * e-mail：962570483@qq.com
 */

public class JsonUtil {

    private static final String STRING_NULL = "null";
    private static final String BOOL_VALUE_TRUE = "true";
    private static final String BOOL_VALUE_1 = "1";
    private static final String DEFAULT_VALUE_STRING = "";
    public static final String TAG = "JsonUtil-JSONException";


    private static Gson gson;


    public static Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }


    /**
     * 从字符串获取json对象,如果发生异常则返回null
     */
    public static JSONObject getJsonObjectFromString(String jsonstr) {
        if (TextUtils.isEmpty(jsonstr)) {
            return null;
        }
        try {
            JSONObject obj;
            obj = new JSONObject(jsonstr);
            return obj;
        } catch (JSONException e) {
            //Logger.e(TAG, e.getMessage());
        }
        return null;
    }


    /**
     * 从字符串获取json数组,如果发生异常则返回null
     */
    public static JSONArray getJsonArrayFromString(String jsonstr) {
        if (TextUtils.isEmpty(jsonstr)) {
            return null;
        }

        try {
            JSONArray obj;
            obj = new JSONArray(jsonstr);
            return obj;
        } catch (JSONException e) {
            //Logger.e(TAG, e.getMessage());
        }
        return null;
    }


    /**
     * 获得字符串
     *
     * @param json json对象
     * @param name 要读取的键值
     * @return 如果发生异常则返回默认值, 则返回""
     */
    public static String getString(JSONObject json, String name) {
        return getString(json, name, DEFAULT_VALUE_STRING);
    }


    /**
     * 获得字符串
     *
     * @param json         json对象
     * @param name         要读取的键值
     * @param defaultValue 默认值,如果发生异常则返回默认值
     * @return
     */
    public static String getString(JSONObject json, String name, String defaultValue) {
        String result = defaultValue;
        if (checkJson(json, name)) {
            try {
                result = json.getString(name);
            } catch (JSONException e) {
                result = defaultValue;
                //Logger.e(TAG, e.getMessage());
            }
        }
        return result;
    }


    /**
     * 获得整数
     *
     * @param json json对象
     * @param name 要读取的键值
     * @return 如果发生异常则返回默认值, 则返回-1
     */
    public static int getInt(JSONObject json, String name) {
        return getInt(json, name, -1);
    }


    /**
     * 获得整数
     *
     * @param json         json对象
     * @param name         要读取的键值
     * @param defaultValue 默认值,如果发生异常则返回默认值
     * @return
     */
    public static int getInt(JSONObject json, String name, int defaultValue) {
        int result = defaultValue;
        if (checkJson(json, name)) {
            try {
                result = json.getInt(name);
            } catch (JSONException e) {
                result = defaultValue;
            }
        }
        return result;
    }


    /**
     * 获得长整数
     *
     * @param json json对象
     * @param name 要读取的键值
     * @return 如果发生异常则返回-1
     */
    public static Long getLong(JSONObject json, String name) {
        return getLong(json, name, -1);
    }


    /**
     * 获得长整数
     *
     * @param json         json对象
     * @param name         要读取的键值
     * @param defaultValue 默认值,如果发生异常则返回默认值
     * @return
     */
    public static Long getLong(JSONObject json, String name, long defaultValue) {
        long result = defaultValue;
        if (checkJson(json, name)) {
            try {
                result = json.getLong(name);
            } catch (JSONException e) {
                result = defaultValue;
            }
        }
        return result;
    }


    /**
     * 获得双精度浮点数
     *
     * @param json json对象
     * @param name 要读取的键值
     * @return 如果发生异常则返回0
     */
    public static Double getDouble(JSONObject json, String name) {
        return getDouble(json, name, 0);
    }


    /**
     * 获得双精度浮点数
     *
     * @param json         json对象
     * @param name         要读取的键值
     * @param defaultValue 默认值,如果发生异常则返回默认值
     * @return
     */
    public static Double getDouble(JSONObject json, String name, double defaultValue) {
        double result = defaultValue;
        if (checkJson(json, name)) {
            try {
                result = json.getDouble(name);
            } catch (JSONException e) {
                result = defaultValue;
                //Logger.e(TAG, e.getMessage());
            }
        }
        return result;
    }


    /**
     * 如果为1则true,否则为false
     *
     * @param json
     * @param name
     * @return
     */
    public static Boolean getIntBoolean(JSONObject json, String name) {
        return getBoolean(json, name);
    }


    /**
     * 获得boolean
     *
     * @param json json对象
     * @param name 要读取的键值
     * @return 如果发生异常则返回false
     */
    public static Boolean getBoolean(JSONObject json, String name) {
        return getBoolean(json, name, false);
    }


    /**
     * 获得boolean
     *
     * @param json         json对象
     * @param name         要读取的键值
     * @param defaultValue 默认值,如果发生异常则返回默认值
     * @return
     */
    public static Boolean getBoolean(JSONObject json, String name, boolean defaultValue) {
        boolean result = defaultValue;
        if (checkJson(json, name)) {
            try {
                String value = json.getString(name);
                result = BOOL_VALUE_1.equals(value) || BOOL_VALUE_TRUE.equalsIgnoreCase(value);
            } catch (JSONException e) {
                result = defaultValue;
                //Logger.e(TAG, e.getMessage());
            }
        }
        return result;
    }


    /**
     * 获得是否为0的boolean值，用于hasnext
     *
     * @param json json对象
     * @param name 要读取的键值
     */
    public static Boolean getIsZero(JSONObject json, String name) {
        int intValue = getInt(json, name);
        boolean result = (intValue == 0) ? true : false;
        return result;
    }


    /**
     * 获得timestamp
     *
     * @param json json对象
     * @param name 要读取的键值
     * @return 如果发生异常则返回0
     */
    public static Long getTimeStamp(JSONObject json, String name) {
        return getTimeStamp(json, name, 0);
    }


    /**
     * 获得timestamp
     *
     * @param json         json对象
     * @param name         要读取的键值
     * @param defaultValue 默认值,如果发生异常则返回默认值
     * @return
     */
    public static Long getTimeStamp(JSONObject json, String name, long defaultValue) {
        return getLong(json, name, defaultValue);
    }


    /**
     * 获得jsonObject
     *
     * @param json json对象
     * @param name 要读取的键值
     * @return 如果发生异常则返回new JSONObject
     */
    public static JSONObject getJsonObject(JSONObject json, String name) {
        JSONObject result = null;
        if (checkJson(json, name)) {
            try {
                result = json.getJSONObject(name);
            } catch (JSONException e) {
                result = new JSONObject();
                Object object = null;
                try {
                    object = json.get(name);
                } catch (JSONException e1) {
                }
                if (object == null) {
                    object = STRING_NULL;
                }
            }
        } else {
            result = new JSONObject();
        }
        return result;
    }


    /**
     * 获得jsonArray
     *
     * @param json json对象
     * @param name 要读取的键值
     * @return 如果发生异常则返回new JSONArray
     */
    public static JSONArray getJsonArray(JSONObject json, String name) {
        JSONArray result = null;
        if (checkJson(json, name)) {
            try {
                result = json.getJSONArray(name);
            } catch (JSONException e) {
                result = new JSONArray();
            }
        } else {
            result = new JSONArray();
        }
        return result;
    }


    /**
     * 检查json合法性
     *
     * @param json
     * @return
     */
    private static boolean checkJson(JSONObject json, String keyName) {
        boolean result = true;
        if (result && json == null) {
            result = false;
        }
        if (result && !json.has(keyName)) {
            result = false;
        }
        if (result && json.isNull(keyName)) {
            result = false;
        }
        return result;
    }


    /**
     * 获得字符串
     *
     * @param json json对象
     * @return 如果发生异常则返回默认值, 则返回""
     */
    public static String getString(JSONArray json, int index) {
        return getString(json, index, DEFAULT_VALUE_STRING);
    }


    /**
     * 获得字符串
     *
     * @param json         json对象
     * @param defaultValue 默认值,如果发生异常则返回默认值
     * @return
     */
    public static String getString(JSONArray json, int index, String defaultValue) {
        String result = defaultValue;
        if (checkJson(json, index)) {
            try {
                result = json.getString(index);
            } catch (JSONException e) {
                result = defaultValue;
            }
        }
        return result;
    }


    /**
     * 获得整数
     *
     * @param json json对象
     * @return 如果发生异常则返回默认值, 则返回-1
     */
    public static int getInt(JSONArray json, int index) {
        return getInt(json, index, -1);
    }


    /**
     * 获得整数
     *
     * @param json         json对象
     * @param defaultValue 默认值,如果发生异常则返回默认值
     * @return
     */
    public static int getInt(JSONArray json, int index, int defaultValue) {
        int result = defaultValue;
        if (checkJson(json, index)) {
            try {
                result = json.getInt(index);
            } catch (JSONException e) {
                result = defaultValue;
            }
        }
        return result;
    }


    /**
     * 获得jsonObject
     *
     * @param json json对象
     * @return 如果发生异常则返回new JSONObject
     */
    public static JSONObject getJsonObject(JSONArray json, int index) {
        JSONObject result = null;
        if (checkJson(json, index)) {
            try {
                result = json.getJSONObject(index);
            } catch (JSONException e) {
                result = new JSONObject();
                Object object = null;
                try {
                    object = json.get(index);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                if (object == null) {
                    object = STRING_NULL;
                }
            }
        } else {
            result = new JSONObject();
        }
        return result;
    }


    /**
     * 检查json合法性
     *
     * @param json
     * @return
     */
    private static boolean checkJson(JSONArray json, int index) {
        boolean result = true;
        if (json == null) {
            result = false;
        }
        //检查index是否合法
        if (result && (index < 0 || index >= json.length())) {
            result = false;
        }
        return result;
    }


}

