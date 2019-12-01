package app.sennen.contact

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_contact_list.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class ContactListActivity : AppCompatActivity() {
    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }
    val list = readAll()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_list)

        var adapter = ContactListAdapter(this, list, object : ContactListAdapter.Click {
            override fun onclick(position: Int) {

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
                val intent = BCReceiver.createIntent(applicationContext)
                val contentIntent = PendingIntent.getBroadcast(
                    applicationContext,
                    1,
                    intent,
                    PendingIntent.FLAG_ONE_SHOT
                )
                var limitDay = Calendar.getInstance()
                limitDay.add(Calendar.SECOND,list[position]?.limit ?: 0)
                val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                manager.setExact(AlarmManager.RTC_WAKEUP, limitDay.timeInMillis, contentIntent)
            }
        })


        listView1.adapter = adapter
//
//        listView1.setOnItemClickListener { parent, view, position, id ->
//
//            Log.e("tag","setOnItemClickListener")
//
//
//        }

//        if (list.isEmpty()) {
//            create("contactName", 14, 3)
//        }

        addButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
            finish()
        }

        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun create(name: String, l: Int, n: Int) {
        realm.executeTransaction {
            val contact = it.createObject(Contact::class.java, UUID.randomUUID().toString())
            contact.name = name
            contact.limit = l
            contact.num = n
        }
    }


    fun readAll(): RealmResults<Contact> {
        return realm.where(Contact::class.java).findAll().sort("name", Sort.ASCENDING)
    }


    companion object {
        fun createIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }




}
