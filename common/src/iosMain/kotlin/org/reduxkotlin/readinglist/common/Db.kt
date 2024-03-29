package org.reduxkotlin.readinglist.common

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.ios.NativeSqliteDriver
import org.reduxkotlin.readinglist.common.repo.createDatabase
import kotlin.native.concurrent.AtomicReference
import kotlin.native.concurrent.freeze

object Db {
    private val driverRef = AtomicReference<SqlDriver?>(null)
    private val dbRef = AtomicReference<LibraryDatabase?>(null)

    internal fun dbSetup(driver: SqlDriver) {
        val db = createDatabase(driver)
        driverRef.value = driver.freeze()
        dbRef.value = db.freeze()
    }

    internal fun dbClear() {
        driverRef.value!!.close()
        dbRef.value = null
        driverRef.value = null
    }

    //Called from Swift
    @Suppress("unused")
    fun defaultDriver() {

        dbSetup(NativeSqliteDriver(LibraryDatabase.Schema, "library.db"))
    }

    val instance: LibraryDatabase
        get() = dbRef.value!!
}