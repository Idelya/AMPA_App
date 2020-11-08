package com.example.bmi_calculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bmi_calculator.databinding.ActivityResultDescribeBinding
import kotlin.math.round

class ResultDescribeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultDescribeBinding
    private var bmiValue: Double = 0.0
    private lateinit var resultCategory: BmiResults

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultDescribeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        bmiValue = intent.getDoubleExtra("bmi_value", 0.0)
        resultCategory = BmiCalc().getResultStatus(bmiValue)

        binding.apply {
            resultBmiTV.text = (round(bmiValue*100) *0.01).toString()
            resultDescribeTV.text = getString(resultCategory.getDescribe())
            resultCategoryTV.text = resultCategory.name
        }
    }
}