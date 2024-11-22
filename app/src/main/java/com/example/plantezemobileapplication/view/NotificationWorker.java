package com.example.plantezemobileapplication.view;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import java.util.UUID;

import androidx.work.Worker;
import androidx.work.WorkerParameters;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationWorker extends Worker {

    public NotificationWorker(Context context, WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @SuppressLint("MissingPermission")
    @Override
    public Result doWork() {
        String habitName = getInputData().getString("habitName");
        System.out.println("HabitName: " + habitName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Habit Notifications";
            String description = "Notifications for habit reminders";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("habit_channel", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Create the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "habit_channel")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Eco Friendly Habit Reminder")
                .setContentText(habitName)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        String habitId = UUID.randomUUID().toString();
        notificationManager.notify(habitId.hashCode(), builder.build());

        return Result.success();
    }


}
