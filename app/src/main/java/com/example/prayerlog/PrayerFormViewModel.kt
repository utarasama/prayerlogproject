package com.example.prayerlog

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlin.reflect.KProperty

class PrayerFormViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PrayerFormUiState())
    val uiState: StateFlow<PrayerFormUiState> = _uiState.asStateFlow()


    var prayers = mutableListOf(
        Prayer(R.string.sobh, 0),
        Prayer(R.string.dohr, 0),
        Prayer(R.string.asr, 0),
        Prayer(R.string.maghreb, 0),
        Prayer(R.string.icha, 0),
    )

/*
    fun updatePrayerField(prayer: Prayer) {
        _uiState.update { currentState ->
            currentState.copy(
                prayers = currentState.prayers.updatePrayerList(prayer)
            )
        }
        validateForm()
    }
*/
    private fun MutableList<Prayer>.updatePrayerList(prayerUpdated: Prayer) {
        this.forEach { prayerInList ->
            if (prayerInList.nameId == prayerUpdated.nameId) {
                val prayerIndex = this.indexOf(prayerInList)
                this[prayerIndex] = prayerUpdated
            }
        }
    }

    fun updatePrayerField(value: String, prayerNameId: Int, extraMade: Boolean = false) {
        val prayerModified = prayers.find { prayer -> prayer.nameId == prayerNameId }
        if (extraMade) {
            prayerModified?.amountPrayed = value.toIntOrNull() ?: DEFAULT_PRAYER_FIELD_VALUE
        } else {
            prayerModified?.amountToPray = value.toIntOrNull() ?: DEFAULT_PRAYER_FIELD_VALUE
        }
        prayers.updatePrayerList(prayerModified!!)
        validateForm()
    }

    /**
     * Sert à valider un champ du formulaire.
     * @return `true` s'il y a une erreur, `false` sinon.
     */
    fun hasFieldError(prayer: Prayer): Boolean {
        if (_uiState.value.hasPrayedSomeOfThem) {
            if (prayer.amountPrayed!! > prayer.amountToPray!!)
                return true
        } else if (prayer.amountPrayed != 0 ||
            prayer.amountToPray == null || prayer.amountToPray!! < 0) {
            return true
        }
        return false
    }

    private fun validateForm() {
        val isFormValid: Boolean = prayers.all { prayer -> !hasFieldError(prayer)}
        _uiState.update { currentState ->
            currentState.copy(
                isConfirmButtonEnabled = isFormValid
            )
        }
    }

    fun toggleHasPrayedSomeOfThem() {
        _uiState.update { currentState ->
            currentState.copy(
                hasPrayedSomeOfThem = _uiState.value.hasPrayedSomeOfThem.not()
            )
        }
    }

    fun setHasPrayedSomeOfThem(hasPrayedSome: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                hasPrayedSomeOfThem = hasPrayedSome
            )
        }
    }
}
