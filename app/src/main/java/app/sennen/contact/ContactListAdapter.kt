package app.sennen.contact

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.RealmBaseAdapter
import kotlinx.android.synthetic.main.custom_list_layout.view.*
import java.util.*


class ContactListAdapter(val context: Context, val contactList: OrderedRealmCollection<Contact>) :
    RealmBaseAdapter<Contact>(contactList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.custom_list_layout, parent, false)
        view.listText3.text = contactList[position].limit.toString() + "日"
        view.listText2.text = contactList[position].num.toString() + "個"
        view.listText1.text = contactList[position].name

        return view
    }

    override fun getCount(): Int {
        return contactList.size
    }


    interface Click {
        fun onclick(position: Int)
    }





}