package com.example.atlednolispe.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // 这里收不到广播很奇怪
        Toast.makeText(context, "received in MyBroadCastReceiver", Toast.LENGTH_SHORT).show();
    }
}
