package com.fl0w3r.graphmaps.ui.screens.update

import com.fl0w3r.graphmaps.ui.screens.update.state.UserErrorState
import com.fl0w3r.graphmaps.ui.screens.update.state.UserFormState


fun validateUser(userFormState: UserFormState): Pair<Boolean, UserErrorState> {
    var isValid = true
    val errorState = UserErrorState()

    userFormState.apply {
        if (name.length < 3) {
            errorState.nameError = "Name should be at lease 3 characters."
            isValid = false
        }
        if (userName.length < 3) {
            errorState.userNameError = "Username should be at lease 3 characters."
            isValid = false
        }
        if (!email.matches(Regex("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"))) {
            errorState.emailError = "Enter a valid email!"
            isValid = false
        }
        if (street.length < 3) {
            errorState.streetError = "Street should be at lease 3 characters."
            isValid = false
        }
        if (suite.length < 3) {
            errorState.suiteError = "Suite should be at lease 3 characters."
            isValid = false
        }
        if (city.length < 3) {
            errorState.cityError = "City should be at lease 3 characters."
            isValid = false
        }
        if (zipcode == "") {
            errorState.zipcodeError = "Please enter a valid zip code."
            isValid = false
        }
        if (lat.toDoubleOrNull() == null) {
            errorState.latError = "Please enter a valid latitude."
            isValid = false
        }
        if (lng.toDoubleOrNull() == null) {
            errorState.lngError = "Please enter a valid longitude."
            isValid = false
        }
        if (companyName.length < 3) {
            errorState.companyNameError = "Company name should be at lease 3 characters."
            isValid = false
        }
        if (catchPhrase.length < 3) {
            errorState.catchPhraseError = "Catch phrase name should be at lease 3 characters."
            isValid = false
        }
        if (businessStrategy.length < 3) {
            errorState.businessStrategyError =
                "Business strategy name should be at lease 3 characters."
            isValid = false
        }
    }

    return Pair(isValid, errorState)
}