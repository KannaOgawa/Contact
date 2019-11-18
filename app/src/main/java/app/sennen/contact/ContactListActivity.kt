package app.sennen.contact

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_contact_list.*

class ContactListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_list)

        val list = arrayListOf<Contact>(Contact(id=0,name="name",num=1,limit = 1),Contact(id=1,name="name",num=20,limit = 11),Contact(id=2,name="name",num=100,limit = 30))
        var adapter = ContactListAdapter(this, list)
        listView1.adapter = adapter

    }
}
