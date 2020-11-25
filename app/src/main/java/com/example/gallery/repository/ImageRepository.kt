package com.example.gallery.repository

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImageRepository(private val context: Context) {

    private var images: List<Pair<Int, String>>? = null

    suspend fun getImages(): List<Pair<Int, String>>? = withContext(Dispatchers.IO) {
        if (images == null) {
            images = context.assets.list("imgs")?.mapIndexed { index, string ->
                index to "file:///android_asset/imgs/${string}"
            }
        }
        images
    }

    fun getNextImage(index: Int): Pair<Int, String>? {
        images?.let {
            if (index < it.size - 1) {
                return it[index + 1]
            }
        }
        return null
    }

    fun getPreviousImage(index: Int): Pair<Int, String>? {
        images?.let {
            if (index > 0) {
                return it[index - 1]
            }
        }
        return null
    }

}