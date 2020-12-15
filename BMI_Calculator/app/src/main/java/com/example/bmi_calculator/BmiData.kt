package com.example.bmi_calculator

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BmiData(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "bid") val bid: Long = 0,
    @ColumnInfo(name = "mass") val mass: Double,
    @ColumnInfo(name = "height") val height: Double,
    @ColumnInfo(name = "bmi") val bmi: Double,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "english units") val units_en: Boolean = false
)