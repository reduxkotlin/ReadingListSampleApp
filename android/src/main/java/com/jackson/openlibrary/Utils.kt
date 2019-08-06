package com.jackson.openlibrary

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

/*
 * Convenience function for executing lambda after animation set is finished.
 */
fun AnimatorSet.onComplete(after: () -> Unit) {
        this.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                after()
            }
        })
    }

/*
 * Convenience function for executing lambda after the image is displayed
 */
fun <T: Any> GlideRequest<T>.onComplete(after: () -> Unit): GlideRequest<T> {
    return this.listener(object : RequestListener<T> {
        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<T>?, isFirstResource: Boolean): Boolean {
            return true
        }
        override fun onResourceReady(resource: T?, model: Any?, target: Target<T>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            after()
            return false
        }

    })
}


fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
}


fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

