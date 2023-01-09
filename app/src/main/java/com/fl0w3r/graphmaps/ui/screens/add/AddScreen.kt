package com.fl0w3r.graphmaps.ui.screens.add


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fl0w3r.graphmaps.graph.ApiStatus
import com.fl0w3r.graphmaps.ui.components.InputField
import com.fl0w3r.graphmaps.ui.screens.add.state.AddUserState
import com.fl0w3r.graphmaps.ui.screens.add.state.UserErrorState
import com.fl0w3r.graphmaps.ui.screens.add.state.UserFormState

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
        AddUserForm(apiStatus = apiStatus,
            userFormState = userFormState,
            errorState = errorState,
            onUserFormChange = {
                onStateChange(it)
            },
            onAddPressed = {
                onAddPressed(it)
            })
    }
}

@Composable
private fun LabeledBreak(label: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(6.dp)) {

        Text(text = label, style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(2.dp))
        Divider(
            modifier = Modifier
                .padding(top = 2.dp, bottom = 6.dp)
                .height(2.dp)
                .background(color = MaterialTheme.colors.primary)
        )

    }
}

@Composable
fun AddUserForm(
    userFormState: UserFormState,
    errorState: UserErrorState,
    onUserFormChange: (UserFormState) -> Unit,
    onAddPressed: (UserFormState) -> Unit,
    apiStatus: ApiStatus,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth()
    ) {

        LabeledBreak(label = "Personal Information")

        InputField(
            value = userFormState.name,
            onValueChange = {
                onUserFormChange(userFormState.copy(name = it))
            },
            label = "Full Name",
            placeholder = "Enter full name....",
            error = errorState.nameError
        )
        InputField(
            value = userFormState.userName,
            onValueChange = {
                onUserFormChange(userFormState.copy(userName = it))
            },
            label = "Username",
            placeholder = "Enter a username....",
            error = errorState.userNameError
        )

        InputField(
            value = userFormState.email, onValueChange = {
                onUserFormChange(userFormState.copy(email = it))
            }, label = "Email", placeholder = "Enter email....", error = errorState.emailError
        )


        InputField(
            value = userFormState.phone,
            onValueChange = {
                onUserFormChange(userFormState.copy(phone = it))
            },
            label = "Phone Number",
            placeholder = "Enter phone number....",
            keyboardType = KeyboardType.Number
        )

        InputField(
            value = userFormState.website, onValueChange = {
                onUserFormChange(userFormState.copy(website = it))
            }, label = "Website", placeholder = "Enter company website...."
        )

        LabeledBreak(label = "Address")

        InputField(
            value = userFormState.street,
            onValueChange = {
                onUserFormChange(userFormState.copy(street = it))
            },
            label = "Street",
            placeholder = "Enter the street....",
            error = errorState.streetError
        )

        InputField(
            value = userFormState.suite, onValueChange = {
                onUserFormChange(userFormState.copy(suite = it))
            }, label = "Suite", placeholder = "Enter the suite....", error = errorState.suiteError
        )

        InputField(
            value = userFormState.city, onValueChange = {
                onUserFormChange(userFormState.copy(city = it))
            }, label = "City", placeholder = "Enter the city....", error = errorState.cityError
        )

        InputField(
            value = userFormState.zipcode,
            onValueChange = {
                onUserFormChange(userFormState.copy(zipcode = it))
            },
            label = "Zip Code",
            placeholder = "Enter the zip code....",
            error = errorState.zipcodeError,
            keyboardType = KeyboardType.Number
        )

        InputField(
            value = userFormState.lat,
            onValueChange = {
                onUserFormChange(userFormState.copy(lat = it))
            },
            label = "Latitude",
            placeholder = "Enter the latitude....",
            error = errorState.latError,
            keyboardType = KeyboardType.Number
        )

        InputField(
            value = userFormState.lng,
            onValueChange = {
                onUserFormChange(userFormState.copy(lng = it))
            },
            label = "Longitude",
            placeholder = "Enter the longitude....",
            error = errorState.lngError,
            keyboardType = KeyboardType.Number
        )

        LabeledBreak(label = "Company")

        InputField(
            value = userFormState.companyName,
            onValueChange = {
                onUserFormChange(userFormState.copy(companyName = it))
            },
            label = "Company Name",
            placeholder = "Enter company name....",
            error = errorState.companyNameError
        )

        InputField(
            value = userFormState.catchPhrase,
            onValueChange = {
                onUserFormChange(userFormState.copy(catchPhrase = it))
            },
            label = "Catch Phrase",
            placeholder = "Enter catch phrase....",
            error = errorState.catchPhraseError
        )

        InputField(
            value = userFormState.businessStrategy,
            onValueChange = {
                onUserFormChange(userFormState.copy(businessStrategy = it))
            },
            label = "Business Strategy",
            placeholder = "Enter business strategy....",
            error = errorState.businessStrategyError
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = { onAddPressed(userFormState) }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    Text(text = "Add")
                }
            }

            if (apiStatus == ApiStatus.PENDING) {
                Spacer(modifier = Modifier.width(10.dp))
                CircularProgressIndicator()
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            if (apiStatus == ApiStatus.FAILED) {
                Text(text = "Failed to add user!", color = MaterialTheme.colors.error)
            }
        }
    }
}