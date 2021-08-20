package com.example.m;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {
    public static final String APLAY = "play";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context,MediaService.class);
        if(intent.getAction() != null){
            switch (intent.getAction()){
                case APLAY:
                    intent1.putExtra("AName",intent.getAction());
                    context.startService(intent1);
                    break;
            }
        }
    }
}
