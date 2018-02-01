package com.yijian.person.util;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;


public class SharePreferenceUtil {
    private static final String DEFAULT_CATEGORY = "default";

    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_ORG_ID = "org_id";
    private static final String KEY_ORG_NAME = "org_name";
    private static final String KEY_GRADE = "grade";

    private static final String KEY_HASLOGINED = "haslogined";
    private static final String KEY_LOGIN_FROM_BETTER_PAD = "login_from_better_pad";
    private static final String KEY_LOGIN_FROM_BETTER_PHONE = "login_from_better_phone";
    private static final String KEY_USERNAME = "user_name";

    private static final String KEY_PASSWORD = "password";


    private static final String KEY_NOTICE_READ_STATE = "notice_read_state_";

    private static final String KEY_PATROL_OFFLINE_KEY = "patrol_offline_key";
    public static final String KEY_JPUSH_REGISTRATION_ID = "jpush_registration_id";
    public static final String KEY_METER_RESULT_NEED_SUMBIT = "meter_result_need_sumbit";
    public static final String KEY_HEAD_ICON_ATTACHMENT_ID = "head_icon_attachment_id ";
    public static final String KEY_HEAD_IMG_URL = "head_img_url";
    public static final String KEY_JPUSH_CAN_PUSH = "jpush_can_push";
    public static final String KEY_JPUSH_ALIAS = "jpush_alias";
    public static final String KEY_HAS_JPUSH_ALIAS = "has_jpush_alias";

    public static final String KEY_ACCOUNT_INFO = "account-";
    public static final String KEY_AUTH_CODE_LIST = "auth_code_list_jsonarray-";
    private static final String KEY_JOB_NAME = "job_name";
    private static final String KEY_NICKNAME = "nick_name";
    private static final String KEY_TELEPHONE = "telephone";
    private static final String KEY_IS_TRACE_STARTED = "is_trace_started";
    private static final String KEY_IS_GATHER_STARTED = "is_trace_started";

    public static void setHeadIconAttachmentId(long attachmentId) {
        setLong(DEFAULT_CATEGORY, KEY_HEAD_ICON_ATTACHMENT_ID, attachmentId);
    }

    public static long getHeadIconAttachmentId() {
        return getLong(DEFAULT_CATEGORY, KEY_HEAD_ICON_ATTACHMENT_ID);
    }

    private static long getLong(String category, String key) {
        long result = 0;
        Application application = ApplicationHolder.getmApplication();
        if (application == null) {
            return result;
        }
        android.content.SharedPreferences references = application.getSharedPreferences(category, Context.MODE_PRIVATE);
        result = references.getLong(key, 0);
        return result;

    }

    private static boolean setLong(String category, String key, long value) {
        boolean result = false;
        Application application = ApplicationHolder.getmApplication();
        if (application == null)
            return result;

        android.content.SharedPreferences references = application.getSharedPreferences(category, Context.MODE_PRIVATE);
        result = references.edit().putLong(key, value).commit();
        return result;
    }



    public static void setNoticeReadState(long noticeId) {
        setString(KEY_NOTICE_READ_STATE + noticeId, String.valueOf(true));
    }

    public static boolean getNoticeReadState(long noticeId) {

        String result = getString(KEY_NOTICE_READ_STATE + noticeId, "");
        if (Boolean.valueOf(result))
            return Boolean.valueOf(result);
        return false;
    }


    public static void setUserId(long userId) {
        setString(KEY_USER_ID, String.valueOf(userId));
    }

    public static long getUserId() {
        String value = getString(KEY_USER_ID, "");
        if (TextUtils.isEmpty(value))
            return 0;
        else
            return Long.valueOf(value);

    }

    public static Boolean getHaslogined() {
        return getBoolean(KEY_HASLOGINED, false);
    }


    public static void setHaslogined(Boolean mHaslogined) {
        setBoolean(KEY_HASLOGINED, mHaslogined);
    }

    public static void setUsername(String username) {
        setString(KEY_USERNAME, username);
    }

    public static String getUsername() {
        return getString(KEY_USERNAME, "");
    }

    public static void setNickName(String username) {
        setString(KEY_NICKNAME, username);
    }

    public static String getNickName() {
        return getString(KEY_NICKNAME, "");
    }

    public static void setPassword(String password) {
        setString(KEY_PASSWORD, password);
    }

    public static String getPassword() {
        return getString(KEY_PASSWORD, "");
    }


    /**
     * 保存 String 值
     *
     * @param key   键值
     * @param value 值
     * @return
     */
    public static boolean setString(String key, String value) {
        return setString(DEFAULT_CATEGORY, key, value);
    }

    /**
     * 保存 String 值
     *
     * @param category 分类名
     * @param key      键值
     * @param value    值
     * @return
     */
    public static boolean setString(String category, String key, String value) {
        boolean result = false;
        Application application = ApplicationHolder.getmApplication();
        if (application == null)
            return result;

        android.content.SharedPreferences references = application.getSharedPreferences(category, Context.MODE_PRIVATE);
        result = references.edit().putString(key, value).commit();
        return result;
    }


    /**
     * 读取  String 值
     *
     * @param key          键值
     * @param defaultValue 默认值
     * @return
     */
    public static String getString(String key, String defaultValue) {
        return getString(DEFAULT_CATEGORY, key, defaultValue);
    }

