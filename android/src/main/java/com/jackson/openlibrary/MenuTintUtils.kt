package com.jackson.openlibrary

import android.graphics.PorterDuff
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.DrawableCompat
import com.jackson.openlibrary.R.id

fun Menu.tintAllIcons(color: Int) {
    for (i in 0 until size()) {
        val item = getItem(i)
        tintMenuItemIcon(color, item!!)
        tintShareIconIfPresent(color, item)
    }
}

private fun tintMenuItemIcon(color: Int, item: MenuItem) {
    val drawable = item.icon
    if (drawable != null) {
        val wrapped = DrawableCompat.wrap(drawable)
        drawable.mutate()
        DrawableCompat.setTintMode(wrapped, PorterDuff.Mode.SRC_ATOP)
        DrawableCompat.setTint(wrapped!!, color)
        item.icon = drawable
    }
}

private fun tintShareIconIfPresent(color: Int, item: MenuItem) {
    if (item.actionView != null) {
        val actionView = item.actionView
        val expandActivitiesButton = actionView.findViewById<View>(id.expand_activities_button)
        if (expandActivitiesButton != null) {
            val image = expandActivitiesButton.findViewById<View>(id.image) as ImageView
            if (image != null) {
                val drawable = image.drawable
                val wrapped = DrawableCompat.wrap(drawable!!)
                drawable.mutate()
                DrawableCompat.setTint(wrapped!!, color)
                image.setImageDrawable(drawable)
            }
        }
    }
}
