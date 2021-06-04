package com.yxf.jsdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.just.agentweb.AgentWeb;
import com.orhanobut.logger.Logger;
//import com.proton.decrypt.DecryptHelper;
//import com.proton.ecg.algorithm.callback.EcgAlgorithmListener;
//import com.proton.ecg.algorithm.interfaces.IEcgAlgorithm;
//import com.proton.ecg.algorithm.interfaces.impl.EcgCardAlgorithm;
//import com.proton.ecg.algorithm.interfaces.impl.EcgPatchAlgorithm;
//import com.proton.ecgcard.connector.EcgCardManager;
//import com.proton.ecgcard.connector.callback.DataListener;
//import com.proton.ecgcard.connector.utils.BleUtils;
//import com.proton.view.EcgRealTimeView;
//import com.wms.ble.callback.OnConnectListener;
import com.yxf.jsdemo.buildmode.Computer;
import com.yxf.jsdemo.util.FileUtil;
import com.yxf.jsdemo.widget.NewSy;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AgentWeb mAgentWeb;
//    EcgRealTimeView ecgRealTimeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        float density = displayMetrics.density;//用于px和sp之间的转换
        int densityDpi = displayMetrics.densityDpi;
        float scaledDensity = displayMetrics.scaledDensity;//用于px和sp之间的转换
        int widthPixels = displayMetrics.widthPixels;
        Logger.w("density:%f,densityDpi:%d,scaleDensity:%f,widthPixels:%d", density, densityDpi, scaledDensity, widthPixels);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Handler handler = new Handler(Looper.myLooper(), new Handler.Callback() {
                    @Override
                    public boolean handleMessage(@NonNull Message msg) {
                        return false;
                    }
                });
            }
        }).start();

        //需要获取定位权限，否则搜索不到设备
        PermissionUtils.getLocationPermission(this);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((LinearLayout) findViewById(R.id.view), new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
//                .go("https://ecgtest.protontek.com/ecg-andlik/#/");
                .go("http://192.168.2.16:3000/#/");

        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new AndroidJSInterface());

        // ecgRealTimeView = findViewById(R.id.id_ecg_view);
        findViewById(R.id.connectCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
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
//                EcgCardManager.getInstance("0C:61:CF:C1:2D:52").disConnect();
            }
        });

        NewSy newSy = findViewById(R.id.idNew);
        newSy.setClickCallBack(new NewSy.Callback() {
            @Override
            public void clickItem(int position) {
                Logger.w("callback position:%d", position);
            }
        });
    }

    private void test() {
//        new FileUtil().testFilePath(this);
        testBuilder();
    }

    private void testBuilder() {
        Computer computer = new Computer.Builder()
                .setIntProperty(10)
                .setStrProperty("str content")
                .build();
        Logger.w("intProperty:%d,strProperty:%s", computer.getIntProperty(), computer.getStrProperty());
    }

//    private void connectCard() {
//        if (true) {
////            TestRxjava.start1();
////            TestRxjava.fetchList();
////            TestRxjava.testJust();
//            new MulThreadTest().startSell();
//            return;
//        }
////        ecgRealTimeView.setWaveSpeed(25.0f);
////        ecgRealTimeView.setSample(500);
//        IEcgAlgorithm ecgAlgorithm = new EcgCardAlgorithm(new EcgAlgorithmListener() {
//            @Override
//            public void receiveEcgFilterData(byte[] bytes) {
//                super.receiveEcgFilterData(bytes);
//                List<Float> filterDatum = DecryptHelper.decryptFilterData(bytes).getFilterDatas();
//                mAgentWeb.getJsAccessEntrace().quickCallJs("callByAndroid", filterDatum.toString());
////                ecgRealTimeView.addEcgData(DecryptHelper.decryptFilterData(bytes).getFilterDatas());//加载解密后的数
////                ecgRealTimeView.addEcgData(ecgData);
////                if (!ecgRealTimeView.isRunning()) {
////                    ecgRealTimeView.startDrawWave();
////                }
//                //Logger.d("android ecgData:%s", s);
//            }
//        });
//
//        DataListener dataListener = new DataListener() {
//            @Override
//            public void receiveEcgRawData(byte[] bytes) {
//                super.receiveEcgRawData(bytes);
////                Logger.d("android ecgData:%s", BleUtils.bytesToHexString(bytes));
//                ecgAlgorithm.processEcgData(bytes);
//                //mAgentWeb.getJsAccessEntrace().quickCallJs("callByAndroid", BleUtils.bytesToHexString(bytes));
//            }
//        };
//
//        OnConnectListener connectListener = new OnConnectListener() {
//            @Override
//            public void onConnectSuccess() {
//                super.onConnectSuccess();
//                Logger.d("connect success");
//                Toast.makeText(MainActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onConnectFaild() {
//                super.onConnectFaild();
//                Logger.d("connect fail");
//            }
//
//            @Override
//            public void onDisconnect(boolean b) {
//                super.onDisconnect(b);
//                Logger.d("disconnect");
//            }
//        };
//
//        EcgCardManager.getInstance("0C:61:CF:C1:2D:52")
//                .setDataListener(dataListener)
//                .connectEcgCard(connectListener);
//    }

    /**
     * call js method
     */
    private void callJs() {
        String params = "1,2,3,4,5,6,7";
        mAgentWeb.getJsAccessEntrace().quickCallJs("callByAndroid", params);
    }

    /**
     * export to js use
     */
    class AndroidJSInterface {
        @JavascriptInterface
        public void addLog(String log) {
            Logger.d(log);
        }
    }


}