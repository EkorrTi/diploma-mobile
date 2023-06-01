package com.example.diploma.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
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
import java.time.format.TextStyle
import java.util.*

@SuppressLint("NotifyDataSetChanged")
class ScheduleRecyclerViewAdapter : RecyclerView.Adapter<ScheduleRecyclerViewAdapter.ScheduleRecyclerViewHolder>() {
    var data: List<Schedule> = listOf(
        Schedule(1, DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(18, 0), 1, 1, 1),
        Schedule(2, DayOfWeek.TUESDAY, LocalTime.of(9, 0), LocalTime.of(18, 0), 1, 1, 1),
        Schedule(3, DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(18, 0), 1, 1, 1),
        Schedule(4, DayOfWeek.THURSDAY, LocalTime.of(9, 0), LocalTime.of(18, 0), 1, 1, 1),
        Schedule(5, DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(18, 0), 1, 1, 1),
        Schedule(6, DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(18, 0), 1, 1, 1),
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
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    class ScheduleRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val expandedGroup: Group = view.findViewById(R.id.schedule_item_expand_group)
        private val dayText: TextView = view.findViewById(R.id.schedule_item_day)
        private val timeText: TextView = view.findViewById(R.id.schedule_item_time)
        private val eventCount: TextView = view.findViewById(R.id.schedule_item_event_count)
        private val eventsText: TextView = view.findViewById(R.id.schedule_item_events)

        fun bind(item: Schedule){
            // Localize dayOfWeek
            dayText.text = item.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            // Time localization
            val formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.getDefault())
            val localizedStartTime = formatter.format(item.startTime)
            val localizedEndTime = formatter.format(item.endTime)
            timeText.text = "$localizedStartTime - $localizedEndTime"
            eventCount.apply { text = resources.getString(R.string.event_count, 0) }
            eventsText.text = "No events"


            // Toggle the expansion
            itemView.setOnClickListener { expandedGroup.isGone = !expandedGroup.isGone }
            // Expand the current day
            if (LocalDate.now().dayOfWeek == item.dayOfWeek){
                eventCount.apply { text = resources.getString(R.string.event_count, 2) }
                eventsText.apply {
                    var s = resources.getString(R.string.events_list, "Aaron's birthday")
                    s += resources.getString(R.string.events_list, "Alibek's birthday")
                    text = s
                }
//                itemView.apply {
//                    val theme = context.theme
//                    val typedValue = TypedValue()
//                    theme.resolveAttribute(android.R.attr.windowBackground, typedValue, true)
//                    backgroundTintList = ColorStateList.valueOf( resources.getColor(typedValue.resourceId) )
//                }
                expandedGroup.isGone = false
            }
        }
    }
}