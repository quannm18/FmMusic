package com.example.fmmusic.Controller;

import static com.example.fmmusic.Controller.ApplicationClass.ACTION_NEXT;
import static com.example.fmmusic.Controller.ApplicationClass.ACTION_PLAY;
import static com.example.fmmusic.Controller.ApplicationClass.ACTION_PREVIOUS;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String actionName = intent.getAction();
        Intent serviceIntent = new Intent(context,MusicService.class);
        if (actionName!=null){
            Log.e("sv",actionName);
            switch (actionName){
                case  ACTION_PREVIOUS:
                    serviceIntent.putExtra("ActionName","previous");
                    context.startService(serviceIntent);
                    break;
                case  ACTION_PLAY:
                    serviceIntent.putExtra("ActionName","playPause");
                    context.startService(serviceIntent);
                    break;
                case  ACTION_NEXT:
                    serviceIntent.putExtra("ActionName","next");
                    context.startService(serviceIntent);
                    break;

            }
        }
    }
}
