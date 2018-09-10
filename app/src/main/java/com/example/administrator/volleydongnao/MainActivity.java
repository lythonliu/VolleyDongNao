package com.example.administrator.volleydongnao;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.volleydongnao.http.Volley;
import com.example.administrator.volleydongnao.http.interfaces.IRequestCallBack;

public class MainActivity extends com.lythonliu.LinkAppCompatActivity {

    @Override
    public String getAppName(){
        return BuildConfig.APP_NAME;
    }

    public  static  final String URL_LoginServlet ="http://192.168.100.24:8080/UserRecord/LoginServlet";
    private static final String TAG = "MainActivity";
    TextView content_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        content_tv = (TextView) findViewById(R.id.content_tv);

    }

    public  void login(View view)
    {
        User user=new User();
        user.setName("13343491234");
        user.setPassword("123456");
        for (int i=0;i<50;i++)
        {
            Volley.sendRequest(user, URL_LoginServlet, LoginBean.class, new IRequestCallBack<LoginBean>() {
                @Override
                public void onSuccess(LoginBean result) {
                    Log.i(TAG, result.toString());
                }

                @Override
                public void onFail() {
                    Log.i(TAG,"获取失败");
                }
            });
        }



    }
}
