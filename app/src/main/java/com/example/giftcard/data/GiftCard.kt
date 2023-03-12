package com.example.giftcard.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GiftCard(
    val vendor: String = "",
    val id: String = "",
    val brand: String = "",
    val image: String = "",
    val denominations: List<Denominations>,
    val position: Int = 0,
    val discount: Double = 0.0,
    val terms: String = "",
    val importantContent: String = "",
    val cardTypeStatus: String = "",
    val customDenominations: List<CustomDenominations>? = null,
    val disclaimer:String = ""
): Parcelable

@Parcelize
data class Denominations(
    val price: Double = 0.0,
    val currency: String = "",
    val stock: String = ""
): Parcelable

@Parcelize
data class CustomDenominations(
    val minPrice: Int = 0,
    val maxPrice: Int = 0
): Parcelable

@Parcelize
data class ResultList(
    val data: List<GiftCard>
): Parcelable

@Parcelize
data class Login(
    val username: String,
    val password: String
): Parcelable
