package com.yxf.jsdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.orhanobut.logger.Logger;

public class MainActivity extends AppCompatActivity {

    private AgentWeb mAgentWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((LinearLayout) findViewById(R.id.view), new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go("http://192.168.2.16:3000/#/");

        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new AndroidJSInterface());


        findViewById(R.id.btnCallJs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callJs();
            }
        });
    }

    private void callJs(){
        String params="1,2,3,4,5,6,7";
        mAgentWeb.getJsAccessEntrace().quickCallJs("callByAndroid",params);
    }

    class AndroidJSInterface{
        /**
         * 跳转到配网
         */
        @JavascriptInterface
        public String goToDockerSetNet(int index) {
//            Logger.d(String.valueOf(index));
            Logger.d("跳转到setting page index:%s",String.valueOf(index));
            return "it is callback from android";
        }

        @JavascriptInterface
        public void addLog(String log){
            Logger.d("from js log %s",log);
        }

        @JavascriptInterface
        public void getCurrentParam(){

        }

        @JavascriptInterface
        public void getDeviceInfo(){

        }

        @JavascriptInterface
        public void onMessage(){

        }

    }
}