package app.sennen.contact

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Contact(val id: Int,val name: String?,val limit :Int, val num: Int) {

}