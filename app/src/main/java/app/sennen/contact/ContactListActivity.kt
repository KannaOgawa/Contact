package app.sennen.contact

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_contact_list.*
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
            createDummyData()
        }


        addButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }


        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
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

    fun createDummyData() {
        for (i in 0..3) {
            create("名前 $i",i,i)
        }
    }

    fun readAll(): RealmResults<Contact>{
        return realm.where(Contact::class.java).findAll().sort("name", Sort.ASCENDING)
    }

}
