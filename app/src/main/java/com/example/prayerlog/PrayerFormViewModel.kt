package com.example.prayerlog

import androidx.compose.runtime.currentComposer
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PrayerFormViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PrayerFormUiState())
    val uiState: StateFlow<PrayerFormUiState> = _uiState.asStateFlow()

    fun updatePrayerField(prayer: Prayer) {
        _uiState.update { currentState ->
            currentState.copy(
                prayers = currentState.prayers.updatePrayerList(prayer)
            )
        }

    }

    private fun List<Prayer>.updatePrayerList(prayerUpdated: Prayer): MutableList<Prayer> {
        val prayers = this.toMutableList()
        prayers.forEach { prayerInList ->
            if (prayerInList.nameId == prayerUpdated.nameId) {
                val prayerIndex = prayers.indexOf(prayerInList)
                prayers[prayerIndex] = prayerUpdated
            }
        }
        return prayers
    }

    /**
     * Sert à valider un champ du formulaire.
     * @return `true` s'il y a une erreur, `false` sinon.
     */
    fun validatePrayerField(fieldValue: String): Boolean {
        val fieldValueConverted = fieldValue.toIntOrNull()
        return fieldValueConverted == null || fieldValueConverted < 0
    }
}