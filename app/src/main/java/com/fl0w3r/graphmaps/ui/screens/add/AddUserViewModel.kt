package com.fl0w3r.graphmaps.ui.screens.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.Optional
import com.fl0w3r.graphmaps.UserMutation
import com.fl0w3r.graphmaps.graph.ApiStatus
import com.fl0w3r.graphmaps.graph.GraphMapClient
import com.fl0w3r.graphmaps.type.AddressInput
import com.fl0w3r.graphmaps.type.CompanyInput
import com.fl0w3r.graphmaps.type.CreateUserInput
import com.fl0w3r.graphmaps.type.GeoInput
import com.fl0w3r.graphmaps.ui.screens.add.state.AddUserState
import com.fl0w3r.graphmaps.ui.screens.add.state.UserErrorState
import com.fl0w3r.graphmaps.ui.screens.add.state.UserFormState
import kotlinx.coroutines.launch

class AddUserViewModel : ViewModel() {

    private val graphClient = GraphMapClient.getClient()

    private val _addUserState = MutableLiveData<AddUserState>()
    val addUserState: LiveData<AddUserState>
        get() = _addUserState

    private val _userFormState = MutableLiveData<UserFormState>()
    val userFormState: LiveData<UserFormState>
        get() = _userFormState

    private val _userErrorState = MutableLiveData<UserErrorState>()
    val userErrorState: LiveData<UserErrorState>
        get() = _userErrorState

    fun onFormChange(userFormState: UserFormState) {
        _userFormState.value = userFormState
    }

    fun addUser(userFormState: UserFormState) {
        if (validateUser(userFormState)) {
            val createUserInput = userFormState.toCreateUserInput()
            addNewUser(createUserInput)
        }
    }

    private fun validateUser(userFormState: UserFormState): Boolean {
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
            if (zipcode.toIntOrNull() == null) {
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

        _userErrorState.value = errorState

        return isValid
    }

    private fun addNewUser(createUserInput: CreateUserInput) {
        viewModelScope.launch {
            try {
                _addUserState.value = AddUserState(
                    apiStatus = ApiStatus.PENDING, userId = null
                )

                val response = graphClient.mutation(UserMutation(createUserInput)).execute()
                val userId = response.data?.createUser?.id?.toIntOrNull()

                if (userId != null) {
                    _addUserState.value = AddUserState(
                        apiStatus = ApiStatus.SUCCESS, userId = userId.toInt()
                    )
                } else {
                    _addUserState.value = AddUserState(
                        apiStatus = ApiStatus.FAILED, userId = -1
                    )
                }

            } catch (e: Exception) {
                _addUserState.value = AddUserState(
                    apiStatus = ApiStatus.FAILED, userId = -1
                )
            }
        }
    }

}