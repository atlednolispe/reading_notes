package com.example.atlednolispe.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BootCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // onReceive中不允许开启线程,添加过多逻辑或者耗时操作执行时间过长就会报错
        Toast.makeText(context, "boot complete", Toast.LENGTH_LONG).show();
    }
}
