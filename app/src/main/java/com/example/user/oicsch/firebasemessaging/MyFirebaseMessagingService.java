/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.user.oicsch.firebasemessaging;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.user.oicsch.R;
import com.example.user.oicsch.Notification.notification;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "poo";
    private Bitmap bitmap;
    private DatabaseReference databaseReference;
    private SharedPreferences checkstart;
private final Boolean my_default_value=true;
    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        checkstart = getSharedPreferences("start", Context.MODE_PRIVATE);
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            String title=remoteMessage.getData().get("title");
            String message=remoteMessage.getData().get("message");
            String image_url=remoteMessage.getData().get("image_url");
            String date=remoteMessage.getData().get("date");
            String time=remoteMessage.getData().get("time");
            bitmap= getBitmapfromUrl(image_url);
            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("Notification");
            databaseReference.child("Notification").child(checkstart.getString("faculty","")).child(title);
            databaseReference.child("Notification").child(checkstart.getString("faculty","")).child(title).child("Title").setValue(title);
            databaseReference.child("Notification").child(checkstart.getString("faculty","")).child(title).child("Message").setValue(message);
            databaseReference.child("Notification").child(checkstart.getString("faculty","")).child(title).child("Image_url").setValue(image_url);
            databaseReference.child("Notification").child(checkstart.getString("faculty","")).child(title).child("Time").setValue(time);
            databaseReference.child("Notification").child(checkstart.getString("faculty","")).child(title).child("Date").setValue(date);
            sendNotification(title,message,bitmap);
            // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
            scheduleJob();

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Schedule a job using FirebaseJobDispatcher.
     */
    private void scheduleJob() {
        // [START dispatch_job]
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job myJob = dispatcher.newJobBuilder()
                .setService(MyJobService.class)
                .setTag("my-job-tag")
                .build();
        dispatcher.schedule(myJob);
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *  @param title
     * @param message
     * @param image FCM message body received.
     */
    private void sendNotification(String title, String message, Bitmap image) {

        Intent intent = new Intent(getApplicationContext(), notification.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0 /* Request code */, intent,0);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(image))
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setLights(0xff00ff00,200,200)
                .setSound(defaultSoundUri)
                .setPriority(Notification.PRIORITY_MAX)
                .setContentIntent(pendingIntent);
       // Log.d("poo","The value is: "+checkstart.getBoolean("checkbox",my_default_value));
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(checkstart.getBoolean("checkbox",my_default_value))
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
    private Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }
}
