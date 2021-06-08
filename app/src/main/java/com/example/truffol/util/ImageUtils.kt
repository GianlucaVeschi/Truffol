package com.example.truffol.util

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.truffol.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.bumptech.glide.request.RequestOptions


const val DEFAULT_FOOD_IMAGE = R.drawable.empty_plate
const val DEFAULT_SHOP_IMAGE = R.drawable.shop_placeholder_1240_698

@ExperimentalCoroutinesApi
@Composable
fun loadPicture(url: String, @DrawableRes defaultImage: Int): MutableState<Bitmap?> {

    val bitmapState: MutableState<Bitmap?> = remember { mutableStateOf(null)}
    val options = RequestOptions().centerCrop()

    // show default image while image loads
    GlideApp.with(LocalContext.current)
        .asBitmap()
        .load(defaultImage)
        .apply(options)
        .into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) {}
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?
            ) {
                bitmapState.value = resource
            }
        })

    // get network image
    GlideApp.with(LocalContext.current)
        .asBitmap()
        .load(url)
        .apply(options)
        .into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) {}
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?
            ) {
                bitmapState.value = resource
            }
        })

    return bitmapState
}
