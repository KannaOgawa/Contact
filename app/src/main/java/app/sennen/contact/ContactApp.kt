package app.sennen.contact

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class ContactApp: Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val realmConfig = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(realmConfig)
    }

}
