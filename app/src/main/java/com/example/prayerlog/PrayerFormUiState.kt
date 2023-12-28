package com.example.prayerlog

data class PrayerFormUiState(
    val prayers: Unit = prayerNames.forEach {
        Prayer(it, 0)
    },
    val isPrayerFieldValid: Array<Boolean> = Array<Boolean>(10) { false }
) {
    // Code généré par Android Studio
    // Il a rajouté ces méthodes pour éviter un warning au niveau de isPrayerFieldValid
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PrayerFormUiState

        return isPrayerFieldValid.contentEquals(other.isPrayerFieldValid)
    }

    override fun hashCode(): Int {
        return isPrayerFieldValid.contentHashCode()
    }
}
