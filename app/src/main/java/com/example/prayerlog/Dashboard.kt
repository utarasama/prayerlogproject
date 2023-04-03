package com.example.prayerlog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.prayerlog.ui.theme.CircularIndicator

@Composable
fun DashboardScreen(fajrAmount: Int, dohrAmount: Int, asrAmount: Int, maghrebAmount: Int, ishaAmount: Int) {
    Column (Modifier.fillMaxWidth()) {
        CircularIndicator(
            indicatorValue = fajrAmount + dohrAmount + asrAmount + maghrebAmount + ishaAmount,
            maxIndicatorValue = 75004,
        )
        // TODO: constraint layout
        Row(Modifier.fillMaxWidth().height(200.dp)) {
            CircularIndicator(
                indicatorValue = fajrAmount,
                maxIndicatorValue = 7504,
                smallText = R.string.fajr_prayer_text
            )
            CircularIndicator(
                indicatorValue = dohrAmount,
                maxIndicatorValue = 7504,
                smallText = R.string.dohr_prayer_text
            )
            CircularIndicator(
                indicatorValue = asrAmount,
                maxIndicatorValue = 7504,
                smallText = R.string.asr_prayer_text
            )
            CircularIndicator(
                indicatorValue = maghrebAmount,
                maxIndicatorValue = 7504,
                smallText = R.string.maghreb_prayer_text
            )
            CircularIndicator(
                indicatorValue = ishaAmount,
                maxIndicatorValue = 7504,
                smallText = R.string.isha_prayer_text
            )
        }
    }
}