package com.udyata.lifelog.presentation

import android.net.Uri

sealed interface InsertDairyEntryEvent {
    data class SetTitle(val title:String):InsertDairyEntryEvent
    data class SetTitleErr(val titleErr:String):InsertDairyEntryEvent
    data class SetContent(val content:String):InsertDairyEntryEvent
    data class SetContentErr(val contentErr:String):InsertDairyEntryEvent
    data class SetDateTime(val dateTime:String):InsertDairyEntryEvent
    data class SetDateTimeErr(val dateTimeErr:String):InsertDairyEntryEvent
    data class SetLocation(val location:String):InsertDairyEntryEvent
    data class SetLocationErr(val locationErr:String):InsertDairyEntryEvent
    data class SetMood(val mood:String):InsertDairyEntryEvent
    data class SetMoodErr(val moodErr:String):InsertDairyEntryEvent
    data class SetImageUrl(val imageUrl:Uri):InsertDairyEntryEvent
    data class SetImageUrlErr(val imageUrlErr:String):InsertDairyEntryEvent
    data class SetDocumentUrl(val documentUrl:Uri):InsertDairyEntryEvent
    data class SetDocumentUrlErr(val documentUrlErr:String):InsertDairyEntryEvent
    data object Submit:InsertDairyEntryEvent
}

