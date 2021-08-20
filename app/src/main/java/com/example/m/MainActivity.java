package com.example.m;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements playinterface, ServiceConnection {

    public static final String APLAY = "play";
    MediaService mediaService;
    MediaSessionCompat mediaSessionCompat;
    MediaPlayer mp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton playPauseB = findViewById(R.id.imageButton);
        mediaSessionCompat = new MediaSessionCompat(this,"mytag");

        playPauseB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        bindService(new Intent(this,MediaService.class),this,BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();

        unbindService(this);
    }

    @Override
    public void play() {
        if( mp == null){
            Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            mp = MediaPlayer.create(getApplicationContext(), alert);

            mp.start();
        }else{
            if(mp.isPlaying())
                mp.pause();
            else {
                mp.start();
            }
        }
        showNotification();
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

        MediaService.MyBinder myBinder = (MediaService.MyBinder) iBinder;
        mediaService = myBinder.getService();
        mediaService.setCallBack(this);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }

    public void showNotification(){
        Intent playI = new Intent(this, NotificationReceiver.class).setAction(APLAY);
        PendingIntent playPI = PendingIntent.getBroadcast(this,90,playI,PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this,"92")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("notify")
                .setContentText("nice")
                .addAction(R.drawable.playimg_foreground,"play",playPI)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                .setMediaSession(mediaSessionCompat.getSessionToken()))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOnlyAlertOnce(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0,notification);
    }
}