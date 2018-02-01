package com.xinwis.okhttputils;


import android.content.Context;

import com.xinwis.okhttputils.builder.GetBuilder;
import com.xinwis.okhttputils.builder.HeadBuilder;
import com.xinwis.okhttputils.builder.OtherRequestBuilder;
import com.xinwis.okhttputils.builder.PostFileBuilder;
import com.xinwis.okhttputils.builder.PostFormBuilder;
import com.xinwis.okhttputils.builder.PostStringBuilder;
import com.xinwis.okhttputils.callback.Callback;
import com.xinwis.okhttputils.cookie.store.NovateCookieManger;
import com.xinwis.okhttputils.https.AllowMyHostnameVerifier;
import com.xinwis.okhttputils.httpsfactroy.HttpsFactroy;
import com.xinwis.okhttputils.interceptor.BaseInterceptor;
import com.xinwis.okhttputils.interceptor.CaheInterceptor;
import com.xinwis.okhttputils.request.RequestCall;
import com.xinwis.okhttputils.utils.AndroidPlatform;
import com.xinwis.okhttputils.utils.Logger;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;


/**
 * Created by zhy on 15/8/17.
 */
public class OkHttpUtils {
    public static final long DEFAULT_MILLISECONDS = 10_000L;
    private volatile static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private  AndroidPlatform mAndroidPlatform;

    public static String zs_1 = "-----BEGIN CERTIFICATE-----\n" +
            "MIIFwjCCBKqgAwIBAgIQUUnge3SbeE7xPNtpGmHfGTANBgkqhkiG9w0BAQsFADBP\n" +
            "MQswCQYDVQQGEwJDTjEaMBgGA1UEChMRV29TaWduIENBIExpbWl0ZWQxJDAiBgNV\n" +
            "BAMMG0NBIOayg+mAmuWFjei0uVNTTOivgeS5piBHMjAeFw0xNjA3MjkxNTEzNDha\n" +
            "Fw0xODA3MjkxNTEzNDhaMC0xCzAJBgNVBAYTAkNOMR4wHAYDVQQDDBV0cmFpbi5o\n" +
            "ZW5ndGVjaC5jb20uY24wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCz\n" +
            "6DkUMSCKPN1KNl9MXtLdb/C/YkuFcDreq+iz2XaeFO8MYoNat6k2biY7ojYXTVI5\n" +
            "Ea9vbDSLi1mFgw+wUYzEig8BfMC0ieEshUgPFJmh2JA/OG9F8RA7Zegtqz24QqW2\n" +
            "8gPQloiXCWsIAtzjSLNy3jr7ylhS7G2h39qjDgAD6X7noNwr+/t+u2YOQPrqkZNe\n" +
            "qaVbdN8Xvmjm380Yw810zPnxs0hD8riGJ3d1UrJPrYossiq7zfX5cX0v66UP2WFx\n" +
            "IWplEB/ZawuA3GECgRQuqIlDZiNqFW+R5ObPXomHd6I4gOlOePtLJFQ2PDU/pca6\n" +
            "L8RoEdal1k3LtrPrcOGnAgMBAAGjggK6MIICtjAOBgNVHQ8BAf8EBAMCBaAwHQYD\n" +
            "VR0lBBYwFAYIKwYBBQUHAwIGCCsGAQUFBwMBMAkGA1UdEwQCMAAwHQYDVR0OBBYE\n" +
            "FIlS6xV5Ks3SRsI6naPTvq3EblpCMB8GA1UdIwQYMBaAFDDadIbzKJBWntcxMcK9\n" +
            "Wc2TEjkdMH8GCCsGAQUFBwEBBHMwcTA1BggrBgEFBQcwAYYpaHR0cDovL29jc3Ay\n" +
            "Lndvc2lnbi5jbi9jYTJnMi9zZXJ2ZXIxL2ZyZWUwOAYIKwYBBQUHMAKGLGh0dHA6\n" +
            "Ly9haWEyLndvc2lnbi5jbi9jYTJnMi5zZXJ2ZXIxLmZyZWUuY2VyMD4GA1UdHwQ3\n" +
            "MDUwM6AxoC+GLWh0dHA6Ly9jcmxzMi53b3NpZ24uY24vY2EyZzItc2VydmVyMS1m\n" +
            "cmVlLmNybDAgBgNVHREEGTAXghV0cmFpbi5oZW5ndGVjaC5jb20uY24wTwYDVR0g\n" +
            "BEgwRjAIBgZngQwBAgEwOgYLKwYBBAGCm1EBAQIwKzApBggrBgEFBQcCARYdaHR0\n" +
            "cDovL3d3dy53b3NpZ24uY29tL3BvbGljeS8wggEEBgorBgEEAdZ5AgQCBIH1BIHy\n" +
            "APAAdwBo9pj4H2SCvjqM7rkoHUz8cVFdZ5PURNEKZ6y7T0/7xAAAAVY3WG9uAAAE\n" +
            "AwBIMEYCIQCkJelBPm16S5nerxXHs7nuVQfr7873wGW/GqSRmmIh5wIhAKQOmKoP\n" +
            "3H0hBKHLs+lilWUsA1CmkwoLPDnu4m6e0Kf/AHUA7ku9t3XOYLrhQmkfq+GeZqMP\n" +
            "fl+wctiDAMR7iXqo/csAAAFWN1hxiwAABAMARjBEAiBmqj8TKbvBFAuhEexzltmc\n" +
            "6RwJKfZwDLwGzLxRcWcmhAIgYvcc35tO1nfFBtt+Qu00WqYy3ScYOYmNsPb00bqj\n" +
            "pB4wDQYJKoZIhvcNAQELBQADggEBADjGKnfxQBQIdrISeOiUujCaLKobqtTU2HYI\n" +
            "Rg679wbObQ/K+MfZwg79oyJ8rx9HtxFeAEma4jLcOf4tT3nevn7p8axVaXawgkyA\n" +
            "eO6clM5xPJ7wtUwzrDF77mYU/opM2vCNvk9opVxPFy8wdPKmxsbM1YN5hj+OF3Bu\n" +
            "SHSyOOsq1zRjiJh+udggtkQl6RVcMfw5arvzxOPMYI2DZyra8k/+kQ989pJ09p+b\n" +
            "XAR/GLrsFNSRgxkPj16eFl0FBK2JZDJPXXptut/AkqylE3pN6bjJVlDRcP7bSafK\n" +
            "0pjJf+bnwEN2u35jeznPwKVnU1St3FbYq+D61QDFV/osf1xUlBs=\n" +
            "-----END CERTIFICATE-----";

