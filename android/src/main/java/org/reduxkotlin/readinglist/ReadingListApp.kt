package org.reduxkotlin.readinglist

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import com.squareup.sqldelight.android.AndroidSqliteDriver
import org.reduxkotlin.readinglist.common.LibraryApp
import org.reduxkotlin.readinglist.common.util.Logger
import org.reduxkotlin.readinglist.common.repo.createDatabase
import kotlinx.coroutines.Dispatchers
import org.reduxkotlin.readinglist.common.LibraryDatabase

class ReadingListApp : Application() {

    lateinit var libraryApp: LibraryApp

    override fun onCreate() {
        super.onCreate()
        instance = this
        val config = SupportSQLiteOpenHelper.Configuration.builder(this)
                .name("database.db")
                .callback(object : SupportSQLiteOpenHelper.Callback(1) {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        val driver = AndroidSqliteDriver(db)
                        LibraryDatabase.Schema.create(driver)
                    }

                    override fun onUpgrade(db: SupportSQLiteDatabase?, oldVersion: Int, newVersion: Int) {
                    }

                })
                .build()

        val sqlHelper = FrameworkSQLiteOpenHelperFactory().create(config)

        val navigator = AndroidNavigator()
        libraryApp = LibraryApp(navigator, Dispatchers.IO, Dispatchers.Main, createDatabase(AndroidSqliteDriver(sqlHelper)))
        registerActivityLifecycleCallbacks(navigator)
        registerActivityLifecycleCallbacks(LifeCycleLogger)
    }

    companion object {
        lateinit var instance: ReadingListApp

        fun gameEngine() = instance.libraryApp
        val dispatch
            get() = instance.libraryApp.store.dispatch
    }
}

object LifeCycleLogger : Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(activity: Activity?) {
        Logger.d(activity?.javaClass?.simpleName + ": onActivityPaused")
    }

    override fun onActivityResumed(activity: Activity?) {
        Logger.d(activity?.javaClass?.simpleName + ": onActivityResumed")
    }

    override fun onActivityStarted(activity: Activity?) {
        Logger.d(activity?.javaClass?.simpleName + ": onActivityStarted")
    }

    override fun onActivityDestroyed(activity: Activity?) {
        Logger.d(activity?.javaClass?.simpleName + ": onActivityDestroyed")
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        Logger.d(activity?.javaClass?.simpleName + ": onActivitySaveInstanceState")
    }

    override fun onActivityStopped(activity: Activity?) {
        Logger.d(activity?.javaClass?.simpleName + ": onActivityStopped")
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        Logger.d(activity?.javaClass?.simpleName + ": onActivityCreated")
    }

}