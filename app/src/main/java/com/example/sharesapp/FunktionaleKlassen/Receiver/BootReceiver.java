package com.example.sharesapp.FunktionaleKlassen.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.sharesapp.FunktionaleKlassen.Services.NotificationOnlyStickyService;
import com.example.sharesapp.FunktionaleKlassen.Services.RequestDataService;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction()) ||
                "android.intent.action.QUICKBOOT_POWERON".equals(intent.getAction())) {
            Intent myIntent = new Intent(context, NotificationOnlyStickyService.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startService(myIntent);
        }
    }
}
