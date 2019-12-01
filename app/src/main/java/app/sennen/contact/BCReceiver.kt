package app.sennen.contact

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class BCReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val newIntent = ContactListActivity.createIntent(context)
        val contentIntent = PendingIntent.getActivity(context, 0, newIntent, PendingIntent.FLAG_ONE_SHOT)

        val notification = NotificationCompat.Builder(context, "default")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("ContactManager")
            .setContentText("使用中のコンタクトの期限が切れます")
            .setContentIntent(contentIntent)
            .setAutoCancel(true)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher_background))
            .build()

        val manager = NotificationManagerCompat.from(context)
        manager.notify(2, notification)
    }

    companion object {
        fun createIntent(context: Context) = Intent(context, BCReceiver::class.java)
    }
}