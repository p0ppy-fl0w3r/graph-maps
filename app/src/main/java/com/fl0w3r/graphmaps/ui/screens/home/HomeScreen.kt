package com.fl0w3r.graphmaps.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fl0w3r.graphmaps.UserQuery
import com.fl0w3r.graphmaps.ui.screens.home.state.ApiStatus
import com.fl0w3r.graphmaps.ui.screens.home.state.UserState
import com.fl0w3r.graphmaps.ui.theme.GraphMapsTheme

@Composable
fun HomeScreen(
    onAddClicked: () -> Unit,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel()
) {

    val context = LocalContext.current

    var searchParam by remember {
        mutableStateOf("")
    }

    val userState by homeViewModel.userState.observeAsState(
        UserState(
            apiStatus = ApiStatus.INITIAL, user = null
        )
    )

    HomeBody(
        modifier = modifier,
        onAddClicked = { onAddClicked() },
        searchParam = searchParam,
        onSearchChanged = {
            searchParam = it
        },
        onSearchClicked = {
            // Try to convert the search param into a int. If it fails show a toast.
            it.toIntOrNull()?.let { userId ->
                homeViewModel.getUser(userId)
            } ?: Toast.makeText(
                context,
                "Please enter a valid user id!",
                Toast.LENGTH_SHORT
            ).show()
        },
        userState = userState
    )
}

@Composable
fun HomeBody(
    userState: UserState,
    searchParam: String,
    onSearchChanged: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onAddClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {

        Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            IconButton(onClick = { onAddClicked() }) {
                Icon(imageVector = Icons.Default.Map, contentDescription = "Add new user.")
            }
        }

        SearchUser(modifier = Modifier.padding(bottom = 12.dp),
            searchParam = searchParam,
            onSearchChanged = {
                onSearchChanged(it)
            },
            onSearchClicked = {
                onSearchClicked(it)
            })

        when (userState.apiStatus) {
            ApiStatus.INITIAL -> {
                Text(text = "Please enter a user id and press search!")
            }

            ApiStatus.PENDING -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            ApiStatus.FAILED -> {
                Text(text = "Failed to fetch user!!")
            }

            ApiStatus.NOT_FOUND -> {
                Text(text = "User not found!")
            }

            ApiStatus.SUCCESS -> {
                // Assumes that user is not null if api request succeeded
                UserItem(userState.user!!)
            }
        }

    }
}

@Composable
fun SearchUser(
    searchParam: String,
    onSearchChanged: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(value = searchParam, onValueChange = {
            onSearchChanged(it)
        }, label = {
            Text(text = "User Id")
        }, placeholder = {
            Text(text = "Get user by id...")
        }, modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Button(onClick = { onSearchClicked(searchParam) }) {
            Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
                Text(text = "Search")
            }

        }
    }
}

@Composable
fun UserItem(user: UserQuery.User, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column() {
            Row(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = user.name ?: "", style = MaterialTheme.typography.h5)
            }
            Divider(modifier = Modifier.background(color = MaterialTheme.colors.primary))

            Location(
                latitude = user.address?.geo?.lat ?: 0.0,
                longitude = user.address?.geo?.lng ?: 0.0
            )
        }
    }

}

@Composable
fun Location(latitude: Double, longitude: Double, modifier: Modifier = Modifier) {
    Text(text = "The location is $latitude and $longitude")
}

@Preview
@Composable
private fun UserPreview() {
    GraphMapsTheme {
        Surface {
            HomeBody(onAddClicked = { }, searchParam = "", onSearchChanged = {

            }, onSearchClicked = {}, userState = UserState(
                apiStatus = ApiStatus.INITIAL, user = null
            )
            )
        }
    }
}