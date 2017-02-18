package assignment.android.acadgild.unboundservice;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

/**
 * Created by DivyaVipin on 2/17/2017.
 */

public class MyService extends Service {
    String tag="MyService";
    MediaPlayer mp;

    @Nullable
    @Override
    //Since it is not a bound service thi functionality not needed
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    //In onCreate initializing the media player with music
    public void onCreate(){
        super.onCreate();
        mp = MediaPlayer.create(getApplicationContext(), R.raw.music1);
    }



    //The system calls this method when another component, such as an activity, requests that the service be started, by calling startService(). If you implement this method,
// it is your responsibility to stop the service when its work is done, by calling stopSelf() or stopService() methods.
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        mp.start();
        Bitmap icon1 = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setAutoCancel(true)
                //.setContentTitle("Music ")
                .setSmallIcon(R.mipmap.ic_launcher).setLargeIcon(icon1)
                .setContentText("Music");

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("Music is playing");
        bigText.setBigContentTitle("Music Player");
        bigText.setSummaryText("By: Divya");
        mBuilder.setStyle(bigText);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(MyService.this,MainActivity.class);

        // The stack builder object will contain an artificial back stack for
        // the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager1 = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // mId allows you to update the notification later on.
        mNotificationManager1.notify(100, mBuilder.build());

        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        mp.release();
        super.onDestroy();
    }

}
