package com.almaz.itis_booking.ui.timetable

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.almaz.itis_booking.R
import com.almaz.itis_booking.core.model.Cabinet
import kotlinx.android.extensions.LayoutContainer

class TimetableAdapter(
    private val cabinetLambda: (Cabinet) -> Unit
) : ListAdapter<Cabinet, TimetableAdapter.TimetableViewHolder>(NewsFeedDiffCallback()) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TimetableViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        return TimetableViewHolder(inflater.inflate(R.layout.fragment_cabinet, p0, false))
    }

    override fun onBindViewHolder(holder: TimetableViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            cabinetLambda.invoke(getItem(position))
        }
    }

    class TimetableViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {


        fun bind(cabinet: Cabinet) {

        }
    }

    class NewsFeedDiffCallback : DiffUtil.ItemCallback<Cabinet>() {
        override fun areItemsTheSame(oldItem: Cabinet, newItem: Cabinet): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Cabinet, newItem: Cabinet): Boolean = oldItem == newItem
    }
}