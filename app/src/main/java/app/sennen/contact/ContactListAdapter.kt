package app.sennen.contact

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter
import kotlinx.android.synthetic.main.activity_add.view.*
import kotlinx.android.synthetic.main.custom_list_layout.view.*
import kotlinx.android.synthetic.main.custom_list_layout.view.textView
import kotlinx.android.synthetic.main.custom_list_layout.view.textView2
import kotlinx.android.synthetic.main.custom_list_layout2.view.*

class ContactListAdapter(val context: Context, val contactList: OrderedRealmCollection<Contact>) :
    RealmBaseAdapter<Contact>(contactList) {



    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.custom_list_layout, parent, false)
        view.textView2.text=contactList[position].num.toString()
        view.textView.text=contactList[position].name
        return view
    }


    override fun getCount(): Int {
        return contactList.size
    }
}