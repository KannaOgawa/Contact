package app.sennen.contact

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.custom_list_layout.view.*

class ContactListAdapter(val context: Context, val contactList: ArrayList<Contact>) : BaseAdapter() {
    override fun getItemId(position: Int): Long {
        return contactList[position].id.toLong()

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.custom_list_layout, parent, false)
        view.textView.text=contactList[position].name
        view.textView2.text=contactList[position].num.toString()
        return view
    }

    override fun getItem(position: Int): Any {
        return contactList[position]
    }

    override fun getCount(): Int {
        return contactList.size
    }
}