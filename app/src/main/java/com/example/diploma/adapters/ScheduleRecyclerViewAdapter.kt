package com.example.diploma.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma.R
import com.example.diploma.models.Schedule
import com.example.diploma.models.Worker
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

// TODO: Change appropriately to Requests
// TODO: Make request class

@SuppressLint("NotifyDataSetChanged")
class ScheduleRecyclerViewAdapter : RecyclerView.Adapter<ScheduleRecyclerViewAdapter.ScheduleRecyclerViewHolder>() {
    var data: List<Schedule> = listOf(
        Schedule(1, DayOfWeek.MONDAY, LocalTime.of(16, 0), LocalTime.of(18, 0), 1, 1, 1),
        Schedule(2, DayOfWeek.TUESDAY, LocalTime.of(16, 0), LocalTime.of(18, 0), 1, 1, 1),
        Schedule(3, DayOfWeek.WEDNESDAY, LocalTime.of(16, 0), LocalTime.of(18, 0), 1, 1, 1),
        Schedule(4, DayOfWeek.THURSDAY, LocalTime.of(16, 0), LocalTime.of(18, 0), 1, 1, 1),
        Schedule(5, DayOfWeek.FRIDAY, LocalTime.of(16, 0), LocalTime.of(18, 0), 1, 1, 1),
        Schedule(6, DayOfWeek.SATURDAY, LocalTime.of(16, 0), LocalTime.of(18, 0), 1, 1, 1),
    )
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleRecyclerViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_schedule, parent, false)
        return ScheduleRecyclerViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ScheduleRecyclerViewHolder, position: Int) {
        val item = data[position]
        val currentDayOfWeek = LocalDate.now().dayOfWeek
        if (currentDayOfWeek == item.dayOfTheWeek)
            holder.itemView.apply {
                val theme = context.theme
                val typedValue = TypedValue()
                theme.resolveAttribute(android.R.attr.windowBackground, typedValue, true)
                // TODO choose color
                backgroundTintList = ColorStateList.valueOf(
                        resources.getColor( typedValue.resourceId )
                    )
            }

        holder.dayText.text = item.dayOfTheWeek.name

        val formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.getDefault())
        val localizedStartTime = formatter.format(item.startTime)
        val localizedEndTime = formatter.format(item.endTime)
        holder.timeText.text = "$localizedStartTime - $localizedEndTime"
    }

    override fun getItemCount(): Int = data.size

    class ScheduleRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dayText: TextView = view.findViewById(R.id.schedule_item_day)
        val timeText: TextView = view.findViewById(R.id.schedule_item_time)
    }
}