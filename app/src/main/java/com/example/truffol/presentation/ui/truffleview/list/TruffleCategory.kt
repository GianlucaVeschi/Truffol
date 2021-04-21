package com.example.truffol.presentation.ui.truffleview.list

enum class TruffleCategory(val value: String){
    BIANCO_PREGIATO("Bianco pregiato"),
    BIANCHETTO("Bianchetto"),
    NERO_PREGIATO("Nero pregiato"),
    ESTIVO("Nero Estivo"),
    INVERNALE("Nero Invernale"),
    LISCIO("Nero Liscio"),
}

fun getAllTruffleCategories(): List<TruffleCategory>{
    return listOf(
        TruffleCategory.BIANCO_PREGIATO,
        TruffleCategory.BIANCHETTO,
        TruffleCategory.NERO_PREGIATO,
        TruffleCategory.ESTIVO,
        TruffleCategory.INVERNALE,
        TruffleCategory.LISCIO)
}

fun getTruffleCategory(value: String): TruffleCategory? {
    val map = TruffleCategory.values().associateBy(TruffleCategory::value)
    return map[value]
}