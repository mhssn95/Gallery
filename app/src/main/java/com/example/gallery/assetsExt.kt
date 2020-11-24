package com.example.gallery

import android.content.Context
import android.net.Uri

fun Context.getImages(): Array<String?>? {
    return assets.list("imgs")
}

fun String.getImageUri(): String {
    return "file:///android_asset/imgs/${this}"
}