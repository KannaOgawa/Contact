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


class ContactListAdapter(val context: Context, val contactList: OrderedRealmCollection<Contact>,var lister:Click) :
    RealmBaseAdapter<Contact>(contactList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.custom_list_layout, parent, false)
        view.listText3.text = contactList[position].limit.toString() + "日"
        view.listText2.text = contactList[position].num.toString() + "個"
        view.listText1.text = contactList[position].name
        view.customListmView.setOnClickListener {
            Log.e("tag","adapterronclick")
            //lister.onclick(position)
            AlertDialog.Builder(this.context)
                    .setTitle("コンタクト開封")
                    .setMessage("使用開始しますか？")
                    .setPositiveButton("OK") { dialog, which ->
                        lister.onclick(position)
                        var realm = Realm.getDefaultInstance()
                        val now = Calendar.getInstance ()

                        realm.executeTransaction {
                            contactList[position].openDate = now.timeInMillis
                            contactList[position].num--
                        }

                    }
                    .setNegativeButton("Cancel", null)
                    .show()
        }
        return view
    }

    override fun getCount(): Int {
        return contactList.size
    }


    interface Click {
        fun onclick(position: Int)
    }





}