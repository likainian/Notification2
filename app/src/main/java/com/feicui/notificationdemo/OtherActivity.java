package com.feicui.notificationdemo;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.hardware.Camera;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.regex.Pattern;

public class OtherActivity extends AppCompatActivity implements View.OnClickListener{

    Button mBtnPhone;
    Button mBtnCPU;
    Button mBtnMemory;
    Button mBtnRoot;
    Button mBtnDisplay;

    Context mContext;

    private static final String TAG = "OtherActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        mBtnPhone = (Button) findViewById(R.id.btn_phone);
        mBtnCPU = (Button) findViewById(R.id.btn_cpu);
        mBtnMemory = (Button) findViewById(R.id.btn_memory);
        mBtnRoot = (Button) findViewById(R.id.btn_root);
        mBtnDisplay = (Button) findViewById(R.id.btn_display);

        mBtnPhone.setOnClickListener(this);
        mBtnCPU.setOnClickListener(this);
        mBtnMemory.setOnClickListener(this);
        mBtnRoot.setOnClickListener(this);
        mBtnDisplay.setOnClickListener(this);

        mContext = OtherActivity.this;


    }
    public boolean isRoot(){
        boolean bool=false;
        try {
            if ((!new File("/system/bin/su").exists()) && (!new File("/system/xbin/su").exists())){
                bool=false;
            }else{
                bool=true;
            }
        } catch (Exception e) {

        }
        return bool;
    }
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
             case R.id.btn_phone:
                TelephonyManager telManager = (TelephonyManager) mContext.getSystemService
                        (Context.TELEPHONY_SERVICE);
                 // 设备ID
                 String deviceId = telManager.getDeviceId();
                 Log.d(TAG, "TelephonyManager: deviceId == " + deviceId);

                 // 拿到电话号码
                 String line1Number = telManager.getLine1Number();
                 Log.d(TAG, "TelephonyManager:  line1Number ==" + line1Number);

                 // 网络状态编号
                 int networkType = telManager.getNetworkType();
                 Log.d(TAG, "TelephonyManager: networkType==" + networkType);

                 // 国家代码
                 String countryIso = telManager.getSimCountryIso();
                 Log.d(TAG, "TelephonyManager: countryIso== " + countryIso);

                 // 运营商名
                 String operatorName = telManager.getSimOperatorName();
                 Log.d(TAG, "TelephonyManager: operatorName== " + operatorName);

                 int phoneType = telManager.getPhoneType();
                 Log.d(TAG, "TelephonyManager: phoneType== " + phoneType);
                 break;

            case R.id.btn_cpu:
                WifiManager wifi = (WifiManager) mContext.getSystemService(WIFI_SERVICE);

                int wifiState = wifi.getWifiState(); // WIFI_STATE_ENABLED = 3
                Log.d(TAG, "WifiManager: wifiState == " + wifiState);

                WifiInfo wifiInfo = wifi.getConnectionInfo();
                String bssid = wifiInfo.getBSSID();
                int ipAddress = wifiInfo.getIpAddress(); // 503425216 --> byte 255.255.255.255
                int linkSpeed = wifiInfo.getLinkSpeed(); // 43 --> mbps /8  3.2  2.5G
                String macAddress = wifiInfo.getMacAddress();
                String ssid = wifiInfo.getSSID();  // 指的是链接的wifi名称
                int networkId = wifiInfo.getNetworkId();
                Log.d(TAG, "WifiManager: bssid == " + bssid);
                Log.d(TAG, "WifiManager: ipAddress == " + ipAddress);
                Log.d(TAG, "WifiManager: linkSpeed == " + linkSpeed);
                Log.d(TAG, "WifiManager: macAddress == " + macAddress);
                Log.d(TAG, "WifiManager: ssid == " + ssid);
                Log.d(TAG, "WifiManager: networkId == " + networkId);

                int sdkInt = Build.VERSION.SDK_INT;
                Log.d(TAG, "SDK: sdkInt == " + sdkInt); // sdkInt == 23 --> 6.0