    /**
     * 读取 String 值
     *
     * @param category     分类名
     * @param key          键值
     * @param defaultValue 默认值
     * @return
     */
    public static String getString(String category, String key, String defaultValue) {
        String result = defaultValue;
        Application application = ApplicationHolder.getmApplication();
        if (application == null)
            return result;

        android.content.SharedPreferences references = application.getSharedPreferences(category, Context.MODE_PRIVATE);
        result = references.getString(key, defaultValue);
        return result;
    }


    /**
     * 保存  Boolean 值
     *
     * @param key   键值
     * @param value 值
     * @return
     */
    public static boolean setBoolean(String key, boolean value) {
        return setBoolean(DEFAULT_CATEGORY, key, value);
    }

    /**
     * 保存  Boolean 值
     *
     * @param category 分类名
     * @param key      键值
     * @param value    值
     * @return
     */
    public static boolean setBoolean(String category, String key, boolean value) {
        boolean result = false;
        Application application = ApplicationHolder.getmApplication();
        if (application == null)
            return result;

        android.content.SharedPreferences references = application.getSharedPreferences(category, Context.MODE_PRIVATE);
        result = references.edit().putBoolean(key, value).commit();
        return result;
    }


    /**
     * 读取 Boolean 值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static Boolean getBoolean(String key, boolean defaultValue) {
        return getBoolean(DEFAULT_CATEGORY, key, defaultValue);
    }


    /**
     * 读取 Boolean 值
     *
     * @param category
     * @param key
     * @param defaultValue
     * @return
     */
    public static Boolean getBoolean(String category, String key, boolean defaultValue) {
        Boolean result = defaultValue;
        Application application = ApplicationHolder.getmApplication();
        if (application == null)
            return result;
        android.content.SharedPreferences references = application.getSharedPreferences(category, Context.MODE_PRIVATE);
        result = references.getBoolean(key, defaultValue);
        return result;
    }


    //三防平板
    public static void setLoginFromBetterPad(boolean loginFromBetterPad) {
        setBoolean(KEY_LOGIN_FROM_BETTER_PAD, loginFromBetterPad);
    }


    public static Boolean getLoginFromBetterPad() {
        return getBoolean(KEY_LOGIN_FROM_BETTER_PAD, false);
    }

    //三防手机
    public static void setLoginFromBetterPhone(boolean loginFromBetterPhone) {
        setBoolean(KEY_LOGIN_FROM_BETTER_PHONE, loginFromBetterPhone);
    }

    public static Boolean getLoginFromBetterPhone() {
        return getBoolean(KEY_LOGIN_FROM_BETTER_PHONE, false);
    }


    public static void setJpushRegistionId(String jpushRegistrationId) {
        setString(SharePreferenceUtil.KEY_JPUSH_REGISTRATION_ID, jpushRegistrationId);
    }

    public static String getJpushRegistionId() {
        return getString(SharePreferenceUtil.KEY_JPUSH_REGISTRATION_ID, "");
    }

    public static void setNeedSumbit(boolean needSumbit) {
        setBoolean(KEY_METER_RESULT_NEED_SUMBIT, needSumbit);
    }

    public static boolean getNeedSumbit() {
        return getBoolean(KEY_METER_RESULT_NEED_SUMBIT, false);
    }

    public static boolean getCanPush() {
        return getBoolean(KEY_JPUSH_CAN_PUSH, false);
    }

    public static void setCanPush(boolean canPush) {
        setBoolean(KEY_JPUSH_CAN_PUSH, canPush);
    }

    public static void setJpushAlias(String alias) {
        setString(KEY_JPUSH_ALIAS, alias);
        setBoolean(KEY_HAS_JPUSH_ALIAS, true);
    }

    public static String getJpushAlias(String alias) {
        return getString(KEY_JPUSH_ALIAS, "");
    }

    public static boolean hasJpushAlias() {
        return getBoolean(KEY_HAS_JPUSH_ALIAS, false);
    }


    public static void setOrgId(long orgId) {
        setString(KEY_ORG_ID, String.valueOf(orgId));
    }

    public static long getOrgId() {
        String value = getString(KEY_ORG_ID, "");
        if (TextUtils.isEmpty(value))
            return 0;
        else
            return Long.valueOf(value);

    }

    public static void setOrgName(String orgName) {
        setString(KEY_ORG_NAME, orgName);
    }

    public static String getOrgName() {
        return getString(KEY_ORG_NAME, "");
    }


    public static long getGrade() {
        String value = getString(KEY_GRADE, "");
        if (TextUtils.isEmpty(value))
            return 0;
        else
            return Long.valueOf(value);
    }

    public static void setGrade(long grade) {
        setString(KEY_GRADE, String.valueOf(grade));
    }

    public static void setJobName(String jobName) {
        setString(KEY_JOB_NAME, jobName);
    }

    public static String getJobName() {
        return getString(KEY_JOB_NAME, "");
    }

    public static String getTelephone() {
        return getString(KEY_TELEPHONE, "");
    }

    public static void setTelephone(String telephone) {
        setString(KEY_TELEPHONE, telephone);
    }

    public static boolean getIsTraceStarted() {
        return getBoolean(KEY_IS_TRACE_STARTED, false);

    }

    public static void setIsTraceStarted(boolean isTraceStarted) {
        setBoolean(KEY_IS_TRACE_STARTED, isTraceStarted);
    }

    public static boolean getIsGatherStarted() {
        return getBoolean(KEY_IS_GATHER_STARTED, false);
    }

    public static void setIsGatherStarted(boolean isGatherStarted) {
        setBoolean(KEY_IS_GATHER_STARTED, isGatherStarted);
    }
}
