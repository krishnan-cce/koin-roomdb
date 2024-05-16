package com.udyata.lifelog.domain.state

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember

@Composable
fun rememberGalleryState(): GalleryState {
    return remember { GalleryState() }
}

class GalleryState {

    val images = mutableStateListOf<GalleryImage>()
    val imagesToBeDeleted = mutableStateListOf<GalleryImage>()

    fun addImage(galleryImage: GalleryImage) {
        images.add(galleryImage)
    }

    fun removeImage(galleryImage: GalleryImage) {
        images.remove(galleryImage)
        imagesToBeDeleted.add(galleryImage)
    }
    fun removeSingleImage(remoteImagePath: Uri) {
        val removedImage = images.find { it.image == remoteImagePath }
        removedImage?.let {
            images.remove(it)
            imagesToBeDeleted.add(it)
        }
    }
    fun getSingleImage(remoteImagePath: Uri) {
        val foundedImage = images.find { it.image == remoteImagePath }
        foundedImage?.let {
            images.add(it)
        }
    }

    fun removeAllImage(){
        images.forEach {
            removeImage(it)
        }
    }
}

data class GalleryImage(
    val image: Uri,
    val remoteImagePath: String = "",
)