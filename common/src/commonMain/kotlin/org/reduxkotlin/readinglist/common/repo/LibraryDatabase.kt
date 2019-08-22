package org.reduxkotlin.readinglist.common.repo

import com.squareup.sqldelight.db.SqlDriver
import org.reduxkotlin.readinglist.common.LibraryDatabase

fun createDatabase(driver: SqlDriver): LibraryDatabase {
    return LibraryDatabase(driver)
}