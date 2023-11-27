package com.example.network.interceptor;

import com.example.network.base.INetworkRequiredInfo;
import com.example.network.utils.TencentUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CommonRequestInterceptor implements Interceptor {
    private INetworkRequiredInfo requiredInfo;

    public CommonRequestInterceptor(INetworkRequiredInfo requiredInfo) {
        this.requiredInfo = requiredInfo;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String timeStr = TencentUtil.getTimeStr();
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("os", "android");
        builder.addHeader("appVersion", this.requiredInfo.getAppVersionCode());
        builder.addHeader("Source", "android");
        builder.addHeader("Authorization", TencentUtil.getAuthorization(timeStr));
        builder.addHeader("Date", timeStr);

        return chain.proceed(builder.build());
    }
}
