package com.example.sharesapp.FunktionaleKlassen.Receiver;

import android.content.Context;
import android.content.Intent;

import androidx.legacy.content.WakefulBroadcastReceiver;

public class BootWakefulReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())
        || "android.permission.WAKE_LOCK".equals(intent.getAction())) {
//            Intent serviceIntent = new Intent(context, RequestDataWakefulService.class);
//            startWakefulService(context, serviceIntent);
        }
    }
}
