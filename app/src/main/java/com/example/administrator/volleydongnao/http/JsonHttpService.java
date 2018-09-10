package com.example.administrator.volleydongnao.http;

import com.example.administrator.volleydongnao.http.interfaces.IHttpListener;
import com.example.administrator.volleydongnao.http.interfaces.IHttpService;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by Administrator on 2017/1/13 0013.
 */

public class JsonHttpService implements IHttpService {
    private IHttpListener httpListener;

    private HttpClient httpClient=new DefaultHttpClient();
    private HttpPost httpUriRequest;
    private String url;

    private byte[] byteArray;
    private HttpResponseHandler responseHandler =new HttpResponseHandler();
    @Override
    public void setUrl(String url) {
        this.url=url;
    }

    @Override
    public void execute() {
        httpUriRequest =new HttpPost(url);
        HttpEntity httpEntry=new ByteArrayEntity(byteArray);
        httpUriRequest.setEntity(httpEntry);
        try {
            httpClient.execute(httpUriRequest, responseHandler);
        } catch (IOException e) {
            httpListener.onFail();
        }
    }

    @Override
    public void setHttpListener(IHttpListener httpListener) {
        this.httpListener=httpListener;
    }

    @Override
    public void setByteArray(byte[] byteArray) {
         this.byteArray = byteArray;
    }
    private class HttpResponseHandler extends BasicResponseHandler
    {
        @Override
        public String handleResponse(HttpResponse response) throws ClientProtocolException {
            int statusCode=response.getStatusLine().getStatusCode();
            if(statusCode==200)
            {
                httpListener.onSuccess(response.getEntity());
            }else{
                httpListener.onFail();
            }
            return null;
        }
    }
}
