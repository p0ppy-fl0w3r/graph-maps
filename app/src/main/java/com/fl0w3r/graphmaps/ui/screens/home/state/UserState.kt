package com.fl0w3r.graphmaps.ui.screens.home.state

import com.fl0w3r.graphmaps.UserQuery.User

data class UserState(
    val apiStatus: ApiStatus,
    val user: User?
)

enum class ApiStatus {
    INITIAL, PENDING, SUCCESS, FAILED, NOT_FOUND
}