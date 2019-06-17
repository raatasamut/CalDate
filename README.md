# CalDate

Install

    allprojects {
        repositories {
            maven { url 'https://jitpack.io' }
        }
    }

    // Inside using module
    implementation 'com.github.raatasamut:CalDate:v1.0.0'
    implementation 'com.google.android.material:material:1.0.0'

Usage

        val fm = CalenDate(object : CalenDate.OnSelected {
                  override fun selected(data: DateItemModel) {
                      Log.d("MainActivity", Date(data.data).toString())
                  }
                  }).apply {
                      pastSelectAble = true
                      useBuddhistYear = true

                      previousButton = object : CalenDate.PreviousButton {
                          override fun IBView(ib: ImageButton) {
                              ib.setImageDrawable(resources.getDrawable(android.R.drawable.ic_media_previous, null))
                          }
                      }
                      nextButton = object : CalenDate.NextButton {
                          override fun IBView(ib: ImageButton) {
                              ib.setImageDrawable(resources.getDrawable(android.R.drawable.ic_media_next, null))
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
