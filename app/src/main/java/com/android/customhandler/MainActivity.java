package com.android.customhandler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        Looper.prepare();
        final Handler handler = new Handler() {
            @Override
            public void handlerMessage(Message msg) {
                super.handlerMessage(msg);
                Log.e(TAG, "handlerMessage: 线程ID:" + Thread.currentThread() + "-s-message:" + msg.obj);
            }
        };

        new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < 10; i++) {
                    Message msg = new Message();
                    msg.obj = UUID.randomUUID();
                    Log.e(TAG, "run: 线程ID:" + Thread.currentThread() + "--message:" + msg.obj);
                    handler.sendMessage(msg);
                }
            }
        }.start();
        Looper.loop();
    }
}
