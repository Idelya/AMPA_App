package com.example.bmi_calculator

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import kotlin.math.round

class HistoryAdapter(private val bmiHistory: List<BmiData>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(private val listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val massTextView: TextView = itemView.findViewById<TextView>(R.id.massTV)
        val heightTextView: TextView = itemView.findViewById<TextView>(R.id.heightTV)
        val massUnitTextView: TextView = itemView.findViewById<TextView>(R.id.massUnitTV)
        val heightUnitTextView: TextView = itemView.findViewById<TextView>(R.id.heightUnitTV)
        val bmiTextView: TextView = itemView.findViewById<TextView>(R.id.bmiValueTV)
        val dateTextView: TextView = itemView.findViewById<TextView>(R.id.dateTV)
        val categoryTextView: TextView = itemView.findViewById<TextView>(R.id.bmiResCategoryTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): HistoryAdapter.HistoryViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.bmi_history_item, parent, false)
        return HistoryViewHolder(contactView)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val measurement = bmiHistory[position];
        holder.massTextView.text = measurement.mass.toString()
        holder.heightTextView.text = measurement.height.toString()
        holder.bmiTextView.text = (round(measurement.bmi *100) *0.01).toString()
        holder.dateTextView.text = measurement.date

        if(measurement.units_en) {
            holder.massUnitTextView.setText(R.string.mass_unit_lb)
            holder.heightUnitTextView.setText(R.string.height_units_in)
        }

        val category = BmiCalc().getResultStatus(measurement.bmi)
        holder.categoryTextView.text = category.name
    }

    override fun getItemCount() = bmiHistory.size
}
