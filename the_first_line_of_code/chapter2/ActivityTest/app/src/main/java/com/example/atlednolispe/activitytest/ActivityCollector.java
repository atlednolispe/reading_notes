package com.example.atlednolispe.activitytest;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity: activities) {
            if (!activity.isFinishing()){
                activity.finish();
            }
            activities.clear();
            // killProcess只能用于杀掉当前程序的进程
            Log.d("CollectorActivity", Integer.toString(android.os.Process.myPid()));
            // 将当前应用进程杀掉后再启动也是不会调用savedInstanceState
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
