package com.arsoft.caldatelib

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arsoft.caldatelib.CalenDate.Companion.calDateDateFormat
import kotlinx.android.synthetic.main.calen_date_main_view_date_item.view.*
import java.util.*

data class DateItemModel(
    var selected: Boolean = false,
    var value: Int = 1,
    var data: Long = 0
)

class DateAdapter(
    val callback: CalenDate.OnSelected,
    private val dateList: ArrayList<DateItemModel>,
    val viewCallback: CalenDate.DateCell?,
    val inActiveViewCallback: CalenDate.DateCell?,
    val disabledDateCell: CalenDate.DateCell?,
    val isFromToday: Boolean
) :
    RecyclerView.Adapter<DateAdapter.DateAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateAdapterViewHolder {
        return DateAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.calen_date_main_view_date_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = dateList.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: DateAdapterViewHolder, position: Int) {

        dateList[position].let {
            holder.v.calen_date_main_view_date_item_cv.setCardBackgroundColor(
                Color.parseColor(
                    if (it.selected) "#6ca5d6" else "#FFFFFF"
                )
            )
            holder.v.calen_date_main_view_date_item_tv.text = it.value.toString()
            holder.v.calen_date_main_view_date_item_tv_date.text = calDateDateFormat.format(Date(it.data))

            val cp = Calendar.getInstance()
            cp.add(Calendar.DATE, -1)

            if (isFromToday && cp.time.after(Date(it.data))) {
                disabledDateCell?.DCView(
                    holder.v.calen_date_main_view_date_item_cv.apply {
                        tag = it
                    },
                    holder.v.calen_date_main_view_date_item_tv,
                    holder.v.calen_date_main_view_date_item_tv_date
                )
            } else {
                holder.v.calen_date_main_view_date_item_cv.setOnClickListener { _: View? ->
                    refreshView(it)
                }

                (if (it.selected) viewCallback else inActiveViewCallback)?.DCView(
                    holder.v.calen_date_main_view_date_item_cv.apply {
                        tag = it
                    },
                    holder.v.calen_date_main_view_date_item_tv,
                    holder.v.calen_date_main_view_date_item_tv_date
                )
            }
        }
    }

    fun refreshView(item: DateItemModel) {
        dateList.forEachIndexed { index, dateItemModel ->
            if (dateItemModel.selected) {
                dateItemModel.selected = false
                notifyItemChanged(index)
            }
        }
        item.selected = true
        notifyItemChanged(dateList.indexOf(item))
        callback.selected(item)
    }

    class DateAdapterViewHolder(val v: View) : RecyclerView.ViewHolder(v)


}