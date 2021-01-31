package com.example.developerslife.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GifItem(
    val id: Int,
    val description: String,
    val gifURL: String,
) : Parcelable