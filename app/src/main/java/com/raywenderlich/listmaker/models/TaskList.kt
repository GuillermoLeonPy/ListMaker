package com.raywenderlich.listmaker.models

import android.os.Parcel
import android.os.Parcelable

//Parcelable lets you break down your object into types the Intent system is already familiar with
//strings, ints, floats, Booleans, and other objects which conform to Parcelable
//With the Parcelable interface implemented, any TaskList can be passed through an Intent.
class TaskList(val name: String, val tasks: ArrayList<String> =ArrayList()) : Parcelable {
    //Reading from a Parcel so a TaskList object can be created from a passed-in Parcel
    constructor(source: Parcel) : this(
        //The constructor grabs the values from the Parcel for the title (by calling readString on the Parcel)
        //and the list of tasks (by calling createStringArrayList on the Parcel)
        //then passes them into the primary constructor using this()
        source.readString()!!,//non-null assertion operator (!!) to get the non-optional values
        source.createStringArrayList()!!//non-null assertion operator (!!) to get the non-optional values
    )

    override fun describeContents() = 0

    //called when a Parcel needs to be created from the TaskList object
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeStringList(tasks)
    }

    companion object CREATOR: Parcelable.Creator<TaskList> {
        override fun createFromParcel(source: Parcel): TaskList = TaskList(source)
        override fun newArray(size: Int): Array<TaskList?> = arrayOfNulls(size)
    }
}