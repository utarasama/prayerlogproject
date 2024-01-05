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
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.lifecycle.viewmodel.compose.viewModel
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
 * @return `true` is all numbers are at least equal to 0, `false` otherwise.
 */
private fun isFormValid(vararg values: Int): Boolean {
    for (value in values)
        if (value < 0)
            return false
    return true
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PrayerTextField(
    prayer: Prayer,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Next,
    isError: Boolean = false,
    isAmountPrayed: Boolean = false,
    onValueChange: (String) -> Unit
) {
    var lostAtLeastOnceFocus by rememberSaveable { mutableStateOf(false) }
    var hadAtLeastOnceFocus by rememberSaveable { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val labelTextFieldName: String= stringResource(prayer.nameId)
/*
    fun hasTextFieldError(): Boolean {
        return lostAtLeastOnceFocus && !isInt(value) || (hadAtLeastOnceFocus && !isInt(value) && value != "")
    }
*/
    var textFieldValue: String = ""
    if (isAmountPrayed) {
        if (prayer.amountPrayed != null)
            textFieldValue = prayer.amountPrayed.toString()
    } else if (prayer.amountToPray != null)
        textFieldValue = prayer.amountToPray.toString()

    OutlinedTextField(
        value = textFieldValue,
        singleLine = true,
        onValueChange = onValueChange,
        label = { Text(labelTextFieldName) },
        modifier = modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth()
            .onFocusChanged {
                if (it.isFocused) {
                    println("$labelTextFieldName is focused")
                    hadAtLeastOnceFocus = true
                } else if (!it.isFocused && hadAtLeastOnceFocus) {
                    println("$labelTextFieldName lost focus")
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
        isError = isError,
        trailingIcon = { // Affichage d'une icône en cas d'erreur
            if (isError)
                Icon(
                    Icons.Filled.Warning,
                    stringResource(R.string.entry_not_integer_text, labelTextFieldName),
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
        modifier = modifier
            .padding(12.dp)
            .fillMaxWidth(),
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
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier.fillMaxWidth()
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(MaterialTheme.colors.primary),
            //modifier = modifier.padding(8.dp)
        )
        Text(
            text = stringResource(id = text),
            modifier = modifier
                .padding(horizontal = 0.dp, vertical = 16.dp)
                .clickable(onClick = clickableText)
        )
    }
}

@Composable
fun PrayerFormScreen(
    navController: NavController,
    formTitle: String = "Salut à toi, jeune entrepreneur",
    prayerFormViewModel: PrayerFormViewModel = viewModel()
) {
    val prayerFormUiState by prayerFormViewModel.uiState.collectAsState()

    var sobhAmountToPrayInput by rememberSaveable { mutableStateOf( "") }
    var dohrAmountToPrayInput by rememberSaveable { mutableStateOf( "") }
    var asrAmountToPrayInput by rememberSaveable { mutableStateOf( "") }
    var maghrebAmountToPrayInput by rememberSaveable { mutableStateOf( "") }
    var ishaAmountToPrayInput by rememberSaveable { mutableStateOf( "") }

    var sobhAmountPrayedInput by rememberSaveable { mutableStateOf( "") }
    var dohrAmountPrayedInput by rememberSaveable { mutableStateOf( "") }
    var asrAmountPrayedInput by rememberSaveable { mutableStateOf( "") }
    var maghrebAmountPrayedInput by rememberSaveable { mutableStateOf( "") }
    var ishaAmountPrayedInput by rememberSaveable { mutableStateOf( "") }

    var hasMadeUpSomePrayers by rememberSaveable { mutableStateOf( false) }
    val scrollState = rememberScrollState()

    val sobhAmountToPray = sobhAmountToPrayInput.toIntOrNull() ?: -1
    val dohrAmountToPray = dohrAmountToPrayInput.toIntOrNull() ?: -1
    val asrAmountToPray = asrAmountToPrayInput.toIntOrNull() ?: -1
    val maghrebAmountToPray = maghrebAmountToPrayInput.toIntOrNull() ?: -1
    val ishaAmountToPray = ishaAmountToPrayInput.toIntOrNull() ?: -1
    val sobhAmountPrayed = sobhAmountPrayedInput.toIntOrNull() ?: -1
    val dohrAmountPrayed = dohrAmountPrayedInput.toIntOrNull() ?: -1
    val asrAmountPrayed = asrAmountPrayedInput.toIntOrNull() ?: -1
    val maghrebAmountPrayed = maghrebAmountPrayedInput.toIntOrNull() ?: -1
    val ishaAmountPrayed = ishaAmountPrayedInput.toIntOrNull() ?: -1


    var isButtonEnabled: Boolean = isFormValid(
        sobhAmountToPray, dohrAmountToPray, asrAmountToPray, maghrebAmountToPray, ishaAmountToPray
    )
    if (hasMadeUpSomePrayers)
        isButtonEnabled = isButtonEnabled && isFormValid(
            sobhAmountPrayed, dohrAmountPrayed, asrAmountPrayed, maghrebAmountPrayed, ishaAmountPrayed
        )

    Column(
        Modifier
            .fillMaxWidth()
            .verticalScroll(state = scrollState),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = formTitle,
            fontSize = MaterialTheme.typography.h4.fontSize,
            modifier = Modifier.padding(12.dp)
        )
        Spacer(Modifier.padding(12.dp))
        /*
        PrayerTextField(R.string.sobh_prayer_text, sobhAmountToPrayInput) {
            sobhAmountToPrayInput = it
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
        PrayerTextField(R.string.isha_prayer_text, ishaAmountToPrayInput, imeAction = ImeAction.Done) {
            ishaAmountToPrayInput = it
        }
        */
        for (prayer in prayerFormViewModel.prayers) {
            PrayerTextField(
                prayer = prayer,
                onValueChange = {
                    prayerFormViewModel.updatePrayerField(it, prayer.nameId)
                },
                isError = prayerFormViewModel.hasFieldError(prayer)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        CheckboxWithText(
            prayerFormUiState.hasPrayedSomeOfThem,
            R.string.i_already_accomplished_some_of_them_text,
            { prayerFormViewModel.toggleHasPrayedSomeOfThem() }
        ) {
            prayerFormViewModel.setHasPrayedSomeOfThem(it)
        }
        if (prayerFormUiState.hasPrayedSomeOfThem) {
            Spacer(modifier = Modifier.height(8.dp))
            for (prayer in prayerFormViewModel.prayers) {
                PrayerTextField(
                    prayer = prayer,
                    onValueChange = {
                        prayerFormViewModel.updatePrayerField(
                            it,
                            prayer.nameId,
                            prayerFormUiState.hasPrayedSomeOfThem
                        )
                    },
                    isAmountPrayed = true
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        ActionButton(R.string.confirm_button_text, prayerFormUiState.isConfirmButtonEnabled) {
            navController.navigate(Screen.DashboardScreen.withArgs(
                if (sobhAmountPrayed < 0) 0 else sobhAmountPrayed, sobhAmountToPray,
                if (dohrAmountPrayed < 0) 0 else dohrAmountPrayed, dohrAmountToPray,
                if (asrAmountPrayed < 0) 0 else asrAmountPrayed, asrAmountToPray,
                if (maghrebAmountPrayed < 0) 0 else maghrebAmountPrayed, maghrebAmountToPray,
                if (ishaAmountPrayed < 0) 0 else ishaAmountPrayed, ishaAmountToPray
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