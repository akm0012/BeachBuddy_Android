package com.andrewkingmarshall.beachbuddy

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.andrewkingmarshall.beachbuddy.inject.Injector
import com.andrewkingmarshall.beachbuddy.repository.FirebaseRepository
import com.andrewkingmarshall.beachbuddy.repository.RequestedItemRepository
import com.andrewkingmarshall.beachbuddy.ui.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber
import javax.inject.Inject


class MyFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var firebaseRepository: FirebaseRepository

    @Inject
    lateinit var requestedItemRepository: RequestedItemRepository

    override fun onCreate() {
        super.onCreate()

        Injector.obtain().inject(this)
    }

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Timber.d("From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        remoteMessage.data.isNotEmpty().let {
            Timber.d("Message data payload: ${remoteMessage.data}")

            // Refresh the Requested Item data
            handleNow()

            // Todo Play Seagull noise (Using Event Bus so it will only work when app is up.)

            remoteMessage.data["updateOnly"]?.let {
                Timber.d("updateOnly: $it")

                // Play a Seagull noise
                if (it == "false") {
                    val mPlayer: MediaPlayer = MediaPlayer.create(applicationContext, R.raw.seagulls)
                    mPlayer.start()
                }
            }

            remoteMessage.data["name"]?.let {
                Timber.d("name: $it")
            }

            remoteMessage.data["count"]?.let {
                Timber.d("count: $it")
            }

            remoteMessage.data["sentByUserId"]?.let {
                Timber.d("sentByUserId: $it")
            }
        }
    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        Timber.d("Refreshed token: $token")

        firebaseRepository.registerFCMToken(token)
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private fun handleNow() {
        Timber.d("Refreshing Requested Items.")

        requestedItemRepository.refreshRequestedItems()
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private fun sendNotification(messageId: String?, teamName: String?, messageBody: String, teamId: String?) {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

//        val messageTitle = teamName ?: getString(R.string.default_push_notification_title)

//        val channelId = getString(R.string.default_notification_channel_id)
        val channelId = "123"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
//            .setSmallIcon(R.drawable.ic_bee_notification)
//            .setContentTitle(messageTitle)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(
//                channelId,
//                getString(R.string.default_push_notification_channel_name),
//                NotificationManager.IMPORTANCE_DEFAULT
//            )
//            notificationManager.createNotificationChannel(channel)
//        }

        // Will only show the most recent notifications of the same ID.
        // It's currently set so we see each message. But you could change this to TeamId and it
        // would only show the last message for that team.
        val notificationId = messageId.hashCode()

        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}