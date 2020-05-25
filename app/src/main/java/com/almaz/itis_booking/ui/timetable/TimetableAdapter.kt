package com.almaz.itis_booking.ui.timetable

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.almaz.itis_booking.R
import com.almaz.itis_booking.core.model.Business
import com.almaz.itis_booking.core.model.Cabinet
import com.almaz.itis_booking.core.model.Status
import com.almaz.itis_booking.core.model.Time
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_timetable.view.*

class TimetableAdapter(
    private val cabinetLambda: (Cabinet) -> Unit
) : ListAdapter<Cabinet, TimetableAdapter.TimetableViewHolder>(TimetableDiffCallback()) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TimetableViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        return TimetableViewHolder(inflater.inflate(R.layout.item_timetable, p0, false))
    }

    override fun onBindViewHolder(holder: TimetableViewHolder, position: Int) {
        for (elt in getItem(position).business) {
            holder.bind(getItem(position), elt)
            holder.itemView.setOnClickListener {
                cabinetLambda.invoke(getItem(position))
            }
        }
    }

    class TimetableViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(cabinet: Cabinet, business: Business) {
            itemView.tv_cabinet_number.text = cabinet.number
            itemView.tv_cabinet_capacity_value.text = cabinet.capacity
            itemView.tv_cabinet_status_addition.text = cabinet.statusAddition
            when (business.status) {
                Status.Free -> {
                    itemView.tv_cabinet_status.text = "Свободно"
                    itemView.tv_cabinet_status.background =
                        containerView.resources.getDrawable(R.drawable.map_cabinet_free_background, null)
                }
                Status.Booked -> {
                    itemView.tv_cabinet_status.text = "Забронировано"
                    itemView.tv_cabinet_status.background =
                        containerView.resources.getDrawable(R.drawable.map_cabinet_booked_background, null)
                }
            }
        }
    }

    class TimetableDiffCallback : DiffUtil.ItemCallback<Cabinet>() {
        override fun areItemsTheSame(oldItem: Cabinet, newItem: Cabinet): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Cabinet, newItem: Cabinet): Boolean = oldItem == newItem
    }
}
