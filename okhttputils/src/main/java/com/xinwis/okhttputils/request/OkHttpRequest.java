package com.xinwis.okhttputils.request;


import com.xinwis.okhttputils.callback.Callback;
import com.xinwis.okhttputils.utils.Exceptions;

import java.util.Map;

import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by zhy on 15/11/6.
 */
public abstract class OkHttpRequest {
    protected String url;
    protected Object tag;
    protected Map<String, String> params;
    protected Map<String, String> headers;
    protected int id;
    private long readTimeOut;
    private long writeTimeOut;
    private long connTimeOut;

    protected Request.Builder builder = new Request.Builder();
    private RequestCall requestCall;

    protected OkHttpRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, int id) {
        this.url = url;
        this.tag = tag;
        this.params = params;
        this.headers = headers;
        this.id = id;
        if (url == null) {
            Exceptions.illegalArgument("url can not be null.");
        }
        initBuilder();
    }


    protected OkHttpRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, int id, long readTimeOut, long writeTimeOut, long connTimeOut) {
        this.url = url;
        this.tag = tag;
        this.params = params;
        this.headers = headers;
        this.readTimeOut = readTimeOut;
        this.writeTimeOut = writeTimeOut;
        this.connTimeOut = connTimeOut;
        this.id = id;
        if (url == null) {
            Exceptions.illegalArgument("url can not be null.");
        }
        initBuilder();
    }


    /**
     * 初始化一些基本参数 url , tag , headers
     */
    private void initBuilder() {
        builder.url(url).tag(tag);
        appendHeaders();
    }

    protected abstract RequestBody buildRequestBody();

    protected RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback) {
        return requestBody;
    }

    protected abstract Request buildRequest(RequestBody requestBody);

    public RequestCall build() {
        requestCall = new RequestCall(this);
        if (readTimeOut == 0) {
            requestCall.readTimeOut(readTimeOut);
        }
        if (writeTimeOut == 0) {
            requestCall.writeTimeOut(writeTimeOut);
        }
        if (connTimeOut == 0) {
            requestCall.connTimeOut(connTimeOut);
        }


        return requestCall;
    }

    public Request generateRequest(Callback callback) {
        RequestBody requestBody = buildRequestBody();
        RequestBody wrappedRequestBody = wrapRequestBody(requestBody, callback);
        Request request = buildRequest(wrappedRequestBody);
        return request;
    }


    protected void appendHeaders() {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty()) return;
        for (String key : headers.keySet()) {
            headerBuilder.add(key, headers.get(key));
        }
        builder.headers(headerBuilder.build());
    }

    public int getId() {
        return id;
    }

}
