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
    var amountToPray: Int,
    var amountPrayed: Int = 0
) {
    /**
     * Tells if the prayer needs to be made up or not.
     * @return `true` if this prayer still needs to be made up, `false` otherwise.
     */
    fun stillNeedsToBeMadeUp(): Boolean {
        return this.amountToPray > this.amountPrayed
    }

    // All methods in this companion object are equivalent to static methods.
    companion object {
        /**
         * Calculates the sum of all amount to pray from prayers.
         * @param prayers The prayers parameters.
         * @return The sum described above.
         */
        fun sumAmountToPray(vararg prayers: Prayer): Int {
            return prayers.sumOf { it.amountToPray }
        }

        /**
         * Calculates the sum of all amount prayed from prayers.
         * @param prayers The prayers parameters.
         * @return The sum described above.
         */
        fun sumAmountPrayed(vararg prayers: Prayer): Int {
            return prayers.sumOf { it.amountPrayed }
        }
    }
}
