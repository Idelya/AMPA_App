package com.example.bmi_calculator

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class BmiHistoryActivity : AppCompatActivity() {
    private val USER_HISTORY_KEY: String = "bmi_calculator_user_history"
    lateinit var history: List<BmiData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_history)

        val rvHistory = findViewById<View>(R.id.bmiRV) as RecyclerView
        val noDataTV = findViewById<View>(R.id.emptyTV)

        val type = object : TypeToken<List<BmiData?>?>() {}.type
        history = Gson().fromJson(intent.getStringExtra(USER_HISTORY_KEY), type)
        val adapter = HistoryAdapter(history)

        if (history.isEmpty()) {
            rvHistory.visibility = View.GONE;
            noDataTV.visibility = View.VISIBLE;
        }
        else {
            rvHistory.visibility = View.VISIBLE;
            noDataTV.visibility = View.GONE;
            rvHistory.adapter = adapter
            rvHistory.layoutManager = LinearLayoutManager(this)
        }
    }
}