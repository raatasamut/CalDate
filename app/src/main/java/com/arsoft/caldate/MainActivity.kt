package com.arsoft.caldate

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.arsoft.caldatelib.CalenDate
import com.arsoft.caldatelib.CalenDate.Companion.calDateCalFormat
import com.arsoft.caldatelib.CalenDate.Companion.calDateDateFormat
import com.arsoft.caldatelib.DateItemModel
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calDateCalFormat = SimpleDateFormat("d MMMM yyyy", Locale("th", "TH"))
        calDateDateFormat = SimpleDateFormat("EE", Locale("th", "TH"))

        supportFragmentManager.beginTransaction().replace(
            R.id.activity_main_cal_date, CalenDate(object : CalenDate.OnSelected {
                override fun selected(data: DateItemModel) {
                    Log.d("MainActivity", Date(data.data).toString())
                }
            }).apply {
                pastSelectAble = true
                useBuddhistYear = true

                previousButton = object : CalenDate.PreviousButton {
                    override fun IBView(ib: ImageButton) {
                        ib.setImageDrawable(resources.getDrawable(R.drawable.ic_left_arrow, null))
                    }
                }
                nextButton = object : CalenDate.NextButton {
                    override fun IBView(ib: ImageButton) {
                        ib.setImageDrawable(resources.getDrawable(R.drawable.ic_right_arrow, null))
                    }
                }
                titleView = object : CalenDate.TextTitle {
                    override fun TVView(tv: TextView) {
                        tv.textSize = 18f
                    }
                }
                dateCell = object : CalenDate.DateCell {
                    override fun DCView(card: androidx.cardview.widget.CardView, date: TextView, day: TextView) {
                        card.apply {
                            setCardBackgroundColor(Color.parseColor("#74b566"))
                        }
                        date.apply {
                            setTextColor(Color.parseColor("#FFFFFF"))
                            textSize = 18f
                            typeface = Typeface.DEFAULT_BOLD
                        }
                        day.apply {
                            setTextColor(Color.parseColor("#FFFFFF"))
                            textSize = 16f
                        }
                    }
                }
                inActiveDateCell = object : CalenDate.DateCell {
                    override fun DCView(card: androidx.cardview.widget.CardView, date: TextView, day: TextView) {
                        card.apply {
                            setCardBackgroundColor(Color.parseColor("#FFFFFF"))
                        }
                        date.apply {
                            setTextColor(Color.parseColor("#000000"))
                            textSize = 18f
                            typeface = Typeface.DEFAULT_BOLD
                        }
                        day.apply {
                            setTextColor(Color.parseColor("#000000"))
                            textSize = 16f
                        }
                    }
                }
                disabledDateCell = object : CalenDate.DateCell {
                    override fun DCView(card: androidx.cardview.widget.CardView, date: TextView, day: TextView) {
                        card.apply {
                            setCardBackgroundColor(Color.parseColor("#c9d6e3"))
                        }
                        date.apply {
                            setTextColor(Color.parseColor("#50000000"))
                            textSize = 18f
                            typeface = Typeface.DEFAULT_BOLD
                        }
                        day.apply {
                            setTextColor(Color.parseColor("#50000000"))
                            textSize = 16f
                        }
                    }
                }
            }
        ).commit()

    }
}
