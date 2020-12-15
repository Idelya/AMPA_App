package com.example.bmi_calculator.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.bmi_calculator.BmiData

@Dao
interface HistoryDao {
    @Query("SELECT * FROM bmidata")
    fun getAll(): List<BmiData>


    @Query("SELECT * FROM bmidata ORDER BY bid desc limit 10")
    fun getLast(): List<BmiData>

    @Insert
    fun insertAll(vararg users: BmiData)

    @Delete
    fun delete(user: BmiData)

}