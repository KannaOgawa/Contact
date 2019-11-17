package app.sennen.contact

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//oreo以上
                val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val channel = NotificationChannel(
                    "default",
                    "Default",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                channel.description = "Default channel"
                manager.createNotificationChannel(channel)
            }

            val intent = MainActivity.createIntent(this)
            val contentIntent = PendingIntent.getActivity(
                applicationContext,
                0,
                intent,
                PendingIntent.FLAG_ONE_SHOT
            )

            val notification = NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("タイトル")
                .setContentText("内容")
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        resources,
                        R.drawable.ic_launcher_background
                    )
                )
                .build()

            val manager = NotificationManagerCompat.from(this)
            manager.notify(1, notification)
        }
        button2.setOnClickListener {
            val intent =BCReceiver.createIntent(this)
            val contentIntent = PendingIntent.getBroadcast(applicationContext, 1, intent, PendingIntent.FLAG_ONE_SHOT)

            val trigger = Calendar.getInstance()
            trigger.add(Calendar.HOUR, 1)

            val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            manager.setExact(AlarmManager.RTC_WAKEUP, trigger.timeInMillis, contentIntent)
            button2.setBackgroundColor(Color.GREEN)

        }
    }

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }
}
