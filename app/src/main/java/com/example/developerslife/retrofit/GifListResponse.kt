package com.example.developerslife.retrofit

import android.os.Parcelable
import com.example.developerslife.data.model.GifItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GifListResponse(
    val totalCount: Int,
    val result: List<GifItem>
): Parcelable