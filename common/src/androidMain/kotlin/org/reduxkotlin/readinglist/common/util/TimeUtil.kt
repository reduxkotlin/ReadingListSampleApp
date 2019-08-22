package org.reduxkotlin.readinglist.common.util

actual object TimeUtil {
    actual fun systemTimeMs(): Long = System.currentTimeMillis()
}