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
import android.graphics.Color
import android.os.Build
import com.google.firebase.database.FirebaseDatabase
import java.util.*



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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


        textView.setOnClickListener {
            val intent =BCReceiver.createIntent(this)
            val contentIntent = PendingIntent.getBroadcast(applicationContext, 1, intent, PendingIntent.FLAG_ONE_SHOT)

            val trigger = Calendar.getInstance()
            trigger.add(Calendar.MINUTE,3)

            val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            manager.setExact(AlarmManager.RTC_WAKEUP, trigger.timeInMillis, contentIntent)
            textView.setBackgroundColor(Color.GREEN)

        }


        settingButton.setOnClickListener {
//            val intent = Intent(this,AddActivity::class.java)
//            startActivity(intent)
            settingButton.setBackgroundColor(Color.GREEN)
        }
        listButton.setOnClickListener {
            val intent = Intent(this,ContactListActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }

}
