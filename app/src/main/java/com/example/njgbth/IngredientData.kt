package com.example.njgbth

import android.os.Parcel
import android.os.Parcelable

data class IngredientData (
    val name: String):Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString().toString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<IngredientData> {
        override fun createFromParcel(parcel: Parcel): IngredientData {
            return IngredientData(parcel)
        }

        override fun newArray(size: Int): Array<IngredientData?> {
            return arrayOfNulls(size)
        }
    }
}
