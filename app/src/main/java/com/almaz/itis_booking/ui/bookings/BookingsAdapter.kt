package com.almaz.itis_booking.ui.bookings

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.almaz.itis_booking.R
import com.almaz.itis_booking.core.model.Time
import com.almaz.itis_booking.core.model.remote.BusinessRemote
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_bookings.view.*

class BookingsAdapter(private val context: Context) :
    ListAdapter<BusinessRemote, BookingsAdapter.BookingsViewHolder>(BookingsDiffCallback()) {

    private var bookingsList: MutableList<BusinessRemote> = mutableListOf()

    override fun submitList(list: List<BusinessRemote>?) {
        super.submitList(list)
        if (list != null) {
            bookingsList = list.toMutableList()
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BookingsViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        return BookingsViewHolder(inflater.inflate(R.layout.item_bookings, p0, false))
    }

    override fun onBindViewHolder(holder: BookingsViewHolder, position: Int) {
        holder.bind(bookingsList[position])

        holder.itemView.btn_delete_booking.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            with(builder) {
                setTitle("Отмена бронирования")
                setMessage("Вы уверены, что хотите отменить бронь?")

                setPositiveButton("Да, отменить") { _, _ ->
                    bookingsList.removeAt(holder.adapterPosition)
                    notifyItemRemoved(holder.adapterPosition)
                }
                setNegativeButton("Нет") { _, _ ->
                }
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
            val btnPositive = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            val btnNegative = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)

            val layoutParams = btnPositive.layoutParams as LinearLayout.LayoutParams
            layoutParams.weight = 10f
            btnPositive.layoutParams = layoutParams
            btnNegative.layoutParams = layoutParams
        }
    }

    class BookingsViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(business: BusinessRemote) {
            itemView.tv_cabinet_number.text = business.cabinet.number.toString()
            itemView.tv_cabinet_date_value.text = business.date
            itemView.tv_cabinet_time_value.text = Time.valueOf(business.time).getStringTime()
        }
    }

    class BookingsDiffCallback : DiffUtil.ItemCallback<BusinessRemote>() {
        override fun areItemsTheSame(oldItem: BusinessRemote, newItem: BusinessRemote): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: BusinessRemote, newItem: BusinessRemote): Boolean =
            oldItem == newItem
    }
}
