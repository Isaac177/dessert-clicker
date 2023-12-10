package com.example.dessertclicker.model

import com.example.dessertclicker.R

data class DessertClickerUIState(
    val revenue: Int = 0,
    val dessertsSold: Int = 0,
    val currentDessertIndex: Int = 0,
    val currentDessertPrice: Int = 0,
    val currentDessertImageId: Int = R.drawable.cupcake
)