//                String os = Build.VERSION.BASE_OS;
//                Log.d(TAG, "SDK: os == " + os);
                String release = Build.VERSION.RELEASE; // release == 6.0.1
                Log.d(TAG, "SDK: release == " + release);

                File dir = new File("/sys/devices/system/cpu/");
                File[] files = dir.listFiles(new CpuFilter());
                Log.d(TAG, "CPU: " + files.length); // 4 4核心的CPU
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < Build.SUPPORTED_ABIS.length; i++) {
                    sb.append(Build.SUPPORTED_ABIS[i]+" ");
                }
                String string = sb.toString();
                String brand = Build.BRAND;
                String manufacturer = Build.MANUFACTURER;
                String model = Build.MODEL;
                String radioVersion = Build.getRadioVersion();

                Log.d(TAG, "abis: CPU品牌" + string);
                Log.d(TAG, "abis: radioVersion == " + radioVersion);
                Log.d(TAG, "abis: 设备 brand " + brand); // 手机品牌
                Log.d(TAG, "abis: 设备 manufacturer " + manufacturer); // 制造商
                Log.d(TAG, "abis: 设备 model " + model); // 具体型号

                break;

            case R.id.btn_memory:
                ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
                ActivityManager manager = (ActivityManager) mContext.getSystemService(
                        ACTIVITY_SERVICE);
                manager.getMemoryInfo(info);
                long availMem = info.availMem;
                Log.d(TAG, "getMemoryInfo: " + availMem / 1024 / 1024); // 272158720  拿到的是瞬间值


                String sdCardPath = MemoryManager.getPhoneInSDCardPath();
                Log.d(TAG, "sdCardPath: " + sdCardPath);

                long phoneSelfSize = MemoryManager.getPhoneSelfSize(); // 1397428224 byte值 /1024 kb /1024 mb
                long mb = phoneSelfSize / 1024 / 1024;
                Log.d(TAG, "phoneSelfSize: " + mb); //phoneSelfSize: 1332

                long phoneSelfFreeSize = MemoryManager.getPhoneSelfFreeSize();
                Log.d(TAG, "phoneSelfFreeSize: " + phoneSelfFreeSize);  //681328640 --> 649

                float phoneSize = MemoryManager.getPhoneSelfSDCardSize(); // 手机内部储存 不是SD卡
                Log.d(TAG, "phoneSize: " + (phoneSize / 1024 / 1024 / 1024));  // 5204983808  约等于5G
                //  4963MB 4.847519GB

                float phoneFreeSize = MemoryManager.getPhoneSelfSDCardFreeSize(); // 约900MB
                Log.d(TAG,
                        "phoneFreeSize: " + (phoneFreeSize / 1024 / 1024 / 1024));  //947101696   phoneFreeSize: 0.8819313

                long phoneAllSize = MemoryManager.getPhoneAllSize();
                long phoneAllFreeSize = MemoryManager.getPhoneAllFreeSize();
                Log.d(TAG,
                        "phoneAllSize: ==" + phoneAllSize / 1024 / 1024 + "   phoneAllFreeSize:==" +
                                phoneAllFreeSize / 1024 / 1024); //phoneAllSize: ==6296   phoneAllFreeSize:==1552
                    break;

            case R.id.btn_root:
                Log.d(TAG, "btn_root:  " + isRoot());
                break;

            case R.id.btn_display:

                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay()
                        .getMetrics(metrics);
                int widthPixels = metrics.widthPixels;
                int heightPixels = metrics.heightPixels;
                Log.d(TAG, "DisplayMetrics: " + widthPixels + "===" + heightPixels);  // width
                // 720 height 1184


                // Camera
                Camera camera = Camera.open();
                Camera.Parameters parameters = camera.getParameters();
                List<Camera.Size> sizes = parameters.getSupportedPictureSizes();
                Camera.Size s = null;
                for (Camera.Size size : sizes) {
                    if (s == null) {
                        s = size;
                    } else if (s.width * s.height < size.width * size.height) {
                        s = size;
                    }
                }
                camera.release();
                Log.d(TAG, "onClick: " + s.width + "---" + s.height); // 3264px---2448px 1920*1080

                break;


        }
    }

    class CpuFilter implements  FileFilter {
        @Override
        public boolean accept(File pathname) {
            if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                return true;
            }
            return false;
        }
    }
}
