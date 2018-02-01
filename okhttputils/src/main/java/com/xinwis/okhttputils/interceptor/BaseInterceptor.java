package com.xinwis.okhttputils.interceptor;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class BaseInterceptor implements Interceptor{

    public BaseInterceptor() {

    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();
		builder.addHeader("Content-Type", "application/json; charset=UTF-8").build();
        builder.addHeader("Accept-Encoding", "utf-8").build();
        builder.addHeader("Connection", "keep-alive").build();
        builder.addHeader("Accept", "*/*").build();
        builder.addHeader("Cookie", "add cookies here").build();

        return chain.proceed(builder.build());
    }

}