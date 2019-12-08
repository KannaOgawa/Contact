package app.sennen.contact
import io.realm.RealmObject


open class Contact(
    open var id: Int=0,
    open var name: String?="",
    open var limit: Int=0,
    open var num: Int=0,
    open var openDate: Long=0,
    open var diff: Int=0


) : RealmObject()



//    inline fun <reified Contact : RealmObject> Realm.getAutoIncrementKey(): Int {
//        if (where(Contact::class.java).count() == 0L) return 1
//        else return where(Contact::class.java).max("id")?.toInt()?.plus(1)!!
//    }

