package com.example.movapps.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cast (
    var name: String ?="",
    var photo: String ?=""
): Parcelable