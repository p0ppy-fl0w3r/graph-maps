package com.fl0w3r.graphmaps.ui.screens.update

import com.fl0w3r.graphmaps.ui.screens.update.model.UpdatedUser
import com.fl0w3r.graphmaps.ui.screens.update.state.UserErrorState
import com.fl0w3r.graphmaps.ui.screens.update.state.UserFormState
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

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

        val latValue = lat.toDoubleOrNull()

        if (latValue == null) {
            errorState.latError = "Please enter a valid latitude."
            isValid = false
        } else if (latValue < -90 || latValue > 90) {
            errorState.latError = "Latitude should be between -90 and 90"
            isValid = false
        }

        val lngValue = lng.toDoubleOrNull()

        if (lngValue == null) {
            errorState.latError = "Please enter a valid latitude."
            isValid = false
        } else if (lngValue < -180 || lngValue > 180) {
            errorState.lngError = "Longitude should be between -180 and 180"
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

fun UpdatedUser.toJson(): String {
    val adapter = moshi.adapter(UpdatedUser::class.java)
    return adapter.toJson(this)
}

fun getUpdatedUser(json: String): UpdatedUser? {
    return try {
        val adapter = moshi.adapter(UpdatedUser::class.java)
        adapter.fromJson(json)
    } catch (e: Exception) {
        null
    }
}