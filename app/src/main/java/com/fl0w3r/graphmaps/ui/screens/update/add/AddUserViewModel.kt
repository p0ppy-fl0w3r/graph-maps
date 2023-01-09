package com.fl0w3r.graphmaps.ui.screens.update.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fl0w3r.graphmaps.UserMutation
import com.fl0w3r.graphmaps.graph.ApiStatus
import com.fl0w3r.graphmaps.graph.GraphMapClient
import com.fl0w3r.graphmaps.type.CreateUserInput
import com.fl0w3r.graphmaps.ui.screens.update.add.state.AddUserState
import com.fl0w3r.graphmaps.ui.screens.update.state.UserErrorState
import com.fl0w3r.graphmaps.ui.screens.update.state.UserFormState
import com.fl0w3r.graphmaps.ui.screens.update.validateUser
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
        val validationResult = validateUser(userFormState)
        validationResult.second.let {
            _userErrorState.value = it
        }

        if (validationResult.first) {
            val createUserInput = userFormState.toCreateUserInput()
            addNewUser(createUserInput)
        }
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