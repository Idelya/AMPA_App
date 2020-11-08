package com.example.bmi_calculator

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import com.google.gson.Gson;
import  com.example.bmi_calculator.databinding.ActivityMainBinding
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.round

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bmiCounter: BmiCalc
    private lateinit var bmiHistory: MutableList<BmiData>
    private val HEIGHT_ET_KEY: String = "height_et_key"
    private val MASS_ET_KEY: String = "mass_et_key"
    private val LAST_BMI_KEY: String = "last_bmi_key"
    private val STATUS_COUNTED_KEY = "status_input_count"
    private val CHANGED_UNIT_KEY: String = "units_key"
    private val USER_HISTORY_KEY: String = "bmi_calculator_user_history"
    private val RESULT_ACTIVITY_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        bmiCounter = BmiCalc();

        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        val historyString = sharedPref.getString(USER_HISTORY_KEY, null)

        val type = object : TypeToken<List<BmiData?>?>() {}.type
        bmiHistory = if(historyString != null) Gson().fromJson(historyString, type) else mutableListOf()
        setContentView(binding.root)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.apply {
            outState?.run {
                putString(MASS_ET_KEY, massET.text.toString())
                putString(HEIGHT_ET_KEY, heightET.text.toString())
                putBoolean(STATUS_COUNTED_KEY, bmiCounter.lastCountStatus == InputStatus.SUCCESS)
                if(bmiCounter.lastCountStatus == InputStatus.SUCCESS) {
                    putDouble(LAST_BMI_KEY, bmiCounter.bmi)
                }
                putBoolean(CHANGED_UNIT_KEY, bmiCounter.unitsEn)
            }
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.apply {
            massET.setText(savedInstanceState?.getString(MASS_ET_KEY))
            heightET.setText(savedInstanceState?.getString(HEIGHT_ET_KEY))
            if(savedInstanceState?.getBoolean(CHANGED_UNIT_KEY)) {
                bmiCounter.changeUnits()
                changeUnitsEffect()
            }
            if(savedInstanceState?.getBoolean(STATUS_COUNTED_KEY)) {
                val lastBmi = savedInstanceState?.getDouble(LAST_BMI_KEY)
                bmiTV.text = (round(lastBmi *100)*0.01).toString()
                bmiTV.setTextColor(getColor(bmiCounter.getResultStatus(lastBmi).getColor()))
                bmiCounter.setLastCountSuccess(lastBmi)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.more -> {
                bmiCounter.changeUnits()
                changeUnitsEffect()
                true
            }
            R.id.history -> {
                seeHistory()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun changeUnitsEffect() {
        binding.apply {
            if(bmiCounter.unitsEn) {
                massUnitTV.setText(R.string.mass_unit_lb)
                heightUnitTV.setText(R.string.height_units_in)
            }
            else {
                massUnitTV.setText(R.string.mass_unit_kg)
                heightUnitTV.setText(R.string.height_units_cm)
            }
        }
    }

    private fun countEffect(countStatus: InputStatus) {
        binding.apply {
            when {
                countStatus == InputStatus.SUCCESS -> bmiTV.text = (round(bmiCounter.bmi*100)*0.01).toString()
                countStatus.toMassInput() -> massET.error = getText(countStatus.message())
                else -> heightET.error = getText(countStatus.message())
            }

            if(countStatus == InputStatus.SUCCESS || countStatus == InputStatus.PENDING) {
                bmiTV.setTextColor(getColor(bmiCounter.result.getColor()))
            }
            else {
                bmiTV.setTextColor(getColor(R.color.textPrimary))
                bmiTV.text = getText(R.string.empty_value)
            }
        }
    }

    fun count(view: View) {
        binding.apply {
            val userMass = massET.text.toString().toDoubleOrNull()
            val userHeight = heightET.text.toString().toDoubleOrNull()
            val countStatus = bmiCounter.countWithValidator(userMass, userHeight)

            if(bmiCounter.lastCountStatus == InputStatus.SUCCESS && userMass != null && userHeight != null) {

                bmiHistory.add(BmiData(userMass, userHeight, bmiCounter.bmi, getCurrentDate(), bmiCounter.unitsEn))
                if(bmiHistory.size > 10) bmiHistory.removeAt(0)
                val sharedPref = this@MainActivity.getPreferences(Context.MODE_PRIVATE) ?: return
                with (sharedPref.edit()) {
                    val historyAsString = Gson().toJson(bmiHistory)
                    putString(USER_HISTORY_KEY, historyAsString)
                    apply()
                }
            }
            countEffect(countStatus)
        }
    }

    fun resultDescribe(view: View) {
        if(bmiCounter.lastCountStatus == InputStatus.SUCCESS) {
            val resultActivity = Intent(this, ResultDescribeActivity::class.java)
            resultActivity.putExtra("bmi_value", bmiCounter.bmi);
            startActivityForResult(resultActivity, RESULT_ACTIVITY_CODE)
        }
    }

    private fun seeHistory() {
            val historyActivity = Intent(this, BmiHistoryActivity::class.java)
            val historyAsString = Gson().toJson(bmiHistory)
            historyActivity.putExtra(USER_HISTORY_KEY, historyAsString)
            startActivity(historyActivity)
    }

    private fun getCurrentDate() : String{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            var answer: String =  current.format(formatter)
            return answer
        } else {
            var date = Date()
            val formatter = SimpleDateFormat("dd-MM-yyyy")
            val answer: String = formatter.format(date)
            return answer
        }
    }
}