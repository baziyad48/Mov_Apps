package com.example.movapps.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ticket (
    var id_ticket: String ?="",
    var title: String ?="",
    var backdrop: String ?="",
    var location: String ?="",
    var date: String ?="",
    var seat: String ?="",
    var seat_price: String ?="",
    var promo: String ?="",
    var promo_price: String ?="",
    var total: String ?="",
    var code: String ?=""
): Parcelable