    public static String zs_2 = "-----BEGIN CERTIFICATE-----\n" +
            "MIIFlDCCA3ygAwIBAgIQAViMOjUHs/iXIxx2t++F3TANBgkqhkiG9w0BAQsFADBG\n" +
            "MQswCQYDVQQGEwJDTjEaMBgGA1UEChMRV29TaWduIENBIExpbWl0ZWQxGzAZBgNV\n" +
            "BAMMEkNBIOayg+mAmuagueivgeS5pjAeFw0xNDExMDgwMDU4NThaFw0yOTExMDgw\n" +
            "MDU4NThaME8xCzAJBgNVBAYTAkNOMRowGAYDVQQKExFXb1NpZ24gQ0EgTGltaXRl\n" +
            "ZDEkMCIGA1UEAwwbQ0Eg5rKD6YCa5YWN6LS5U1NM6K+B5LmmIEcyMIIBIjANBgkq\n" +
            "hkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0JMBh1z5ihKZLtSptut+daPq6CIovna/\n" +
            "2uswQNsecCE4v+pEZ5djMblHQwzu1ciAav06jGTK7c6lRB4Sbb82hoXUUnZ5jBao\n" +
            "RDNPM3NagriXz06P3Mm2FJXtzWKT73Nw4gu+TnbdtLildjf7EOCpZFUC76e8svcO\n" +
            "tfJpRR0dDSui5xNH7E1zEnJBCL3hvyVnhWu6KWfKyiAHL25IEK1bEQmHAZCePcJp\n" +
            "eiSDPE9URMF8iiurgXivk2Hi9NKKHOByKr0AlNUxWM0qjTv5FivrmrqvFnF5+oPV\n" +
            "cZUWyDbEocauums8RjgYaqaD2qv7mCU3uaOzCWIijCPadMXtHXUqOwIDAQABo4IB\n" +
            "czCCAW8wDgYDVR0PAQH/BAQDAgEGMB0GA1UdJQQWMBQGCCsGAQUFBwMCBggrBgEF\n" +
            "BQcDATASBgNVHRMBAf8ECDAGAQH/AgEAMC8GA1UdHwQoMCYwJKAioCCGHmh0dHA6\n" +
            "Ly9jcmxzMi53b3NpZ24uY24vY2EyLmNybDBwBggrBgEFBQcBAQRkMGIwJgYIKwYB\n" +
            "BQUHMAGGGmh0dHA6Ly9vY3NwMi53b3NpZ24uY24vY2EyMDgGCCsGAQUFBzAChixo\n" +
            "dHRwOi8vYWlhMi53b3NpZ24uY24vY2EyZzItc2VydmVyMS1mcmVlLmNlcjAdBgNV\n" +
            "HQ4EFgQUMNp0hvMokFae1zExwr1ZzZMSOR0wHwYDVR0jBBgwFoAU4E2/3JtBXRPo\n" +
            "ZPCn6RWk4YHBujEwRwYDVR0gBEAwPjA8Bg0rBgEEAYKbUQYBAgICMCswKQYIKwYB\n" +
            "BQUHAgEWHWh0dHA6Ly93d3cud29zaWduLmNvbS9wb2xpY3kvMA0GCSqGSIb3DQEB\n" +
            "CwUAA4ICAQBbCJgtcYOHa2WELfGAcTXZawWomqwm56yyVnowYt+4F5chlEdx8SRu\n" +
            "aYPzurYbMJuY0u1fs7NqmFw/A/oXbNy0j37h2fOWVVVayGQIPtw8i3yAaIGTyEp5\n" +
            "lHQhjKebBq6SyRzSyrzQzWNst8pZlLH51m3KR1vs2zXPWcXmu3zyAc7p+Bghv0N/\n" +
            "/wo98IKJSXHscLMh5bXBVQLyd/et/YnN2MzHXJHN5ZrSby8P4WAWtr2AC0bl3922\n" +
            "KUnzHCBKIM4VAxNXOhuXt5pn1yoyKbFh/xGbpnMtFBud5KEPCPR7umQRiESRYsLy\n" +
            "aMx0INAHknGLevWUKdAF9PTX5Yt+LIwNiYmZsIJYCA2vEmhXWoLaz6xKySt6jDhG\n" +
            "2ITIuuz6dY5sJMV5eDFnrWMAR3qTHBy5TGF4nWtsHuADhx0vkTdlodVxZaDI5VuN\n" +
            "DjCfhAvMpDvMQc5I4CuSLTtyzUMMsIKSxlG58FQ/S0nVfqQlpJDzt+7pSW1fb0wF\n" +
            "tyQjVsUymi/o0Q8CNJxvKGDeQiAvYwsjd1kWxKpiV+uXcqLlPUTdnv5jP7oNE2uL\n" +
            "rCk64HjrrqEDIShimAuTKxXpNUbknxGehrtEIQcXfsl6MDJhmPuwx/9tzdu9uOYy\n" +
            "E9MX9CFzVIWohrOAOVFtWcYTzi/LhyTDIZebcHNeaxdjedEoLB44oQ==\n" +
            "-----END CERTIFICATE-----";

