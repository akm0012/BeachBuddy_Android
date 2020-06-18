package com.andrewkingmarshall.beachbuddy.ui

import android.app.Activity
import android.content.Context
import com.andrewkingmarshall.beachbuddy.AppSecretHeader
import com.andrewkingmarshall.beachbuddy.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaderFactory
import com.bumptech.glide.load.model.LazyHeaders
import de.hdodenhof.circleimageview.CircleImageView
import timber.log.Timber


/**
 * Loads a Profile Image into a CircleImageView.
 *
 *
 * This is optimized to load a an image that is 10% it's original size, when the smaller image is ready to show
 * it will be shown, otherwise it will show the full size image if it gets done processing first.
 *
 * @param context         Android Context.
 * @param imageUrl        The URL where the profile image is located.
 * @param circleImageView The CircleImageView we are loading the image into.
 */
fun loadCircleProfilePhoto(
    context: Context,
    imageUrl: String?,
    circleImageView: CircleImageView?
) {
    if (circleImageView == null) return

    // Load the Image with a place holder image (Added some edge case crash prevention)
    if (context is Activity) {
        if (context.isFinishing || context.isDestroyed) {
            Timber.w("loadThumbnail skipped as Context was Activity and Activity was not in a usable state")
            return
        }
    }

    val glideUrl = GlideUrl(
        imageUrl, LazyHeaders.Builder()
            .addHeader("AppToken", AppSecretHeader)
            .build()
    )

    Glide.with(context)
        .load(glideUrl) // Must call this when using Glide with the CircleImageView library
        .dontAnimate()
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .thumbnail(0.1f) // 10%
        .circleCrop()
        .error(R.color.cancel_gray)
        .placeholder(R.color.cancel_gray)
        .into(circleImageView)
}