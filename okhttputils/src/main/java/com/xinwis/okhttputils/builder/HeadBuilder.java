package com.xinwis.okhttputils.builder;


import com.xinwis.okhttputils.OkHttpUtils;
import com.xinwis.okhttputils.request.OtherRequest;
import com.xinwis.okhttputils.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder {
    @Override
    public RequestCall build() {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