    public static String zs_3 = "-----BEGIN CERTIFICATE-----\n" +
            "MIIGTTCCBDWgAwIBAgIHH86n9ql/6TANBgkqhkiG9w0BAQsFADB9MQswCQYDVQQG\n" +
            "EwJJTDEWMBQGA1UEChMNU3RhcnRDb20gTHRkLjErMCkGA1UECxMiU2VjdXJlIERp\n" +
            "Z2l0YWwgQ2VydGlmaWNhdGUgU2lnbmluZzEpMCcGA1UEAxMgU3RhcnRDb20gQ2Vy\n" +
            "dGlmaWNhdGlvbiBBdXRob3JpdHkwHhcNMDYwOTE3MjI0NjM2WhcNMTkxMjMxMjM1\n" +
            "OTU5WjBGMQswCQYDVQQGEwJDTjEaMBgGA1UEChMRV29TaWduIENBIExpbWl0ZWQx\n" +
            "GzAZBgNVBAMMEkNBIOayg+mAmuagueivgeS5pjCCAiIwDQYJKoZIhvcNAQEBBQAD\n" +
            "ggIPADCCAgoCggIBANBJIR4l/IfBKsKs23aGBk7n0HQ03O1lNfxQ1og/pPB/6w9f\n" +
            "eS+Jsf28Y1g3k5s4+LdbqfrYcce0vICXjWxL8VDVKimqqBl6luaVjnTtlwpXdfQF\n" +
            "220LObkBf6r21tps5gXgpE1S/NvQdLcRjHuNT/+Hg67/BQMTV1A3/oyWUhBMX7+U\n" +
            "cWnZlj4MQ0++MMCfOXRPBkVdo9ZWOWgHzIdPUHeTcdlECLGKNOmJrNubTuHZ5FJF\n" +
            "jC4UH5FrGR1oKSxWxOIeE1dk8GHjuRHfsOFXoBut11/Rr9srLT/QaI4P6p8PizVY\n" +
            "GxMc9N41oQpd1urfEm/A+2kHRnLcgfYEIxfgTXXhcm+wKOub4eGDoZ9KXa/Mm/oC\n" +
            "ILYYYneRO6PVZa3cfJB3HERBpEqL65Vy6fYJZNyoLZ90eOjBogljnO+g20+dlasg\n" +
            "T7ew94dcpqDkNzjHXOM1Dyyto4Ci7C5dwM/tiwXC5nNu9onV9dJGjuptYxseisl9\n" +
            "pvic6+XVY4VNc2ZpEf7IDvTBx2ZJU37kGWvx6XpZo21+xRfmJ8bvG9tv/A1NBgG0\n" +
            "DlwwRlVgrzhlOspHuqwszEYfskaWP/PtJgXud6Fqa34tbVhcStSOZ7jx2tVGiif5\n" +
            "EfLJQv5O3t8fXMSkhocWM6GnFxilDeQF5SvCKwuilZC5/WA8Tok+55zuH7sBAgMB\n" +
            "AAGjggEHMIIBAzASBgNVHRMBAf8ECDAGAQH/AgECMA4GA1UdDwEB/wQEAwIBBjAd\n" +
            "BgNVHQ4EFgQU4E2/3JtBXRPoZPCn6RWk4YHBujEwHwYDVR0jBBgwFoAUTgvvGqRA\n" +
            "W6UXaYcwyjRoQ9BBrvIwaQYIKwYBBQUHAQEEXTBbMCcGCCsGAQUFBzABhhtodHRw\n" +
            "Oi8vb2NzcC5zdGFydHNzbC5jb20vY2EwMAYIKwYBBQUHMAKGJGh0dHA6Ly9haWEu\n" +
            "c3RhcnRzc2wuY29tL2NlcnRzL2NhLmNydDAyBgNVHR8EKzApMCegJaAjhiFodHRw\n" +
            "Oi8vY3JsLnN0YXJ0c3NsLmNvbS9zZnNjYS5jcmwwDQYJKoZIhvcNAQELBQADggIB\n" +
            "AI274T7wqbpK6IUpiNBGwjQCnLQYCkkOse9rVge7hwFTdK2gpoA1bNcBmrmEubOP\n" +
            "jRBVHNhW+bXyJIc+UBs8RdjcgJMmGHSVhMPs2zvncMwx5b2FHvr4ajWwXcL3INlF\n" +
            "vwJYifZgzi9FSe+Xg9KLqfQMSZNCpIop57BJ8F/hhnWcmjNvCtPod9jaV91SMy/R\n" +
            "xN6FfVNV8V/TJNYlJYNjLF9xSHxgpLYu5WHPFzHzyFdPlDdtWZbgoTyXJ0zKwsMV\n" +
            "w1zS6WDAGRWHgWlRRXPbmBSBPhXk5JfSBLX8M/uHJGl1t6frghUdSqw7EiOJn7T8\n" +
            "JE7dIsi3zx462waTg7jKDLpzC34/PEvPYcF7X5pybb/TQVjjGzWZg61sQERjfN7B\n" +
            "ePHm6SGg74pOkX4XVVuX8SnaH/Y+dTmaZfGJKDs50PU7PpF5qA2x1FaeIFuzklqt\n" +
            "H4DHm6uaOsO5HOIWstKhtlH9xnb2l9vxF581/Tlxea6OUtHfk4J7GvGcowyADI8g\n" +
            "7OOYkS8e9GQ2DBVqacq0VrnIFA0uAXGTL27Oh6VYFmZ61MjVEhXF5y4yHQ8KFpQK\n" +
            "vFCV0S6lX0VqalP0cPBCUftf1L8qBqy5FMQDhlET6Lwh0FT1iTvz03xlQMRZBzyS\n" +
            "EkE9+JcNYJ8zFJKmjFvuStRRmMJTdkorYuwFw2tQZLFs\n" +
            "-----END CERTIFICATE-----";



