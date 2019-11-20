package app.sennen.contact

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {

    var name : String ="name"
    var num : Int =0
    var limit : Int =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        button.setOnClickListener {
            if(nameEditText.text != null){
                // 取得したテキストを TextView に張り付ける
                name = nameEditText.text.toString()
            }

            if(nameEditText.text != null){
                // 取得したテキストを TextView に張り付ける
                num = Integer.parseInt(numEditText.text.toString())
            }
            if(numEditText.text != null){
                // 取得したテキストを TextView に張り付ける
                limit =  Integer.parseInt(limitEditText.text.toString())
            }
            val intent = Intent(this,ContactListActivity::class.java)
            intent.putExtra("name",name)
            intent.putExtra("num",num)
            intent.putExtra("limit",limit)
            startActivity(intent)

            Log.e("showItem1 name",name)
            Log.e("showItem1 num",num.toString())
            Log.e("showItem1 limit",limit.toString())

        }
    }
}
