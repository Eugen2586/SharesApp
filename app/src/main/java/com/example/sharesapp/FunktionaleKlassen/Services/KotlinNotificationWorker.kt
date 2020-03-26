package com.example.sharesapp.FunktionaleKlassen.Services

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.sharesapp.R

class KotlinNotificationWorker(var context: Context, var workerParameters: WorkerParameters) :
        CoroutineWorker(context, workerParameters){

    override suspend fun doWork(): Result {
        showNotification()

        return Result.retry()
    }

    private fun showNotification() {
        // https://developer.android.com/training/notify-user/build-notification#java
        // build notification channel
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = "channel_name"
//            val descriptionText = "channel_description"
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel("channel_id", name, importance).apply {
//                description = descriptionText
//            }
//            // Register the channel with the system
//            val notificationManager: NotificationManager =
//                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }

        // build notification
        val builder = NotificationCompat.Builder(this.context) // without channel
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("BBB")
                .setContentText("Content Content")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // notify user
        with(NotificationManagerCompat.from(this.context)) {
            // notificationId is a unique int for each notification that you must define
            notify(0, builder.build())

        }
    }
}