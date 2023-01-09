package com.fl0w3r.graphmaps.ui.screens.home.state

import com.fl0w3r.graphmaps.UserQuery.User
import com.fl0w3r.graphmaps.graph.ApiStatus

data class UserState(
    val apiStatus: ApiStatus,
    val user: User?
)

data class UserDeleteState(
    val apiStatus: ApiStatus,
    val deleted: Boolean = false
)
