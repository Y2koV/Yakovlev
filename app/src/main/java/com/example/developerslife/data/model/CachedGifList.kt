package com.example.developerslife.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CachedGifList (
    val gifList: ArrayList<GifItem>,
    var currentPosition: Int
): Parcelable
