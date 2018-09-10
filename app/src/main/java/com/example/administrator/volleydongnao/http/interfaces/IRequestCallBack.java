package com.example.administrator.volleydongnao.http.interfaces;

/**
 * Created by Administrator on 2017/1/13 0013.
 */

public interface IRequestCallBack<M> {
     void onSuccess(M result);


      void onFail();
}
