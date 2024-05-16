package com.udyata.lifelog.presentation.createDiaryScren

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.udyata.lifelog.core.composables.ImageUploader
import com.udyata.lifelog.domain.state.rememberGalleryState
import com.udyata.lifelog.presentation.createDiaryScren.components.CreateDiaryScreenToolbar

@Composable
fun CreateDiaryScreen(
    onBackPressed:()->Unit
) {
    val galleryState = rememberGalleryState()

    Scaffold (
        topBar = {
            CreateDiaryScreenToolbar(
                onBackPressed = {
                    onBackPressed()
                }
            )
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            ImageUploader(
                galleryState = galleryState,
                onAddClicked = {  },
                onImageSelect = {},
                onImageClicked = {}
            ) {

            }
        }
    }
}