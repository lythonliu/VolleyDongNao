package com.example.administrator.volleydongnao.http;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;
import com.example.administrator.volleydongnao.http.interfaces.IRequestCallBack;
import com.example.administrator.volleydongnao.http.interfaces.IHttpListener;

import org.apache.http.HttpEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2017/1/13 0013.
 * M  对应响应类
 */

public class JsonHttpListener<M> implements IHttpListener {
    private Class<M> responseClass;
    /**
     * 回调调用层 的接口
     */
    private IRequestCallBack<M> requestCallBack;

    Handler mainHandler =new Handler(Looper.getMainLooper());
    public JsonHttpListener(Class<M> responseClass, IRequestCallBack<M> requestCallBack) {
        this.responseClass = responseClass;
        this.requestCallBack = requestCallBack;
    }

    @Override
    public void onSuccess(HttpEntity httpEntity) {
        InputStream inputStream=null;
        try {
            inputStream=httpEntity.getContent();
            /*
            得到网络返回的数据
            子线程
             */
            String content=this.getContent(inputStream);
            final M result= JSON.parseObject(content,responseClass);

            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    requestCallBack.onSuccess(result);
                }
            });

        } catch (IOException e) {
            requestCallBack.onFail();
        }

    }

    @Override
    public void onFail() {
        requestCallBack.onFail();
    }
    private String getContent(InputStream inputStream) {
        String content=null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                requestCallBack.onFail();
                System.out.println("Error=" + e.toString());
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    System.out.println("Error=" + e.toString());
                }
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            requestCallBack.onFail();
        }
        return content;
    }
}
