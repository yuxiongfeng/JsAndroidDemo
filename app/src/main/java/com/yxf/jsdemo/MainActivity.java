package com.yxf.jsdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.just.agentweb.AgentWeb;
import com.orhanobut.logger.Logger;
import com.proton.ecg.algorithm.callback.EcgPatchAlgorithmListener;
import com.proton.ecg.algorithm.interfaces.IEcgAlgorithm;
import com.proton.ecg.algorithm.interfaces.impl.EcgPatchAlgorithm;
import com.proton.ecgcard.connector.EcgCardManager;
import com.proton.ecgcard.connector.callback.DataListener;
import com.proton.ecgcard.connector.utils.BleUtils;
import com.wms.ble.callback.OnConnectListener;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    private AgentWeb mAgentWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.d("log start.");
        //需要获取定位权限，否则搜索不到设备
        PermissionUtils.getLocationPermission(this);

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((LinearLayout) findViewById(R.id.view), new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go("http://192.168.2.16:3000/#/");

        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new AndroidJSInterface());

        findViewById(R.id.connectCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectCard();
            }
        });

        findViewById(R.id.btnCallJs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callJs();
            }
        });

        findViewById(R.id.disconnect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EcgCardManager.getInstance("0C:61:CF:C1:2D:52").disConnect();
            }
        });
    }

    private void connectCard() {
        IEcgAlgorithm ecgAlgorithm = new EcgPatchAlgorithm(new EcgPatchAlgorithmListener() {
            @Override
            public void receiveEcgFilterData(byte[] bytes) {
                super.receiveEcgFilterData(bytes);

               // DecryptHelper.decryptFilterData(data).getFilterDatas()
                mAgentWeb.getJsAccessEntrace().quickCallJs("callByAndroid", BleUtils.bytesToHexString(bytes));
                //Logger.d("android ecgData:%s", BleUtils.bytesToHexString(bytes));
            }
        });

        DataListener dataListener = new DataListener() {
            @Override
            public void receiveEcgRawData(byte[] bytes) {
                super.receiveEcgRawData(bytes);
//                Logger.d("android ecgData:%s", BleUtils.bytesToHexString(bytes));
                ecgAlgorithm.processEcgData(bytes);
                //mAgentWeb.getJsAccessEntrace().quickCallJs("callByAndroid", BleUtils.bytesToHexString(bytes));
            }
        };

        OnConnectListener connectListener = new OnConnectListener() {
            @Override
            public void onConnectSuccess() {
                super.onConnectSuccess();
                Logger.d("connect success");
                Toast.makeText(MainActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onConnectFaild() {
                super.onConnectFaild();
                Logger.d("connect fail");
            }

            @Override
            public void onDisconnect(boolean b) {
                super.onDisconnect(b);
                Logger.d("disconnect");

            }
        };

        EcgCardManager.getInstance("0C:61:CF:C1:2D:52")
                .setDataListener(dataListener)
                .connectEcgCard(connectListener);
    }

    private void callJs() {
        String params = "1,2,3,4,5,6,7";
        mAgentWeb.getJsAccessEntrace().quickCallJs("callByAndroid", params);
    }

    class AndroidJSInterface {
        /**
         * 跳转到配网
         */
        @JavascriptInterface
        public String goToDockerSetNet(int index) {
//            Logger.d(String.valueOf(index));
            Logger.d("跳转到setting page index:%s", String.valueOf(index));
            return "it is callback from android";
        }

        @JavascriptInterface
        public void addLog(String log) {
            Logger.d(log);
        }

        @JavascriptInterface
        public void getCurrentParam() {

        }

        @JavascriptInterface
        public void getDeviceInfo() {

        }

        @JavascriptInterface
        public void onMessage() {

        }

    }
}