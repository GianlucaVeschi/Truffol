package com.example.tartufozon.presentation.ui.list

enum class TruffleCategory(val value: String){
    CHICKEN("Chicken"),
    BEEF("Beef"),
    SOUP("Soup"),
    DESSERT("Dessert"),
    VEGETARIAN("Vegetarian"),
    MILK("Milk"),
    VEGAN("Vegan"),
    PIZZA("Pizza"),
    DONUT("Donut"),
}

fun getAllTruffleCategories(): List<TruffleCategory>{
    return listOf(
        TruffleCategory.CHICKEN,
        TruffleCategory.BEEF,
        TruffleCategory.SOUP,
        TruffleCategory.DESSERT,
        TruffleCategory.VEGETARIAN,
        TruffleCategory.MILK,
        TruffleCategory.VEGAN,
        TruffleCategory.PIZZA,
        TruffleCategory.DONUT)
}

fun getTruffleCategory(value: String): TruffleCategory? {
    val map = TruffleCategory.values().associateBy(TruffleCategory::value)
    return map[value]
}