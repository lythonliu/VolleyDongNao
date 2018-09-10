package com.example.administrator.volleydongnao.http;

import com.alibaba.fastjson.JSON;
import com.example.administrator.volleydongnao.http.interfaces.IHttpService;

/**
 * Created by Administrator on 2017/1/13 0013.
 */

public class HttpTaskRunnable<T> implements Runnable {
    private IHttpService httpService;
    public HttpTaskRunnable(RequestInfo<T> requestInfo)
    {
        httpService= requestInfo.getHttpService();
        httpService.setHttpListener(requestInfo.getHttpListener());
        httpService.setUrl(requestInfo.getUrl());
        T requestData = requestInfo.getRequestData();
        String requestString= JSON.toJSONString(requestData);

        try {
            httpService.setByteArray(requestString.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        httpService.execute();
    }
}
