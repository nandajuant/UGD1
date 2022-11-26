package com.example.komoke

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.SystemClock.sleep
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.komoke.databinding.ActivityMainBinding
import com.google.firebase.installations.time.SystemClock

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private val CHANNEL_ID_1 = "channel_notification_01"
    private val CHANNEL_ID_2 = "channel_notification_02"
    private val notificationId1 = 101
    private val notificationId2 = 102
    private val notificationId3 = 103
    private val notificationId4 = 104

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        createNotificationChannel()

        val btnLoginMain = findViewById<Button>(R.id.btn_login_main)
        val btnRegisterMain = findViewById<Button>(R.id.btn_register_main)

        btnLoginMain.setOnClickListener {
            val moveLogin = Intent(this, LoginActivity::class.java)
            startActivity(moveLogin)
            sendNotification3()
        }

        btnRegisterMain.setOnClickListener {
            val moveRegister = Intent(this, RegisterActivity::class.java)
            startActivity(moveRegister)
            sendNotification3()
        }


    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Tittle"
            val descriptionText = "Notification Description"

            val channel1 = NotificationChannel(CHANNEL_ID_1, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }

            val channel2 = NotificationChannel(CHANNEL_ID_2, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel1)
            notificationManager.createNotificationChannel(channel2)
        }
    }

    private fun sendNotification3(){
        val GROUP_KEY_WORK_EMAIL = "com.android.example.WORK_EMAIL"
        val rexy = BitmapFactory.decodeResource(resources, R.drawable.profile_rexy_round)
        val nanda = BitmapFactory.decodeResource(resources, R.drawable.profile_nanda_round)
        val bayu = BitmapFactory.decodeResource(resources, R.drawable.profile_bayu_round)
        val newMessageNotification1 = NotificationCompat.Builder(this@MainActivity, CHANNEL_ID_1)
            .setSmallIcon(R.drawable.ic_email)
            .setContentTitle("Nanda")
            .setContentText("Thank you for choosing us to be your...")
            .setLargeIcon(nanda)
            .setColor(Color.CYAN)
            .setGroup(GROUP_KEY_WORK_EMAIL)
            .build()

        val newMessageNotification2 = NotificationCompat.Builder(this@MainActivity, CHANNEL_ID_1)
            .setSmallIcon(R.drawable.ic_email)
            .setContentTitle("Bayu")
            .setContentText("Lets travel with us...")
            .setLargeIcon(bayu)
            .setColor(Color.CYAN)
            .setGroup(GROUP_KEY_WORK_EMAIL)
            .build()

        val newMessageNotification3 = NotificationCompat.Builder(this@MainActivity, CHANNEL_ID_1)
            .setSmallIcon(R.drawable.ic_email)
            .setContentTitle("Rexy")
            .setContentText("Welcome to komoke app...")
            .setLargeIcon(rexy)
            .setColor(Color.CYAN)
            .setGroup(GROUP_KEY_WORK_EMAIL)
            .build()

        val summaryNotification = NotificationCompat.Builder(this@MainActivity, CHANNEL_ID_1)
            .setContentText("Three new messages")
            .setSmallIcon(R.drawable.ic_email)
            //build summary info into InboxStyle template
            .setStyle(NotificationCompat.InboxStyle()
                .addLine(newMessageNotification1.toString())
                .addLine(newMessageNotification2.toString())
                .addLine(newMessageNotification3.toString())
                .setBigContentTitle("3 new messages")
                .setSummaryText("komoke@gmail.com"))
            .setColor(Color.CYAN)
            //specify which group this notification belongs to
            .setGroup(GROUP_KEY_WORK_EMAIL)
            //set this notification as the summary for the group
            .setGroupSummary(true)
            .build()

        NotificationManagerCompat.from(this).apply {
            notify(notificationId1, newMessageNotification1)
            notify(notificationId2, newMessageNotification2)
            notify(notificationId3, newMessageNotification3)
            notify(notificationId4, summaryNotification)
        }

    }


    //progress bar
//    private fun sendNotification3(){
//        val builder = NotificationCompat.Builder(this, CHANNEL_ID_1).apply {
//            setContentTitle("Loading")
//            setContentText("Loading in progress")
//            setSmallIcon(R.drawable.ic_baseline_notifications_24)
//            setPriority(NotificationCompat.PRIORITY_LOW)
//        }
//        val PROGRESS_MAX = 100
//        val PROGRESS_CURRENT = 0
//        NotificationManagerCompat.from(this).apply {
//            // Issue the initial notification with zero progress
//            builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false)
//            notify(notificationId3, builder.build())
//
//            // Do the job here that tracks the progress.
//            // Usually, this should be in a
//            // worker thread
//            // To show progress, update PROGRESS_CURRENT and update the notification with:
//            // builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);
//            // notificationManager.notify(notificationId, builder.build());
//
//            // When done, update the notification one more time to remove the progress bar
//            builder.setContentText("Loading complete")
//                .setProgress(0, 0, false)
//            notify(notificationId3, builder.build())
//        }
//    }

}