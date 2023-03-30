package com.example.prayerlog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.prayerlog.ui.theme.PrayerLogTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrayerLogTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        PrayerTextField(stringResource(R.string.fajr_prayer_text))
                        PrayerTextField(stringResource(R.string.dohr_prayer_text))
                        PrayerTextField(stringResource(R.string.asr_prayer_text))
                        PrayerTextField(stringResource(R.string.maghrib_prayer_text))
                        PrayerTextField(stringResource(R.string.icha_prayer_text))
                    }
                }
            }
        }
    }
}

@Composable
fun PrayerTextField(prayerName: String, prayerCount: Int? = null, modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf(prayerCount?.toString() ?: "") }

    OutlinedTextField(
        value = text,
        singleLine = true,
        onValueChange = { text = it },
        label = { Text(prayerName) },
        modifier = Modifier.padding(12.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal
        )
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PrayerLogTheme {
        PrayerTextField("Fajr")
    }
}