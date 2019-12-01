package app.sennen.contact

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Contact(
    @PrimaryKey open var id: String = UUID.randomUUID().toString(),
    open var name: String?="",
    open var limit: Int=0,
    open var num: Int=0,
    open var openDate: Long=0,
    open var diff: Int=0



) : RealmObject()

