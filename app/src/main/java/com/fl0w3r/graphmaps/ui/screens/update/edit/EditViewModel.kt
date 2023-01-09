package com.fl0w3r.graphmaps.ui.screens.update.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fl0w3r.graphmaps.UpdateUserMutation
import com.fl0w3r.graphmaps.UserInfoQuery
import com.fl0w3r.graphmaps.graph.ApiStatus
import com.fl0w3r.graphmaps.graph.GraphMapClient
import com.fl0w3r.graphmaps.ui.screens.update.edit.state.EditUserState
import com.fl0w3r.graphmaps.ui.screens.update.edit.state.UserFetchStatus
import com.fl0w3r.graphmaps.ui.screens.update.state.UserErrorState
import com.fl0w3r.graphmaps.ui.screens.update.state.UserFormState
import com.fl0w3r.graphmaps.ui.screens.update.validateUser
import kotlinx.coroutines.launch

class EditViewModel : ViewModel() {

    private val graphClient = GraphMapClient.getClient()

    private val _userFormState = MutableLiveData<UserFormState>()
    val userFormState: LiveData<UserFormState>
        get() = _userFormState

    private val _userErrorState = MutableLiveData<UserErrorState>()
    val userErrorState: LiveData<UserErrorState>
        get() = _userErrorState

    private val _userEditState = MutableLiveData<EditUserState>()
    val userEditState: LiveData<EditUserState>
        get() = _userEditState

    private val _userFetchState = MutableLiveData<UserFetchStatus>()
    val userFetchStatus: LiveData<UserFetchStatus>
        get() = _userFetchState


    fun getUser(userId: String) {
        viewModelScope.launch {
            // Prevent fetching user every time the screen recomposes.
            if (_userFormState.value == null) {
                try {
                    val response = graphClient.query(UserInfoQuery(userId)).execute()
                    response.data?.user?.let {
                        _userFormState.value = UserFormState.fromUser(it)
                        _userFetchState.value = UserFetchStatus(apiStatus = ApiStatus.SUCCESS)
                    } ?: run {
                        _userFetchState.value = UserFetchStatus(apiStatus = ApiStatus.FAILED)
                    }
                } catch (e: Exception) {
                    _userFetchState.value = UserFetchStatus(apiStatus = ApiStatus.FAILED)
                }

            }
        }
    }

    fun editUser(userId: String, userFormState: UserFormState) {
        val validationResults = validateUser(userFormState)
        validationResults.second.let {
            _userErrorState.value = it
        }

        if (validationResults.first) {
            viewModelScope.launch {
                try {
                    _userEditState.value = EditUserState(
                        apiStatus = ApiStatus.PENDING
                    )

                    val userUpdateInput = userFormState.toUpdateUserInput()
                    val response =
                        graphClient.mutation(UpdateUserMutation(userId, userUpdateInput)).execute()

                    if (response.data?.updateUser?.name != null) {
                        _userEditState.value = EditUserState(
                            apiStatus = ApiStatus.SUCCESS,
                            updateUser = response.data?.updateUser!!
                        )
                    } else {
                        _userEditState.value = EditUserState(
                            apiStatus = ApiStatus.FAILED
                        )
                    }

                } catch (e: Exception) {
                    _userEditState.value = EditUserState(
                        apiStatus = ApiStatus.FAILED
                    )
                }
            }
        }
    }

    fun resetEditStatus() {
        _userEditState.value = EditUserState(
            apiStatus = ApiStatus.INITIAL
        )
    }

    fun onFormChange(userFormState: UserFormState) {
        _userFormState.value = userFormState
    }
}