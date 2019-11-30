package app.sennen.contact

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_add.*
import java.util.*

class AddActivity : AppCompatActivity() {

    var name : String ="name"
    var num : Int =0
    var limit : Int =0

    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        button.setOnClickListener {

            val intent = Intent(this,ContactListActivity::class.java)
            realm.executeTransaction {

                val contact = it.createObject(Contact::class.java, UUID.randomUUID().toString())
                if(nameEditText.text != null){
                    contact.name = nameEditText.text.toString()
                }
                if(nameEditText.text != null){
                    contact.num = Integer.parseInt(numEditText.text.toString())
                }
                if(limitEditText.text != null){
                    contact.limit =  Integer.parseInt(limitEditText.text.toString())
                }
            }
            startActivity(intent)
        }
    }
}
