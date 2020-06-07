package com.example.medicinediary.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "illness_medicine_table")
data class IllnessMedicine (

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "illness_id")
    var illnessId : Long = 0L,

    @ColumnInfo(name = "illness_name")
    var illness : String = "",

    @ColumnInfo(name = "medicine_name")
    var medicine : String = "",

    @ColumnInfo(name = "expiry_date")
    var expiryDate : String = "",

    @ColumnInfo(name = "photo_id")
    var photoId : Int = -1,

    @ColumnInfo(name = "prescription_id")
    var prescriptionId : Int = -1
) : Parcelable