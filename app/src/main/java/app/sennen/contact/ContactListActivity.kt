package app.sennen.contact

import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_contact_list.*
import java.util.*


class ContactListActivity : AppCompatActivity() {
    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }
    val list = readAll()

    inline fun <reified Contact : RealmObject> Realm.getAutoIncrementKey(): Int {
        if (where(Contact::class.java).count() == 0L) return 1
        else return where(Contact::class.java).max("id")?.toInt()?.plus(1)!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_list)

        var adapter = ContactListAdapter(this, list)


        listView1.adapter = adapter

        listView1.setOnItemClickListener { parent, view, position, id ->
            open(position)
        }

        if(list.isEmpty()){
            create("Tap here to open new one",1,1)
            create("LongTap here to delete",1,1)

        }




        listView1.setOnItemLongClickListener { parent, view, position, id ->

            // 一番下の項目以外は長押しで削除
            if (position == null) {
                return@setOnItemLongClickListener false
            } else {
                AlertDialog.Builder(this)
                    .setTitle("コンタクトを編集")
                    .setMessage("削除しますか？")
                    .setPositiveButton("削除",
                        DialogInterface.OnClickListener { dialog, which ->
                            delete(list[position]!!)
                        })
                    .setNegativeButton("キャンセル", null)
                    .show()



                return@setOnItemLongClickListener true
            }

        }



        addButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    fun delete(contact: Contact) {
        realm.executeTransaction {
            contact.deleteFromRealm()
        }
    }

    fun create(name: String, l: Int, n: Int) {
        realm.executeTransaction {
            val contact = it.createObject(Contact::class.java)
            contact.id= realm.getAutoIncrementKey<Contact>()
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


    fun open(position: Int) {

        if (list[position]?.openDate == 0.toLong()) {

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
            //lister.onclick(position)
            AlertDialog.Builder(this)
                .setTitle("コンタクト開封")
                .setMessage("使用開始しますか？")
                .setPositiveButton("OK") { dialog, which ->

                    var realm = Realm.getDefaultInstance()
                    val now = Calendar.getInstance()
                    var count: Long = realm.where(Contact::class.java).count()
                    val intent = BCReceiver.createIntent(this)
                    val contentIntent = PendingIntent.getBroadcast(
                        this,
                        list[position]!!.id,
                        intent,
                        PendingIntent.FLAG_ONE_SHOT
                    )
                    var limitDay = Calendar.getInstance()
                    limitDay.add(Calendar.DATE, list[position]?.limit ?: 0)
                    val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    manager.setExact(AlarmManager.RTC_WAKEUP, limitDay.timeInMillis, contentIntent)

                    realm.executeTransaction {
                        list[position]?.openDate = now.timeInMillis
                        list[position]!!.num--
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        } else {

            Log.e("tag", list[position]?.openDate.toString())
            Toast.makeText(this, "すでに使用中のコンタクトがあります", Toast.LENGTH_SHORT).show()
        }

    }
}
