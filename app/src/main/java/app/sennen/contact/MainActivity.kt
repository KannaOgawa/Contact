package app.sennen.contact

import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {
    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }
    var openContactList = readOpen()

    override fun onResume() {
        super.onResume()
        updateScreen()
    }

    fun readOpen(): RealmResults<Contact> {
        calc()
        return realm.where(Contact::class.java).greaterThan("openDate", 0.toInt())
            .findAll().sort("diff", Sort.ASCENDING)
    }


    fun calc() {
        var resultArray = realm.where(Contact::class.java).greaterThan("openDate", 0.toInt())
            .findAll().sort("openDate", Sort.ASCENDING)
        for (result in resultArray) {

            realm.executeTransaction {
                result.diff = result.limit-diffNow(result.openDate)//残り
                if(result.diff<=0){
                    result.openDate=0
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var mainAdapter = MainListAdapter(this, openContactList)
        mainlist.adapter = mainAdapter

//        settingButton.setOnClickListener {
//            delete(openContactList[0]!!)
//        }

        listButton.setOnClickListener {
            val intent = Intent(this, ContactListActivity::class.java)
            startActivity(intent)
        }

        mainlist.setOnItemClickListener { parent, view, position, id ->

            //view.visibility= View.INVISIBLE

        }
    }

    fun diffNow(calendar1: Long): Int {
        val now = Calendar.getInstance()
        //==== ミリ秒単位での差分算出 ====//
        val diffTime = now.timeInMillis-calendar1
        //==== 日単位に変換 ====//
        val MILLIS_OF_DAY: Long = 1000 * 60 * 60 * 24
        return (diffTime / MILLIS_OF_DAY).toInt()
    }


    fun diffDayCal(calendar1: Long, calendar2: Calendar): Int {
        //==== ミリ秒単位での差分算出 ====//
        val diffTime = calendar2.timeInMillis - calendar1
        //==== 日単位に変換 ====//
        val MILLIS_OF_DAY: Long = 1000 *60 * 60 * 24
        return (diffTime / MILLIS_OF_DAY).toInt()
    }

    fun updateScreen() {
        calc()
        var mainContact = realm.where(Contact::class.java)
            .greaterThan("openDate", 0.toInt()).sort("diff", Sort.ASCENDING).findFirst()
        if (mainContact != null) {
            var d = mainContact.diff
            var castd = d.toDouble()
            var percent = (castd / (mainContact.limit)) * 100

            progressBar.setProgress(percent.toInt())
            limitTextView.text = d.toString()
            nameTextView.text = mainContact.name

        }
    }


}
