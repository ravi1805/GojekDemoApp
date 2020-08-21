package com.gojek.sample.presentation.utils

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.gojek.sample.R


object AppUtils {

    const val noNetworkMsg = "Oops!! Not connected to internet."
    /**
     * @param context activity reference
     * @param imageUrl image web url
     * @param imageView reference of image view to display image
     */
    fun displayImage(context: Context, imageUrl: String, imageView: ImageView) {
        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.drawable.ic_launcher_gojek)
        requestOptions.format(DecodeFormat.PREFER_ARGB_8888)
        Glide.with(context).setDefaultRequestOptions(requestOptions).asBitmap().load(imageUrl)
            .into(object : BitmapImageViewTarget(imageView) {
                override fun setResource(resource: Bitmap?) {
                    val circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.resources, resource)
                    circularBitmapDrawable.isCircular = true
                    imageView.setImageDrawable(circularBitmapDrawable)
                }
            })


    }


}