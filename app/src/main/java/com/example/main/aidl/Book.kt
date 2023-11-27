package com.example.main.aidl

import android.os.Parcelable

class Book() : Parcelable{
    var bookId: Int = 0
    var bookName: String ?= ""

    constructor(parcel: android.os.Parcel) : this() {
        bookId = parcel.readInt()
        bookName = parcel.readString()
    }

    override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {
        parcel.writeInt(bookId)
        parcel.writeString(bookName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: android.os.Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }
}