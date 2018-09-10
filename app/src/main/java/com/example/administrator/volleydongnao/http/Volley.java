package com.example.administrator.volleydongnao.http;

import com.example.administrator.volleydongnao.http.interfaces.IRequestCallBack;
import com.example.administrator.volleydongnao.http.interfaces.IHttpListener;
import com.example.administrator.volleydongnao.http.interfaces.IHttpService;

import java.util.concurrent.FutureTask;

/**
 * Created by Administrator on 2017/1/13 0013.
 */

public class Volley {

    /**
     *
     * @param <T>  请求参数类型
     * @param <M>  响应参数类型
     */
    public static <T,M> void sendRequest(T  requestData, String url,
                                         Class<M> responseClass, IRequestCallBack requestCallBack)
    {
        RequestInfo<T> requestInfo =new RequestInfo<>();
        requestInfo.setUrl(url);
        IHttpService httpService=new JsonHttpService();
        requestInfo.setHttpService(httpService);
        IHttpListener httpListener=new JsonHttpListener<>(responseClass,requestCallBack);
        requestInfo.setHttpListener(httpListener);
        HttpTaskRunnable<T> httpTaskRunnableRunnable =new HttpTaskRunnable<>(requestInfo);
        try {
            ThreadPoolManager.getInstance().execute(new FutureTask<>(httpTaskRunnableRunnable, null));
        } catch (InterruptedException e) {
            requestCallBack.onFail();
        }
    }
}
