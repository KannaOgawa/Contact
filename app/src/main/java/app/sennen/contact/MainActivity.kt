package app.sennen.contact

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {
    lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRealm()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//oreo以上
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(
                "default",
                "Default",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "Default channel"
            manager.createNotificationChannel(channel)

            setText()
        }


        textView.setOnClickListener {
            val intent =BCReceiver.createIntent(this)
            val contentIntent = PendingIntent.getBroadcast(applicationContext, 1, intent, PendingIntent.FLAG_ONE_SHOT)
            var limitDay =Calendar.getInstance()
            val now = Calendar.getInstance()
            limitDay.add(Calendar.MINUTE,3)
            var diif:Int =getDiffDays(limitDay,now)
            limitTextView.text=diif.toString()
//            val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            manager.setExact(AlarmManager.RTC_WAKEUP, limitDay.timeInMillis, contentIntent)
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
            finish()
        }
    }

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }

    private fun initRealm() {
        val realmConfiguration = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .build()
        realm = Realm.getInstance(realmConfiguration)
    }

    fun getDiffDays(calendar1: Calendar, calendar2: Calendar): Int {
        //==== ミリ秒単位での差分算出 ====//
        val diffTime = calendar1.timeInMillis - calendar2.timeInMillis
        //==== 日単位に変換 ====//
        val MILLIS_OF_DAY = 1000 * 60 //* 60 * 24
        return (diffTime / MILLIS_OF_DAY).toInt()
    }

    fun setText(){


        Log.e("tag","setText")
        var mainContact = realm.where(Contact::class.java)
            .equalTo("isOpen","1")
            .findAll()
        Log.d("tag",mainContact.toString())


    }

}
