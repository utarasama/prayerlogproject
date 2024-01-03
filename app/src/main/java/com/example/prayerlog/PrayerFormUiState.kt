package com.example.prayerlog

data class PrayerFormUiState(
    val arePrayerFieldsValid: Array<Boolean> = Array(10) { false },
    val hasPrayedSomeOfThem: Boolean = false,
    val isConfirmButtonEnabled: Boolean = false
) {
    // Code généré par Android Studio
    // Il a rajouté ces méthodes pour éviter un warning au niveau de isPrayerFieldValid
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PrayerFormUiState

        return arePrayerFieldsValid.contentEquals(other.arePrayerFieldsValid)
    }

    override fun hashCode(): Int {
        return arePrayerFieldsValid.contentHashCode()
    }
}
