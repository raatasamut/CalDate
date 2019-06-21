package com.arsoft.caldatelib

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.calen_date_main_view.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

fun Calendar.ymd(): Calendar {
    this.apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }
    return this
}

class CalenDate(val callback: OnSelected) : Fragment() {

    interface PreviousButton {
        fun IBView(ib: ImageButton)
    }

    interface NextButton {
        fun IBView(ib: ImageButton)
    }

    interface TextTitle {
        fun TVView(tv: TextView)
    }

    interface DateCell {
        fun DCView(card: CardView, date: TextView, day: TextView)
    }

    var pastSelectAble = true
    var useBuddhistYear = false

    var previousButton: PreviousButton? = null
    var nextButton: NextButton? = null
    var titleView: TextTitle? = null
    var dateCell: DateCell? = null
    var inActiveDateCell: DateCell? = null
    var disabledDateCell: DateCell? = null

    companion object {
        var calDateDateFormat = SimpleDateFormat("EE")
        var calDateCalFormat = SimpleDateFormat("dd MMM yyyy")
    }

    interface OnSelected {
        fun selected(data: DateItemModel)
    }

    lateinit var rootView: View

    var selectCal = Calendar.getInstance(Locale.getDefault())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.calen_date_main_view, null)

        setCustom()
        setButton()
        setDate()
        setMonth()

        return rootView
    }

    private fun setCustom() {
        previousButton?.IBView(rootView.calen_date_main_view_previous_month)
        nextButton?.IBView(rootView.calen_date_main_view_next_month)
        titleView?.TVView(rootView.calen_date_main_view_month)
    }

    private fun setDate() {
        if (pastSelectAble) {
            val cp = Calendar.getInstance(Locale.getDefault())
            if (cp.get(Calendar.YEAR) == selectCal.get(Calendar.YEAR) &&
                cp.get(Calendar.MONTH) == selectCal.get(Calendar.MONTH)
            ) {
                if (cp.get(Calendar.DATE) > selectCal.get(Calendar.DATE)) {
                    selectCal = Calendar.getInstance(Locale.getDefault())
                }
                rootView.calen_date_main_view_previous_month.visibility = View.INVISIBLE
            } else
                rootView.calen_date_main_view_previous_month.visibility = View.VISIBLE
        }

        val dayCount = selectCal.getActualMaximum(Calendar.DAY_OF_MONTH)
//        Log.d("DAYS", dayCount.toString())

        rootView.calen_date_main_view_rv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = DateAdapter(object : OnSelected {
                override fun selected(data: DateItemModel) {
                    selectCal.set(Calendar.DATE, data.value)
                    setMonth()
                }
            }, ArrayList<DateItemModel>().apply {
                for (i in 1..dayCount) {
                    add(
                        DateItemModel(
                            i == selectCal.get(Calendar.DATE),
                            i,
                            Calendar.getInstance().apply {
                                set(
                                    selectCal.get(Calendar.YEAR),
                                    selectCal.get(Calendar.MONTH),
                                    i,
                                    0,
                                    0,
                                    0
                                )
                            }.timeInMillis
                        )
                    )
                }
            }, dateCell, inActiveDateCell, disabledDateCell, pastSelectAble)
        }

        rootView.calen_date_main_view_rv.scrollToPosition(selectCal.get(Calendar.DATE) - 1)
    }

    private fun setButton() {
        rootView.calen_date_main_view_previous_month.setOnClickListener {
            val m = selectCal.get(Calendar.MONTH)
            when (m) {
                0 -> {
                    selectCal.add(Calendar.YEAR, -1)
                    selectCal.set(Calendar.MONTH, 11)
                    selectCal.set(Calendar.DATE, 1)
                }
                else -> {
                    selectCal.add(Calendar.MONTH, -1)
                    selectCal.set(Calendar.DATE, 1)
                }
            }
            setDate()
            setMonth()
        }
        rootView.calen_date_main_view_next_month.setOnClickListener {
            val m = selectCal.get(Calendar.MONTH)
            when (m) {
                11 -> {
                    selectCal.add(Calendar.YEAR, 1)
                    selectCal.set(Calendar.MONTH, 0)
                    selectCal.set(Calendar.DATE, 1)
                }
                else -> {
                    selectCal.add(Calendar.MONTH, 1)
                    selectCal.set(Calendar.DATE, 1)
                }
            }
            setDate()
            setMonth()
        }
    }

    fun setMonth() {
        if (useBuddhistYear) {
            val tmp = Calendar.getInstance()
            tmp.set(Calendar.YEAR, selectCal.get(Calendar.YEAR) + 543)
            tmp.set(Calendar.MONTH, selectCal.get(Calendar.MONTH))
            tmp.set(Calendar.DATE, selectCal.get(Calendar.DATE))
            rootView.calen_date_main_view_month.text = calDateCalFormat.format(tmp.time)
        } else
            rootView.calen_date_main_view_month.text = calDateCalFormat.format(selectCal.time)

        callback.selected(DateItemModel(true, selectCal.get(Calendar.DATE), selectCal.ymd().timeInMillis))
    }
}