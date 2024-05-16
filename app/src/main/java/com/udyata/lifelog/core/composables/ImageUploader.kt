package com.udyata.lifelog.core.composables

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.udyata.lifelog.domain.state.GalleryImage
import com.udyata.lifelog.domain.state.GalleryState
import kotlin.math.max

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageUploader(
    modifier: Modifier = Modifier,
    galleryState: GalleryState,
    imageSize: Dp = 40.dp,
    imageShape: CornerBasedShape = Shapes().medium,
    spaceBetween: Dp = 12.dp,
    onAddClicked: () -> Unit,
    onImageSelect: (Uri) -> Unit,
    onImageClicked: (GalleryImage) -> Unit,
    onImageRemoveClicked: (GalleryImage) -> Unit
) {
    var selectedGalleryImage by remember { mutableStateOf<GalleryImage?>(null) }

    val multiplePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 2),
    ) { images ->
        images.forEach {
            onImageSelect(it)
        }
    }

    BoxWithConstraints(modifier = modifier) {
        val numberOfVisibleImages = remember {
            derivedStateOf {
                max(
                    a = 0,
                    b = this.maxWidth.div(spaceBetween + imageSize).toInt().minus(2)
                )
            }
        }

        val remainingImages = remember {
            derivedStateOf {
                galleryState.images.size - numberOfVisibleImages.value
            }
        }

        Row {
            ImageUploaderButton(
                imageSize = imageSize,
                imageShape = imageShape,
                onClick = {
                    onAddClicked()
                    multiplePhotoPicker.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                }
            )
            Spacer(modifier = Modifier.width(spaceBetween))
            galleryState.images.take(numberOfVisibleImages.value).forEach { galleryImage ->

                Box {
                    AsyncImage(
                        modifier = Modifier
                            .clip(imageShape)
                            .size(imageSize)
                            .clickable {
                                selectedGalleryImage = galleryImage
                                onImageClicked(galleryImage)
                            },
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(galleryImage.image)
                            .crossfade(true)
                            .build(),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Gallery Image"
                    )


                    IconButton(
                        onClick = { onImageRemoveClicked(galleryImage) },
                        modifier = Modifier
                            .padding(4.dp)
                            //.size(18.dp)
                            .clip(CircleShape)
                            .size(25.dp)
                            .background(Color.LightGray)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Remove Icon",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
                AnimatedVisibility(
                    visible = selectedGalleryImage != null,
                    enter = scaleIn(
                        animationSpec = tween(durationMillis = 300),
                        // Starting smaller and growing to full size; adjust as needed
                        initialScale = 0.8f
                    ),
                    exit = scaleOut(
                        animationSpec = tween(durationMillis = 300),
                        // Shrinking to a smaller scale; adjust as needed
                        targetScale = 0.8f
                    )
                ) {
                    Dialog(onDismissRequest = { selectedGalleryImage = null }) {
                        if (selectedGalleryImage != null) {
                            ZoomableImage(
                                selectedGalleryImage = selectedGalleryImage!!,
                                onCloseClicked = { selectedGalleryImage = null },
                                onDeleteClicked = {
                                    if (selectedGalleryImage != null) {
                                        onImageRemoveClicked(selectedGalleryImage!!)
                                        selectedGalleryImage = null
                                    }
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(spaceBetween))
            }
            if (remainingImages.value > 0) {
                UploaderLastImageOverlay(
                    imageSize = imageSize,
                    imageShape = imageShape,
                    remainingImages = remainingImages.value
                )
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun ImageUploaderButton(
    imageSize: Dp,
    imageShape: CornerBasedShape,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .size(imageSize)
            .clip(shape = imageShape),
        onClick = onClick,
        tonalElevation = 2.dp,
        contentColor = Color.Black
    ) {
        Box(
            modifier = androidx.compose.ui.Modifier
                .background(MaterialTheme.colorScheme.primary.copy(.7f))
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Icon",
            )
        }
    }
}

@Composable
fun UploaderLastImageOverlay(
    imageSize: Dp,
    imageShape: CornerBasedShape,
    remainingImages: Int
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .clip(imageShape)
                .size(imageSize),
            contentColor = MaterialTheme.colorScheme.primary.copy(.7f)
        ) {}
        Text(
            text = "+$remainingImages",
            style = TextStyle(
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                fontWeight = FontWeight.Medium
            ),
            color = Color.Black
        )
    }
}

@Composable
fun ZoomableImage(
    selectedGalleryImage: GalleryImage,
    onCloseClicked: () -> Unit,
    onDeleteClicked: () -> Unit
) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }
    var scale by remember { mutableFloatStateOf(1f) }
    Box(
        modifier = Modifier
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale = maxOf(1f, minOf(scale * zoom, 5f))
                    val maxX = (size.width * (scale - 1)) / 2
                    val minX = -maxX
                    offsetX = maxOf(minX, minOf(maxX, offsetX + pan.x))
                    val maxY = (size.height * (scale - 1)) / 2
                    val minY = -maxY
                    offsetY = maxOf(minY, minOf(maxY, offsetY + pan.y))
                }
            }
    ) {

        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = maxOf(.5f, minOf(3f, scale)),
                    scaleY = maxOf(.5f, minOf(3f, scale)),
                    translationX = offsetX,
                    translationY = offsetY
                ),
            model = ImageRequest.Builder(LocalContext.current)
                .data(selectedGalleryImage.image.toString())
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Fit,
            contentDescription = "Gallery Image"
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = onCloseClicked) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close Icon")
                Text(text = "Close")
            }
            Button(onClick = onDeleteClicked) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Icon")
                Text(text = "Delete")
            }
        }
    }
}
