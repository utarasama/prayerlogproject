package com.example.prayerlog

import androidx.annotation.StringRes

/**
 * Represents a prayer.
 * @property nameId The prayer's name ID stored in `strings.xml`.
 * @property amountToPray The amount of prayers to make up in total.
 * @property amountPrayed The amount of prayers already made up, based on amountToPray. 0 by default.
 *
 * @sample Prayer(R.string.fajr_prayer_text, 500, 5)
 * // That means that you made up 5 fajr out of 500
 */
data class Prayer(
    @StringRes val nameId: Int,
    val amountToPray: Int,
    val amountPrayed: Int = 0
)
