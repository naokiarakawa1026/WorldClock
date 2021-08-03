package com.example.worldclock

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextClock
import android.widget.TextView
import java.util.*

// BaseAdapterを継承させる必要がある
class TimeZoneAdapter
    ( private val context: Context,
      private val timeZones: Array<String> = TimeZone.getAvailableIDs()) : BaseAdapter() {

    private val inflater = LayoutInflater.from(context)

    override fun getCount() = timeZones.size

    override fun getItem(position: Int) = timeZones[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: createView(parent)
        val timeZoneId = getItem(position)
        val timeZone = TimeZone.getTimeZone(timeZoneId)
        val viewHolder = view.tag as ViewHolder
        viewHolder.name.text = "${timeZone.displayName} (${timeZone.id})"
        viewHolder.clock.timeZone = timeZone.id
        return view
    }

    private class ViewHolder(view: View) {
        val name = view.findViewById<TextView>(R.id.timeZone)
        val clock = view.findViewById<TextClock>(R.id.clock)
    }

    private fun createView(parent: ViewGroup?) : View {
        val view = inflater.inflate(R.layout.list_time_zone_row, parent, false)
        view.tag = ViewHolder(view)
        return view
    }
}