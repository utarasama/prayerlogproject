package com.example.prayerlog

data class PrayerFormUiState(
    val prayers: List<Prayer> = prayerNames.map { prayerName ->
        Prayer(prayerName, 0)
    },
    val isPrayerFieldValid: Array<Boolean> = Array(10) { false },
    val hasPrayedSomeOfThem: Boolean = false
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
