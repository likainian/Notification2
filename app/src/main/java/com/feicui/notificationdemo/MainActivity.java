package com.feicui.notificationdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnShow;

    private Context mContext;

    private NotificationManager mNotiManager;
    private Notification mNotification;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;

        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        mNotiManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        mBtnShow = (Button) findViewById(R.id.btn_show);
        mBtnShow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_show:
                Toast.makeText(MainActivity.this, "点击了Button", Toast.LENGTH_SHORT)
                        .show();
                // 点击通知以后的跳转
                Intent intent = new Intent(mContext, OtherActivity.class);
                PendingIntent pi = PendingIntent.getActivity(mContext, 0, intent, 0);

                // builder对象
                Notification.Builder builder = new Notification.Builder(this);

                builder.setContentTitle("通知标题") // 设置标题
                        .setContentText("通知内容") // 内容
                        .setSubText("SubText")
                        .setTicker("这里是Ticker, 你收到了信息")
                        .setWhen(System.currentTimeMillis())
                        .setDefaults(
                                Notification.DEFAULT_ALL)
                        .setAutoCancel(true)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(pi);

                // 把创建过的notification样式 赋值给 notification的对象
                mNotification = builder.build();

                mNotiManager.notify(1, mNotification);
                break;
        }
    }
}
