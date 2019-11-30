package app.sennen.contact
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter
import kotlinx.android.synthetic.main.custom_list_layout2.view.*


class MainListAdapter(val context: Context,val contactList: OrderedRealmCollection<Contact>) :
RealmBaseAdapter<Contact>(contactList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.custom_list_layout2, parent, false)
        view.listText2_1.text=contactList[position].name
        view.listText2_2.text=contactList[position].num.toString()
        view.progressBar2.progress = 50
        return view
    }


    override fun getCount(): Int {
        return contactList.size
    }
}