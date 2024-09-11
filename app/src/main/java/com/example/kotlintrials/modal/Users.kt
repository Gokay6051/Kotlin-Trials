package com.example.kotlintrials.modal

import android.os.Parcel
import android.os.Parcelable

data class Users (
    val status: String? = "",
    val email: String? = "",
    val profileImageUrl: String? = "",
    val uid: String? = "",
    val userName: String? = ""
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(status)
        parcel.writeString(email)
        parcel.writeString(profileImageUrl)
        parcel.writeString(uid)
        parcel.writeString(userName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Users> {
        override fun createFromParcel(parcel: Parcel): Users {
            return Users(parcel)
        }

        override fun newArray(size: Int): Array<Users?> {
            return arrayOfNulls(size)
        }
    }

}