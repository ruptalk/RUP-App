package org.techtown.reducetheuseofplastic;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessage extends FirebaseMessagingService {

    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Log.d("check","1");
            showNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("message"));
        }

        if (remoteMessage.getNotification() != null) {
            Log.d("check","2");
            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }
    }

    private RemoteViews getCustomDesign(String title, String message) {
        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.notification);
        remoteViews.setTextViewText(R.id.noti_title, title);
        remoteViews.setTextViewText(R.id.noti_message, message);
        remoteViews.setImageViewResource(R.id.noti_icon, R.mipmap.ic_launcher);
        return remoteViews;
    }

    private void showNotification(String title, String message) {
        Intent intent = new Intent(this, MainActivity.class);
        String channel_id = "channel";
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channel_id)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(uri)
                .setAutoCancel(true)
                .setVibrate(new long[] {1000, 1000, 1000, 1000, 1000})
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            builder = builder.setContent(getCustomDesign(title, message));
        }
        else
        {
            builder = builder.setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.mipmap.ic_launcher);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel(channel_id, "web_app", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setSound(uri, null);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationManager.notify(0, builder.build());
    }

    /*
    private static final String TAG="FirebaseMessage";


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
            if (remoteMessage.getData().size() > 0) {
                Log.d("check","1");
                showNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("message"));
            }

            if (remoteMessage.getNotification() != null) {
                Log.d("check","2");
                showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
            }
        }

        private RemoteViews getCustomDesign(String title, String message) {
            RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.notification);
            remoteViews.setTextViewText(R.id.noti_title, title);
            remoteViews.setTextViewText(R.id.noti_message, message);
            remoteViews.setImageViewResource(R.id.noti_icon, R.mipmap.ic_launcher);
            return remoteViews;
        }

        private void showNotification(String title, String message) {
            Intent intent = new Intent(this, MainActivity.class);
            String channel_id = "channel";
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channel_id)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setSound(uri)
                    .setAutoCancel(true)
                    .setVibrate(new long[] {1000, 1000, 1000, 1000, 1000})
                    .setOnlyAlertOnce(true)
                    .setContentIntent(pendingIntent);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            {
                builder = builder.setContent(getCustomDesign(title, message));
            }
            else
            {
                builder = builder.setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(R.mipmap.ic_launcher);
            }

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                NotificationChannel notificationChannel = new NotificationChannel(channel_id, "web_app", NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.setSound(uri, null);
                notificationManager.createNotificationChannel(notificationChannel);
            }

            notificationManager.notify(0, builder.build());
        }

        super.onMessageReceived(remoteMessage);

        String title=remoteMessage.getData().get("title"); //firebase에서 보낸 메세지의 title
        String message=remoteMessage.getData().get("message"); //firebase에서 보낸 메세지의 내용
        String test=remoteMessage.getData().get("test");

        Intent intent=new Intent(this, MainActivity.class);
        intent.putExtra("test",test);

        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            String channel="채널";
            String channel_nm="채널명";

            NotificationManager notichannel=(android.app.NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channelMessage=new NotificationChannel(channel, channel_nm, NotificationManager.IMPORTANCE_DEFAULT);
            channelMessage.setDescription("채널에 대한 설명");
            channelMessage.enableLights(true);
            channelMessage.enableVibration(true);
            channelMessage.setShowBadge(false);
            channelMessage.setVibrationPattern(new long[]{1000,1000});
            notichannel.createNotificationChannel(channelMessage);

            //푸시알림을 Builder를 이용하여 만듬
            NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(this,channel)
                    .setSmallIcon(R.drawable.logo_rup)
                    .setContentTitle(title) //푸시알림의 제목
                    .setContentText(message) //푸시알림의 내용
                    .setChannelId(channel)
                    .setAutoCancel(true) //선택시 자동으로 삭제되도록 설정
                    .setContentIntent(pendingIntent) //알림을 눌렀을 때 실행할 인텐트 설정
                    .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE);

            NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(9999,notificationBuilder.build());
        }
        else{
            NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(this,"")
                    .setSmallIcon(R.drawable.logo_rup)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE);

            NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(9999,notificationBuilder.build());
        }
    }



        Log.d(TAG, "From: " + remoteMessage.getFrom());


        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            if (true) {
                scheduleJob();
            } else {
                handleNow();
            }
        }


        if(remoteMessage.getNotification()!=null){
            Log.d(TAG,"Message Notification Body: "+remoteMessage.getNotification().getBody());
        }
    }

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG,"Refreshed token: "+token);

        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }
    */

}
