package com.udyata.lifelog.presentation

import android.net.Uri

data class InsertDairyEntryState(
    val title:String="",
    val titleErr:String="",
    val content:String="",
    val contentErr:String="",
    val dateTime:String="",
    val dateTimeErr:String="",
    val location:String="",
    val locationErr:String="",
    val mood:String="",
    val moodErr:String="",
    val imageUrl: Uri?=null,
    val imageUrlErr:String="",
    val documentUrl: Uri?=null,
    val documentUrlErr:String="",
)