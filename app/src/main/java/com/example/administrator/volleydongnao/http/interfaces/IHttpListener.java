package com.example.administrator.volleydongnao.http.interfaces;

import org.apache.http.HttpEntity;

/**
 * Created by Administrator on 2017/1/13 0013.
 */

public interface IHttpListener {
    void onSuccess(HttpEntity httpEntity);
    void onFail();
}
