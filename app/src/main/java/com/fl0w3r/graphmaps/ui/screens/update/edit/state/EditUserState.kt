package com.fl0w3r.graphmaps.ui.screens.update.edit.state

import com.fl0w3r.graphmaps.UpdateUserMutation
import com.fl0w3r.graphmaps.graph.ApiStatus

data class EditUserState(
    val apiStatus: ApiStatus,
    val updateUser: UpdateUserMutation.UpdateUser? = null
)

data class UserFetchStatus(
    val apiStatus: ApiStatus
)