package app.sennen.contact

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_contact_list.*
import kotlinx.android.synthetic.main.activity_main.*

class ContactListActivity : AppCompatActivity() {
    val list = arrayListOf<Contact>(Contact(id = 0, name = "name", limit = 1, num = 1))

    override fun onResume() {
        super.onResume()
        var name: String = intent.getStringExtra("name")?: "Default values if not provided"
        var num = intent.getIntExtra("num", 0)
        var limit = intent.getIntExtra("limit", 0)
        var newContact: Contact = Contact(3, name, limit, num)
        list.add(newContact)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_list)

        var adapter = ContactListAdapter(this, list)
        listView1.adapter = adapter

        floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

    }
}
