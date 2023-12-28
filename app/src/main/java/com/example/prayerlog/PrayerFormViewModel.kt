package com.example.prayerlog

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
                //prayers =
            )
        }

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