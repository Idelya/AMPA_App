package com.example.bmi_calculator

import java.time.LocalDate

data class BmiData(val mass: Double, val height: Double, val bmi: Double, val date:
String, val units_en: Boolean = false)