    private static int[] certificates = {R.raw.uat,R.raw.pro_wechat,R.raw.train,R.raw.pro_kai,R.raw.pms};

    public OkHttpUtils(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        } else {
            mOkHttpClient = okHttpClient;
        }
        mAndroidPlatform= AndroidPlatform.get();
    }


    public static OkHttpUtils initClient(Context context) {

        if (mInstance == null) {

            Cache cache = null;
            File httpCacheDirectory = null;
            try {
                if (httpCacheDirectory == null && context != null) {
                    String cacheDir = context.getExternalCacheDir().toString() + "/baolimananger_cache";
                    //判断目录是否存在，不存在则创建该目录
                    File dirFile = new File(cacheDir);
                    if (!dirFile.exists()) {
                        dirFile.mkdirs();
                    }
                    //创建缓存文件
                    httpCacheDirectory = new File(cacheDir, "baolimananger_cache");
                }
                if (cache == null) {
                    cache = new Cache(httpCacheDirectory, 100 * 1024 * 1024);
                }
            } catch (Exception e) {
                Logger.i("CustomApplication", e.getMessage());
            }



            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            //注册证书
            SSLSocketFactory sslSocketFactory = HttpsFactroy.getSSLSocketFactory(context,
                    new Buffer().writeUtf8(zs_1).inputStream(),
                    new Buffer().writeUtf8(zs_2).inputStream(),
                    new Buffer().writeUtf8(zs_3).inputStream());
            builder.sslSocketFactory(sslSocketFactory, Platform.get().trustManager(sslSocketFactory));

            //初始化okhttp其他配置
            builder. addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .cookieJar(new NovateCookieManger(context))
                    .cache(cache)
                    .addInterceptor(new CaheInterceptor(context))
                    .addInterceptor(new BaseInterceptor())
                    .addNetworkInterceptor(new CaheInterceptor(context))
                    .hostnameVerifier(new AllowMyHostnameVerifier())
                    .sslSocketFactory(HttpsFactroy.getSSLSocketFactory(context, certificates), Platform.get().trustManager(HttpsFactroy.getSSLSocketFactory(context, certificates)))
                    .connectionPool(new ConnectionPool(8, 10, TimeUnit.SECONDS))
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS);
            OkHttpClient okHttpClient = builder.build();
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtils(okHttpClient);
                }
            }
        }
        return mInstance;
    }


    public static OkHttpUtils getInstance() {
        return initClient(null);
    }


    public Executor getDelivery() {
        return mAndroidPlatform.defaultCallbackExecutor();
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public static GetBuilder get() {
        return new GetBuilder();
    }

    public static PostStringBuilder postString() {
        return new PostStringBuilder();
    }

    public static PostFileBuilder postFile() {
        return new PostFileBuilder();
    }

    public static PostFormBuilder post() {
        return new PostFormBuilder();
    }

    public static OtherRequestBuilder put() {
        return new OtherRequestBuilder(METHOD.PUT);
    }

    public static HeadBuilder head() {
        return new HeadBuilder();
    }

    public static OtherRequestBuilder delete() {
        return new OtherRequestBuilder(METHOD.DELETE);
    }

    public static OtherRequestBuilder patch() {
        return new OtherRequestBuilder(METHOD.PATCH);
    }

    public void execute(final RequestCall requestCall, Callback callback) {
        if (callback == null)
            callback = Callback.CALLBACK_DEFAULT;
        final Callback finalCallback = callback;
        final int id = requestCall.getOkHttpRequest().getId();

        requestCall.getCall().enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                sendFailResultCallback(call, e, finalCallback, id);
            }

            @Override
            public void onResponse(final Call call, final Response response) {
                try {
                    if (call.isCanceled()) {
                        sendFailResultCallback(call, new IOException("Canceled!"), finalCallback, id);
                        return;
                    }

                    if (!finalCallback.validateReponse(response, id)) {
                        sendFailResultCallback(call, new IOException("request failed , reponse's code is : " + response.code()), finalCallback, id);
                        return;
                    }

                    Object o = finalCallback.parseNetworkResponse(response, id);
                    sendSuccessResultCallback(o, finalCallback, id);
                } catch (Exception e) {
                    sendFailResultCallback(call, e, finalCallback, id);
                } finally {
                    if (response.body() != null)
                        response.body().close();
                }
            }
        });
    }


    public void sendFailResultCallback(final Call call, final Exception e, final Callback callback, final int id) {
        if (callback == null) return;
        mAndroidPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onError(call, e, id);
                callback.onAfter(id);
            }
        });
    }

    public void sendSuccessResultCallback(final Object object, final Callback callback, final int id) {
        if (callback == null) return;
        mAndroidPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(object, id);
                callback.onAfter(id);
            }
        });
    }

    public void cancelTag(Object tag) {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    public static class METHOD {
        public static final String HEAD = "HEAD";
        public static final String DELETE = "DELETE";
        public static final String PUT = "PUT";
        public static final String PATCH = "PATCH";
    }


}

