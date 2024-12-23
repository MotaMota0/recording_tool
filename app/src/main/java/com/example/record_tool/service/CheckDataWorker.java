package com.example.record_tool.service;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.SavedStateHandle;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.record_tool.R;
import com.example.record_tool.ui.activity.EstimatorClientActivity;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Date;

public class CheckDataWorker extends Worker {

    public CheckDataWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }
    private void getNotify(){
        Intent intent = new Intent(getApplicationContext(), EstimatorClientActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                0 , intent,PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationChannel notificationChannel = new NotificationChannel(
                "TEST_CHANNEL","TEST_DESCRIPTION",
                NotificationManager.IMPORTANCE_HIGH);


        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);

        Notification notification = new NotificationCompat.Builder(getApplicationContext(),"TEST_CHANNEL")
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setContentTitle("Мастерді бағалау")
                .setContentText("1 - 5 Аралығында Баға беріңіз")
                .addAction(R.drawable.select_ic_for_notif, "Принять", pendingIntent)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(45,notification);

    }
    private void getNotify2(){

        NotificationChannel notificationChannel = new NotificationChannel(
                "5","TEST_DESCRIPTION",
                NotificationManager.IMPORTANCE_HIGH);


        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);

        Notification notification = new NotificationCompat.Builder(getApplicationContext(),"5")
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setContentTitle("Еске алу")
                .setContentText("Сізде жакын уақытта жазылу бар!")
                .build();

        notificationManager.notify(46,notification);

    }
    @NonNull
    @Override
    public Result doWork() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        // Инициализация переменной timestamp
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("EditPrefs", Context.MODE_PRIVATE);
        String timestampString = sharedPreferences.getString("timestamp", "Default");
        try {
            long millis = Long.parseLong(timestampString);

            Timestamp timestampUser = new Timestamp(new Date(millis));
            firestore.collection("bookings")
                    .whereEqualTo("timestamp", timestampUser)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Timestamp firestoreTimestamp = documentSnapshot.getTimestamp("timestamp");
                            if (firestoreTimestamp != null && firestoreTimestamp.equals(timestampUser)) {
                                Date date = firestoreTimestamp.toDate();
                                Date currentDate = new Date();

                                if (date.before(currentDate)) {
                                    getNotify();
                                }
                                if (date.after(currentDate)) {
                                    getNotify2();
                                }
                            }
                        }
                    })
                    .addOnFailureListener(v -> {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    });
            return Result.success();
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Invalid timestamp format", Toast.LENGTH_SHORT).show();
            return Result.failure();
        }
    }


}
