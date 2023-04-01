package com.example.prayerlog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
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
                    PrayerFormScreen()
                }
            }
        }
    }
}

/**
 * Tells if the TextField value is Int or not.
 * @param value The TextField value.
 * @return `true` if it's an Int, `false` otherwise
 */
private fun isInt(value: String): Boolean {
    return value.toIntOrNull() == null
}

private fun isFormValid(vararg values: Int): Boolean {
    for (value in values)
        if (value == -1)
            return false
    return true
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PrayerTextField(
    prayerName: String,
    value: String,
    imeAction: ImeAction = ImeAction.Next,
    onValueChange: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current



    OutlinedTextField(
        value = value,
        singleLine = true,
        onValueChange = onValueChange,
        label = { Text(prayerName) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions( // On masque le clavier quand on finit la saisie
            onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        ),
        isError = isInt(value),
        trailingIcon = { // Affichage d'une icône en cas d'erreur
            if (isInt(value))
                Icon(
                    Icons.Filled.Warning,
                    "La donnée entrée dans $prayerName n'est pas un nombre entier.",
                    tint = MaterialTheme.colors.error
                )
        }
    )
}

@Composable
fun ActionButton(buttonText: String, enabled: Boolean, customAction: () -> Unit) {
    Button(
        onClick = customAction,
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled
    ) {
        Text(text = buttonText, Modifier.padding(12.dp))
    }
}

@Composable
fun PrayerFormScreen() {
    var fajrAmountInput by remember { mutableStateOf( "") }
    var dohrAmountInput by remember { mutableStateOf( "") }
    var asrAmountInput by remember { mutableStateOf( "") }
    var maghrebAmountInput by remember { mutableStateOf( "") }
    var ishaAmountInput by remember { mutableStateOf( "") }

    val fajrAmount = fajrAmountInput.toIntOrNull() ?: -1
    val dohrAmount = dohrAmountInput.toIntOrNull() ?: -1
    val asrAmount = asrAmountInput.toIntOrNull() ?: -1
    val maghrebAmount = maghrebAmountInput.toIntOrNull() ?: -1
    val ishaAmount = ishaAmountInput.toIntOrNull() ?: -1

    val isButtonEnabled = isFormValid(fajrAmount, dohrAmount, asrAmount, maghrebAmount, ishaAmount)

    Column(
        Modifier.fillMaxWidth().padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PrayerTextField(stringResource(R.string.fajr_prayer_text), fajrAmountInput) {
            fajrAmountInput = it
        }
        PrayerTextField(stringResource(R.string.dohr_prayer_text), dohrAmountInput) {
            dohrAmountInput = it
        }
        PrayerTextField(stringResource(R.string.asr_prayer_text), asrAmountInput) {
            asrAmountInput = it
        }
        PrayerTextField(stringResource(R.string.maghrib_prayer_text), maghrebAmountInput) {
            maghrebAmountInput = it
        }
        PrayerTextField(
            stringResource(R.string.icha_prayer_text),
            ishaAmountInput,
            imeAction = ImeAction.Done,
        ) {
            ishaAmountInput = it
        }
        Spacer(modifier = Modifier.height(24.dp))
        ActionButton(stringResource(R.string.confirmer_button_text), isButtonEnabled) {}
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PrayerLogTheme {
        PrayerFormScreen()
    }
}