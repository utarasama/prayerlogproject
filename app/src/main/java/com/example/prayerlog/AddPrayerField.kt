package com.example.prayerlog

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.prayerlog.ui.theme.PrayerLogTheme

@Composable
fun AddPrayerField(
    prayer: Prayer,
    modifier: Modifier = Modifier,
) {
    var amountToAddInput by rememberSaveable { mutableStateOf("0") }
    val amountToAdd: Int = amountToAddInput.toIntOrNull() ?: 0
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = stringResource(id = prayer.nameId))
        SquaredIconButton(
            Icons.Filled.KeyboardArrowLeft,
            R.string.add_accomplished_prayers_text) {
            amountToAddInput = (amountToAdd - 1).toString()
        }
        TextField(
            amountToAddInput,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            onValueChange = {
                amountToAddInput = it
            },
            modifier = modifier.width(80.dp)
        )
        SquaredIconButton(
            Icons.Filled.KeyboardArrowRight,
            R.string.add_accomplished_prayers_text) {
            amountToAddInput = (amountToAdd + 1).toString()
        }
    }
}

@Composable
fun SquaredIconButton(
    icon: ImageVector,
    @StringRes description: Int,
    onClick: () -> Unit
) {
    Button(onClick = onClick) {
        Icon(icon, stringResource(description))
    }
}

@Preview(showBackground = true)
@Composable
fun AddPrayerPreview() {
    PrayerLogTheme {
        AddPrayerField(
            Prayer(
                R.string.sobh_prayer_text,
                5
            )
        )
    }
}