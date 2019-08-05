package com.willowtreeapps.common

import com.squareup.sqldelight.db.SqlDriver
import com.willowtree.common.LibraryDatabase

fun createDatabase(driver: SqlDriver): LibraryDatabase {
    return LibraryDatabase(driver)
}