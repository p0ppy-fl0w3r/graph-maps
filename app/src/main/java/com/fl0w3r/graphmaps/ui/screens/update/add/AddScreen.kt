package com.fl0w3r.graphmaps.ui.screens.update.add


import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fl0w3r.graphmaps.graph.ApiStatus
import com.fl0w3r.graphmaps.ui.screens.update.components.UserForm
import com.fl0w3r.graphmaps.ui.screens.update.add.state.AddUserState
import com.fl0w3r.graphmaps.ui.screens.update.state.UserErrorState
import com.fl0w3r.graphmaps.ui.screens.update.state.UserFormState

@Composable
fun AddScreen(
    onUserAdded: (Int) -> Unit,
    modifier: Modifier = Modifier,
    addViewModel: AddUserViewModel = viewModel()
) {

    val context = LocalContext.current

    val addUserSate by addViewModel.addUserState.observeAsState(
        AddUserState(
            apiStatus = ApiStatus.INITIAL, userId = null
        )
    )
    val userFormState by addViewModel.userFormState.observeAsState(UserFormState())
    val formErrorState by addViewModel.userErrorState.observeAsState(UserErrorState())

    if (addUserSate.apiStatus == ApiStatus.SUCCESS) {
        LaunchedEffect(addUserSate) {
            addUserSate.userId?.let {
                Toast.makeText(context, "Added user with id $it", Toast.LENGTH_SHORT).show()
                onUserAdded(it)
            }
        }
    }

    AddBody(
        modifier = modifier,
        userFormState = userFormState,
        errorState = formErrorState,
        onStateChange = {
            addViewModel.onFormChange(it)
        },
        onAddPressed = {
            addViewModel.addUser(it)
        },
        apiStatus = addUserSate.apiStatus
    )
}


@Composable
fun AddBody(
    userFormState: UserFormState,
    apiStatus: ApiStatus,
    errorState: UserErrorState,
    onStateChange: (UserFormState) -> Unit,
    onAddPressed: (UserFormState) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        UserForm(apiStatus = apiStatus,
            userFormState = userFormState,
            errorState = errorState,
            onUserFormChange = {
                onStateChange(it)
            },
            onUpdatePressed = {
                onAddPressed(it)
            })
    }
}
