package com.example.plantezemobileapplication.view.ecoTracker;

import android.app.Activity;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Data;

import com.example.plantezemobileapplication.utils.Habit;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;

import java.util.concurrent.TimeUnit;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class HabitNotification {

    public static final int REQUEST_CODE_POST_NOTIFICATIONS = 1;
    private Activity context;

    public HabitNotification(Activity context) {
        this.context = context;
    }

    private long calculateTimeDifference(Habit habit, int targetDay) {
        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            Date habitDate = timeFormat.parse(habit.getTime());

            Calendar currentTime = Calendar.getInstance();
            Calendar targetTime = Calendar.getInstance();
            targetTime.setTime(habitDate);

            targetTime.set(Calendar.YEAR, currentTime.get(Calendar.YEAR));
            targetTime.set(Calendar.MONTH, currentTime.get(Calendar.MONTH));
            targetTime.set(Calendar.DAY_OF_MONTH, currentTime.get(Calendar.DAY_OF_MONTH));

            int currentDayOfWeek = currentTime.get(Calendar.DAY_OF_WEEK);
            int targetDayOfWeek = targetDay;

            int daysDifference = targetDayOfWeek - currentDayOfWeek;

            if (targetTime.before(currentTime) && daysDifference == 0) {
                targetTime.add(Calendar.WEEK_OF_YEAR, 1);
            } else if (daysDifference < 0) {
                daysDifference += 7;
            }

            targetTime.add(Calendar.DAY_OF_YEAR, daysDifference);

            // Now that the date is set, make sure the target time is set correctly
            targetTime.set(Calendar.HOUR_OF_DAY, habitDate.getHours());
            targetTime.set(Calendar.MINUTE, habitDate.getMinutes());
            targetTime.set(Calendar.SECOND, 0);

            System.out.println("Current Time: " + habit.getName() + currentTime.getTime());
            System.out.println("Target Time: " + habit.getName() + targetTime.getTime());

            return targetTime.getTimeInMillis() - currentTime.getTimeInMillis();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void createWeeklyNotifications(Habit habit) {
        if (habit.getDays() == null || habit.getDays().isEmpty()) {
            return;
        }


        for (int day : habit.getDays()) {
            long timeDifference = calculateTimeDifference(habit, day);
            Data inputData = new Data.Builder()
                    .putString("habitName", habit.getName())
                    .putString("habitId", habit.getId())
                    .build();

            PeriodicWorkRequest weeklyWorkRequest = new PeriodicWorkRequest.Builder(
                    NotificationWorker.class,
                    7, TimeUnit.DAYS)
                    .setInitialDelay(timeDifference, TimeUnit.MILLISECONDS)
                    .setInputData(inputData) // Pass the input data to the worker
                    .build();

            WorkManager.getInstance(context.getApplicationContext())
                    .enqueueUniquePeriodicWork(
                            "WeeklyNotificationWork_" + habit.getId() + "_Day_" + day,
                            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
                            weeklyWorkRequest
                    );

        }



    }

    public Task<Void> removeWeeklyNotification(Habit habit) {
        if (habit.getDays() == null || habit.getDays().isEmpty()) {
            return Tasks.forResult(null);
        }

        TaskCompletionSource<Void> taskCompletionSource = new TaskCompletionSource<>();

        for (int day : habit.getDays()) {
            WorkManager.getInstance(context.getApplicationContext())
                    .cancelUniqueWork("WeeklyNotificationWork_" + habit.getId() + "_Day_" + day);
        }

        taskCompletionSource.setResult(null);
        return taskCompletionSource.getTask();
    }

    public void removeCreateWeeklyNotification(Habit habit) {
        removeWeeklyNotification(habit).addOnCompleteListener(task -> {
           if (task.isSuccessful()) {
               createWeeklyNotifications(habit);
           }
        });
    }
}
