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
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {
    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }
    var openContactList = readOpen()

    override fun onResume() {
        super.onResume()
        updateScreen()
    }
    fun readOpen(): RealmResults<Contact> {
        return realm.where(Contact::class.java).greaterThan("openDate", 0.toInt())
            .findAll().sort("name", Sort.ASCENDING)
    }

    fun calc(): RealmResults<Contact> {
        var resultArray = realm.where(Contact::class.java).greaterThan("openDate", 0.toInt())
            .findAll()//.sort("openDate", Sort.ASCENDING)

        for (result in resultArray) {
            realm.executeTransaction {
                result.diff=diffDays(result.openDate+result.limit*1000 * 60 * 60 * 24)//残り何日
            }
        }

        resultArray = realm.where(Contact::class.java).greaterThan("openDate", 0.toInt())
            .findAll().sort("diff",Sort.ASCENDING)

        Log.e("array",resultArray.toString())

        return  resultArray
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var mainAdapter = MainListAdapter(this, openContactList)
        mainlist.adapter = mainAdapter


        nameTextView.setOnClickListener {
            val intent = BCReceiver.createIntent(this)
            val contentIntent = PendingIntent.getBroadcast(
                applicationContext,
                1,
                intent,
                PendingIntent.FLAG_ONE_SHOT
            )
            var limitDay = Calendar.getInstance()
            val now = Calendar.getInstance()
            var diif: Int = getDiffDays(limitDay, now)
            val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            manager.setExact(AlarmManager.RTC_WAKEUP, limitDay.timeInMillis, contentIntent)
            nameTextView.setBackgroundColor(Color.GREEN)
        }

        settingButton.setOnClickListener {
            delete(openContactList[0]!!)
        }
        listButton.setOnClickListener {
            val intent = Intent(this, ContactListActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun getDiffDays(calendar1: Calendar, calendar2: Calendar): Int {
        //==== ミリ秒単位での差分算出 ====//
        val diffTime = calendar1.timeInMillis - calendar2.timeInMillis
        //==== 日単位に変換 ====//
        val MILLIS_OF_DAY = 1000 * 60 //* 60 * 24
        return (diffTime / MILLIS_OF_DAY).toInt()
    }

    fun diffDays(calendar1: Long): Int {
        val now = Calendar.getInstance()
        //==== ミリ秒単位での差分算出 ====//
        val diffTime =  calendar1 - now.timeInMillis
        //==== 日単位に変換 ====//
        val MILLIS_OF_DAY = 1000 * 60 * 60 * 24
        return (diffTime / MILLIS_OF_DAY).toInt()
    }

    fun updateScreen() {

        var mainContact = realm.where(Contact::class.java)
            .greaterThan("openDate", 0.toInt()).findFirst()

        if (mainContact != null) {
            var tmp: Int = diffDays(mainContact.openDate)//開封日から現在の経過
            var diff = mainContact.limit - tmp//残り
            var castd = diff.toDouble()
            var percent = (castd / (mainContact.limit)) * 100

            progressBar.setProgress(percent.toInt())
            limitTextView.text = diff.toString()
            nameTextView.text = mainContact.name
        }


    }

    fun delete(contact: Contact) {
        realm.executeTransaction {
            contact.deleteFromRealm()
        }
    }
}
