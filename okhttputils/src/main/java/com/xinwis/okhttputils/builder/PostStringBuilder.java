package com.xinwis.okhttputils.builder;



import com.xinwis.okhttputils.request.PostStringRequest;
import com.xinwis.okhttputils.request.RequestCall;

import okhttp3.MediaType;

/**
 * Created by zhy on 15/12/14.
 */
public class PostStringBuilder extends OkHttpRequestBuilder<PostStringBuilder> {
    private String content;
    private MediaType mediaType;
    protected long  readTimeOut=0;
    protected long writeTimeOut=0;
    protected long connTimeOut=0;

    public PostStringBuilder content(String content) {
        this.content = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build() {
        if (readTimeOut==0&&writeTimeOut==0&&connTimeOut==0){
            return new PostStringRequest(url, tag, params, headers, content, mediaType,id).build();
        }else {
            return new PostStringRequest(url, tag, params, headers, content, mediaType,id,readTimeOut,writeTimeOut,connTimeOut).build();

        }

    }

    public void setReadTimeOut(long readTimeOut) {
        this.readTimeOut=readTimeOut;
    }


    public void setWriteTimeOut(long writeTimeOut) {
        this.writeTimeOut =writeTimeOut;

    }


    public void setConnTimeOut(long connTimeOut) {
        this.connTimeOut =connTimeOut;
    }

}
