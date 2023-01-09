package com.fl0w3r.graphmaps.ui.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fl0w3r.graphmaps.DeleteUserMutation
import com.fl0w3r.graphmaps.UserQuery
import com.fl0w3r.graphmaps.graph.ApiStatus
import com.fl0w3r.graphmaps.graph.GraphMapClient
import com.fl0w3r.graphmaps.ui.screens.home.state.UserDeleteState
import com.fl0w3r.graphmaps.ui.screens.home.state.UserState
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val graphClient = GraphMapClient.getClient()

    private val _userState = MutableLiveData<UserState>()
    val userState: LiveData<UserState>
        get() = _userState

    private val _userDeleteState = MutableLiveData<UserDeleteState>()
    val userDeleteState: LiveData<UserDeleteState>
        get() = _userDeleteState


    fun deleteUser(userId: String) {
        viewModelScope.launch {
            try {
                _userDeleteState.value = UserDeleteState(apiStatus = ApiStatus.PENDING)
                val response = graphClient.mutation(DeleteUserMutation(userId)).execute()
                if (response.data?.deleteUser == true) {
                    _userDeleteState.value =
                        UserDeleteState(apiStatus = ApiStatus.SUCCESS, deleted = true)
                } else {
                    _userDeleteState.value =
                        UserDeleteState(apiStatus = ApiStatus.FAILED, deleted = false)
                }
            } catch (e: Exception) {
                _userDeleteState.value =
                    UserDeleteState(apiStatus = ApiStatus.FAILED, deleted = false)
            }

        }
    }

    fun resetDeleteStatus() {
        _userDeleteState.value = UserDeleteState(
            apiStatus = ApiStatus.INITIAL,
            deleted = false
        )
    }

    fun resetAppState() {
        _userState.value = UserState(
            apiStatus = ApiStatus.INITIAL, user = null
        )

        resetDeleteStatus()
    }

    fun getUser(userId: Int) {
        viewModelScope.launch {
            try {
                _userState.value = UserState(
                    ApiStatus.PENDING,
                    user = null
                )

                val response = graphClient.query(UserQuery(userId.toString())).execute()
                val user = response.data?.user
                if (user?.name != null) {

                    _userState.value = UserState(
                        ApiStatus.SUCCESS,
                        user = user,
                    )
                } else {
                    _userState.value = UserState(
                        ApiStatus.NOT_FOUND,
                        null
                    )
                }
            } catch (e: Exception) {
                _userState.value = UserState(
                    ApiStatus.FAILED,
                    user = null
                )
            }
        }
    }

}