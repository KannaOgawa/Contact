package app.sennen.contact
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter
import kotlinx.android.synthetic.main.custom_list_layout2.view.*
import java.util.*

class MainListAdapter(val context: Context,val contactList: OrderedRealmCollection<Contact>) :
    RealmBaseAdapter<Contact>(contactList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.custom_list_layout2, parent, false)
            view.listText2_1.text = contactList[position].name
            view.listText2_2.text = contactList[position].limit.toString()
            view.progressBar2.progress = showProgress(position)

        return view
    }

    override fun getCount(): Int {
        return contactList.size
    }
    fun diffDays(calendar1: Long): Int {

        val now = Calendar.getInstance ()
        //==== ミリ秒単位での差分算出 ====//
        val diffTime = now.timeInMillis-calendar1
        //==== 日単位に変換 ====//
        val MILLIS_OF_DAY = 1000 * 60 * 60 * 24
        return (diffTime / MILLIS_OF_DAY).toInt()
    }

    fun showProgress(position: Int) :Int{

        var tmp:Int = diffDays(contactList[position].openDate)//開封日から現在の経過
        var diff=contactList[position].limit-tmp//残り
        var castd= diff.toDouble()
        var percent =( castd /(contactList[position].limit))*100

        return  percent.toInt()
    }


}