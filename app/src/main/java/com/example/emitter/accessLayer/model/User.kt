package com.example.emitter.accessLayer.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class User(
    val id:Int,
    val name:String,
    val username:String,
    val email:String,
    val address:Address,
    val phone:String,
    val company: Company,
) : Parcelable

@Parcelize
data class Address(
    val street:String,
    val suite:String,
    val zipcode:String,
    val geo:Geo
) : Parcelable

@Parcelize
data class Geo(
    val lat:String,
    val lng:String,
) : Parcelable

@Parcelize
data class Company(
    val name:String,
    val catchPhrase:String,
    val bs:String,
) : Parcelable

