package com.fl0w3r.graphmaps.ui.screens.update.edit

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fl0w3r.graphmaps.UpdateUserMutation
import com.fl0w3r.graphmaps.graph.ApiStatus
import com.fl0w3r.graphmaps.ui.screens.update.components.UserForm
import com.fl0w3r.graphmaps.ui.screens.update.edit.state.EditUserState
import com.fl0w3r.graphmaps.ui.screens.update.edit.state.UserFetchStatus
import com.fl0w3r.graphmaps.ui.screens.update.state.UserErrorState
import com.fl0w3r.graphmaps.ui.screens.update.state.UserFormState

@Composable
fun EditScreen(
    userId: String,
    modifier: Modifier = Modifier,
    onUserEdited: (UpdateUserMutation.UpdateUser) -> Unit,
    editViewModel: EditViewModel = viewModel()
) {

    val context = LocalContext.current

    val userFormState by editViewModel.userFormState.observeAsState(initial = null)
    val userErrorState by editViewModel.userErrorState.observeAsState(initial = UserErrorState())

    val userFetchStatus by editViewModel.userFetchStatus.observeAsState(
        initial = UserFetchStatus(
            apiStatus = ApiStatus.INITIAL
        )
    )

    val userEditStatus by editViewModel.userEditState.observeAsState(
        initial = EditUserState(
            apiStatus = ApiStatus.INITIAL
        )
    )

    if (userEditStatus.apiStatus != ApiStatus.INITIAL) {
        if (userEditStatus.apiStatus == ApiStatus.SUCCESS) {
            LaunchedEffect(userEditStatus) {
                Toast.makeText(context, "Updated user!!", Toast.LENGTH_SHORT).show()
                // Again, assumes that update user is not null if we successfully edited a user.
                onUserEdited(userEditStatus.updateUser!!)
            }
        } else if (userEditStatus.apiStatus == ApiStatus.FAILED) {
            Toast.makeText(context, "Failed to update user!!", Toast.LENGTH_SHORT).show()
            editViewModel.resetEditStatus()
        }
    }


    editViewModel.getUser(userId)

    if (userFormState != null) {
        EditBody(
            modifier = modifier,
            userFormState = userFormState!!,
            userErrorState = userErrorState,
            onStateChange = {
                editViewModel.onFormChange(it)
            },
            onEditPressed = {
                editViewModel.editUser(userId, it)
            },
            apiStatus = userEditStatus.apiStatus
        )
    } else {
        when (userFetchStatus.apiStatus) {
            ApiStatus.INITIAL -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            else -> {
                Text(text = "Failed to fetch user!")
            }
        }
    }
}


@Composable
fun EditBody(
    userErrorState: UserErrorState,
    userFormState: UserFormState,
    onStateChange: (UserFormState) -> Unit,
    onEditPressed: (UserFormState) -> Unit,
    apiStatus: ApiStatus,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(4.dp)
            .verticalScroll(rememberScrollState())
    ) {
        UserForm(
            userFormState = userFormState,
            errorState = userErrorState,
            onUserFormChange = {
                onStateChange(it)
            },
            onUpdatePressed = {
                onEditPressed(it)
            },
            isEdit = true,
            apiStatus = apiStatus,
        )

    }

}