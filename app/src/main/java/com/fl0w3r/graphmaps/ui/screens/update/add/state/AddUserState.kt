package com.fl0w3r.graphmaps.ui.screens.update.add.state

import com.fl0w3r.graphmaps.UserMutation
import com.fl0w3r.graphmaps.graph.ApiStatus

data class AddUserState(
    val apiStatus: ApiStatus,
    val user: UserMutation.CreateUser? = null
)