package com.example.prayerlog

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun DashboardScreen(
    fajr: Prayer,
    dohr: Prayer,
    asr: Prayer,
    maghreb: Prayer,
    isha: Prayer,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(modifier.fillMaxSize()) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            CircularIndicator(
                indicatorValue = fajr.amountPrayed + dohr.amountPrayed + asr.amountPrayed + maghreb.amountPrayed + isha.amountPrayed,
                maxIndicatorValue = fajr.amountToPray + dohr.amountToPray + asr.amountToPray + maghreb.amountToPray + isha.amountToPray,
                foregroundIndicatorStrokeWidth = 50f,
                backgroundIndicatorStrokeWidth = 50f,
            )
            // TODO: constraint layout
            val rowSize = 85.dp
            val prayers = listOf(fajr, dohr, asr, maghreb, isha)


            Row(
                modifier
                    .fillMaxWidth()
                    .height(rowSize),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                for (prayer in prayers)
                    CircularIndicator(
                        indicatorValue = prayer.amountPrayed,
                        maxIndicatorValue = prayer.amountToPray,
                        smallText = prayer.nameId,
                        canvasSize = rowSize,
                        foregroundIndicatorStrokeWidth = 10f,
                        backgroundIndicatorStrokeWidth = 10f,
                        bigTextFontSize = MaterialTheme.typography.body1.fontSize,
                        smallTextFontSize = MaterialTheme.typography.overline.fontSize
                    )
            }
            //TODO: make it displayed in a round shape


        }
        val button = createRef()
        Button(onClick = {},
            modifier
                .clip(CircleShape)
                .constrainAs(button) {
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    absoluteRight.linkTo(parent.absoluteRight, 16.dp)
                }) {
            Icon(Icons.Filled.Add, stringResource(R.string.add_accomplished_prayers_text))
        }
    }
}