package com.example.movapps.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film (
    var description: String ?="",
    var genre: String ?="",
    var poster: String ?="",
    var rating: String ?="",
    var title: String ?="",
    var trailer: String ?="",
    var backdrop: String ?=""
): Parcelable