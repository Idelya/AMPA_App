package com.example.bmi_calculator

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.bmi_calculator.database.HistoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class BmiHistoryActivity : AppCompatActivity() {
    lateinit var history: List<BmiData>
    lateinit var adapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_history)

        val rvHistory = findViewById<View>(R.id.bmiRV) as RecyclerView
        val noDataTV = findViewById<View>(R.id.emptyTV)

        val dbDao = HistoryDatabase.getInstance(this).historyDao()

        history = emptyList()

        rvHistory.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {

            whenStarted {
                withContext(Dispatchers.IO) {
                    history = dbDao.getLast()
                }
            }

            if (history.isEmpty()) {
                rvHistory.visibility = View.GONE;
                noDataTV.visibility = View.VISIBLE;
            }
            else {
                adapter = HistoryAdapter(history)
                rvHistory.visibility = View.VISIBLE;
                noDataTV.visibility = View.GONE;
                rvHistory.adapter = adapter
            }
        }

    }
}