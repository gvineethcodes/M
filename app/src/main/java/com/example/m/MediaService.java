package com.example.m;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MediaService extends Service {
    private IBinder mBinder = new MyBinder();
    public static final String APLAY = "play";
    playinterface pinterface;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class MyBinder extends Binder {
        MediaService getService(){
            return MediaService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getStringExtra("AName");
        if(action != null){
            switch (action){
                case APLAY:
                    if (pinterface != null)
                        pinterface.play();
                    break;
            }
        }
        return START_STICKY;
    }
    public void setCallBack(playinterface pinterface){
        this.pinterface = pinterface;
    }
}
