package com.example.plantezemobileapplication.view;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.plantezemobileapplication.R;
import com.example.plantezemobileapplication.model.Habit;

import java.util.ArrayList;

public class HabitNotification {

    static final int REQUEST_CODE_POST_NOTIFICATIONS = 1;
    private Activity context;
    private ArrayList<Notification> notifications;
    private ArrayList<Integer> notificationIds;

    private NotificationManager notificationManager;
    public HabitNotification(Activity context) {
        this.context = context;
        this.notificationIds = new ArrayList<Integer>();
        this.notifications = new ArrayList<Notification>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Habit Notifications";
            String description = "Used for displaying habits at specified times";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("habit_channel", name, importance);
            channel.setDescription(description);

            // Register the channel with the system
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void createNotification(Habit habit) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "habit_channel")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Eco Friendly Habit Reminder")
                .setContentText(habit.getName())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // Build the notification and add it to the list
        Notification notification = builder.build();
        notifications.add(notification);
        int notificationId =notificationIds.size() + 1;
        notificationIds.add(notificationId);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            requestNotificationPermission();
            return;
        }

        // Send the notification
        notificationManager.notify(notificationId, notification);

    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // API level 33
            ActivityCompat.requestPermissions(
                    context,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS},
                    REQUEST_CODE_POST_NOTIFICATIONS
            );
        }

    }
}
