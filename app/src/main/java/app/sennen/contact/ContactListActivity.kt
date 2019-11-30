package app.sennen.contact

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_contact_list.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class ContactListActivity : AppCompatActivity() {
    //val list = arrayListOf<Contact>(Contact( name = "name", limit = 1, num = 1))

    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }
    val taskList = readAll()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_list)

        var adapter = ContactListAdapter(this, taskList)
        listView1.adapter = adapter



        if (taskList.isEmpty()) {
            create("名前",100,100)
        }
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

    fun create(name: String,l:Int,n:Int) {
        realm.executeTransaction {
            val contact = it.createObject(Contact::class.java, UUID.randomUUID().toString())
            contact.name = name
            contact.limit = l
            contact.num=n
        }
    }


    fun readAll(): RealmResults<Contact>{
        return realm.where(Contact::class.java).findAll().sort("name", Sort.ASCENDING)
    }



    fun openContact(){
        val intent =BCReceiver.createIntent(this)
        val contentIntent = PendingIntent.getBroadcast(applicationContext, 1, intent, PendingIntent.FLAG_ONE_SHOT)
        var limitDay =Calendar.getInstance()
        val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            manager.setExact(AlarmManager.RTC_WAKEUP, limitDay.timeInMillis, contentIntent)
    }
}
