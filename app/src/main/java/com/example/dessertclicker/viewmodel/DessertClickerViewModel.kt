package com.example.dessertclicker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dessertclicker.data.Datasource
import com.example.dessertclicker.model.Dessert
import com.example.dessertclicker.model.DessertClickerUIState

class DessertClickerViewModel : ViewModel() {

    // Initial state of the UI
    private val _uiState = MutableLiveData(DessertClickerUIState())
    val uiState: LiveData<DessertClickerUIState> = _uiState

    private val desserts = Datasource.dessertList
    private var currentDessertIndex = 0

    init {
        // Initialize your UI state with the first dessert in the list
        updateUIState(desserts.first())
    }

    fun onDessertClicked() {
        val currentDessert = desserts[currentDessertIndex]

        // Calculate the new revenue and desserts sold
        val newRevenue = uiState.value?.revenue?.plus(currentDessert.price) ?: 0
        val newDessertsSold = uiState.value?.dessertsSold?.inc() ?: 1

        // Determine the next dessert to show based on the sales
        val nextDessert = determineNextDessert(newDessertsSold)

        // Update the UI state
        _uiState.value = uiState.value?.copy(
            revenue = newRevenue,
            dessertsSold = newDessertsSold,
            currentDessertImageId = nextDessert.imageId,
            currentDessertPrice = nextDessert.price
        )
    }

    private fun determineNextDessert(dessertsSold: Int): Dessert {
        while (currentDessertIndex < desserts.size - 1 &&
            dessertsSold >= desserts[currentDessertIndex + 1].startProductionAmount) {
            currentDessertIndex++
        }
        return desserts[currentDessertIndex]
    }

    fun updateUIState(dessert: Dessert) {
        _uiState.value = DessertClickerUIState(
            currentDessertPrice = dessert.price,
            currentDessertImageId = dessert.imageId
        )
    }
}
