package com.andrewkingmarshall.beachbuddy

import android.app.Application
import com.andrewkingmarshall.beachbuddy.database.initRealm
import com.andrewkingmarshall.beachbuddy.inject.Injector
import net.danlew.android.joda.JodaTimeAndroid
import timber.log.Timber

class BeachBuddyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        JodaTimeAndroid.init(this)

        setUpDagger()

        setUpLogging()

        setUpRealm()

        setUpFirebase()
    }

    private fun setUpDagger() {
        Injector.init(this)
    }

    private fun setUpLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setUpRealm() {
        initRealm(this)
    }

    private fun setUpFirebase() {
        // todo
//        FirebaseApp.initializeApp(this)
    }
}