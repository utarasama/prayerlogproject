package com.example.prayerlog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
                    Navigation()
                }
            }
        }
    }
}

/**
 * Tells if the TextField value is Int or not.
 * @param value The TextField value.
 * @return `true` if it's a positive Int, `false` otherwise
 */
private fun isInt(value: String): Boolean {
    val intValue = value.toIntOrNull()
    if (intValue != null)
        return isFormValid(intValue)
    return false
}

private fun isFormValid(vararg values: Int): Boolean {
    for (value in values)
        if (value < 0)
            return false
    return true
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PrayerTextField(
    @StringRes label: Int,
    value: String,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Next,
    onValueChange: (String) -> Unit,
) {
    var lostAtLeastOnceFocus by remember { mutableStateOf(false) }
    var hadAtLeastOnceFocus by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val labelStr: String= stringResource(label)

    fun hasTextFieldError(): Boolean {
        return lostAtLeastOnceFocus && !isInt(value) || (hadAtLeastOnceFocus && !isInt(value) && value != "")
    }

    OutlinedTextField(
        value = value,
        singleLine = true,
        onValueChange = onValueChange,
        label = { Text(stringResource(label)) },
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                if (it.isFocused) {
                    println("$labelStr is focused")
                    hadAtLeastOnceFocus = true
                } else if (!it.isFocused && hadAtLeastOnceFocus) {
                    println("$labelStr lost focus")
                    lostAtLeastOnceFocus = true
                }
            },
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
        isError = hasTextFieldError(),
        trailingIcon = { // Affichage d'une icône en cas d'erreur
            if (hasTextFieldError())
                Icon(
                    Icons.Filled.Warning,
                    "La donnée entrée dans $label n'est pas un nombre entier.",
                    tint = MaterialTheme.colors.error
                )
        }
    )
}

@Composable
fun ActionButton(
    @StringRes label: Int,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    customAction: () -> Unit,
) {
    Button(
        onClick = customAction,
        modifier = modifier.fillMaxWidth(),
        enabled = enabled
    ) {
        Text(text = stringResource(label), Modifier.padding(12.dp))
    }
}

@Composable
fun CheckboxWithText(
    checked: Boolean,
    @StringRes text: Int,
    clickableText: () -> Unit,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier.fillMaxWidth()
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(MaterialTheme.colors.primary),
            modifier = modifier.padding(8.dp)
        )
        Text(
            text = stringResource(id = text),
            modifier = modifier
                .padding(horizontal = 8.dp, vertical = 16.dp)
                .clickable(onClick = clickableText)
        )
    }
}

@Composable
fun PrayerFormScreen(navController: NavController, formTitle: String = "Salut à toi, jeune entrepreneur") {
    var fajrAmountToPrayInput by remember { mutableStateOf( "") }
    var dohrAmountToPrayInput by remember { mutableStateOf( "") }
    var asrAmountToPrayInput by remember { mutableStateOf( "") }
    var maghrebAmountToPrayInput by remember { mutableStateOf( "") }
    var ishaAmountToPrayInput by remember { mutableStateOf( "") }
    var hasMadeUpSomePrayers by remember { mutableStateOf( false) }
    val scrollState = rememberScrollState()

    val fajrAmountToPray = fajrAmountToPrayInput.toIntOrNull() ?: -1
    val dohrAmountToPray = dohrAmountToPrayInput.toIntOrNull() ?: -1
    val asrAmountToPray = asrAmountToPrayInput.toIntOrNull() ?: -1
    val maghrebAmountToPray = maghrebAmountToPrayInput.toIntOrNull() ?: -1
    val ishaAmountToPray = ishaAmountToPrayInput.toIntOrNull() ?: -1

    val isButtonEnabled = isFormValid(
        fajrAmountToPray,
        dohrAmountToPray,
        asrAmountToPray,
        maghrebAmountToPray,
        ishaAmountToPray
    )

    Column(
        Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .verticalScroll(state = scrollState),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formTitle,
            fontSize = MaterialTheme.typography.h4.fontSize
        )
        Spacer(Modifier.padding(12.dp))
        PrayerTextField(R.string.fajr_prayer_text, fajrAmountToPrayInput) {
            fajrAmountToPrayInput = it
        }
        PrayerTextField(R.string.dohr_prayer_text, dohrAmountToPrayInput) {
            dohrAmountToPrayInput = it
        }
        PrayerTextField(R.string.asr_prayer_text, asrAmountToPrayInput) {
            asrAmountToPrayInput = it
        }
        PrayerTextField(R.string.maghreb_prayer_text, maghrebAmountToPrayInput) {
            maghrebAmountToPrayInput = it
        }
        PrayerTextField(R.string.isha_prayer_text, ishaAmountToPrayInput, imeAction = ImeAction.Done,) {
            ishaAmountToPrayInput = it
        }
        Spacer(modifier = Modifier.height(12.dp))
        CheckboxWithText(
            hasMadeUpSomePrayers,
            R.string.i_already_accomplished_some_of_them_text,
            { hasMadeUpSomePrayers = !hasMadeUpSomePrayers }
        ) {
            hasMadeUpSomePrayers = it
        }
        Spacer(modifier = Modifier.height(24.dp))
        ActionButton(R.string.confirm_button_text, isButtonEnabled) {
            if (hasMadeUpSomePrayers) {
                // goto the same form in appearance but ask for already made up prayers
            } else
                navController.navigate(Screen.DashboardScreen.withArgs(
                    0, fajrAmountToPray,
                    0, dohrAmountToPray,
                    0, asrAmountToPray,
                    0, maghrebAmountToPray,
                    0, ishaAmountToPray
                ))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PrayerLogTheme {
        Navigation()
    }
}