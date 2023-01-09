package com.fl0w3r.graphmaps.ui.screens.update

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddRoad
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhoneInTalk
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Web
import androidx.compose.material.icons.filled.Work
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fl0w3r.graphmaps.ui.components.Location
import com.fl0w3r.graphmaps.ui.screens.update.model.UpdatedUser
import com.fl0w3r.graphmaps.ui.theme.GraphMapsTheme

@Composable
fun UpdatedUserScreen(updatedUser: UpdatedUser, modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Heading(title = "Personal Information")

        Field(title = "Name", value = updatedUser.name, icon = Icons.Default.Person)
        Field(title = "Username", value = updatedUser.userName, icon = Icons.Default.VerifiedUser)
        Field(title = "Email", value = updatedUser.email, icon = Icons.Default.Email)
        Field(title = "Website", value = updatedUser.name, icon = Icons.Default.Web)
        Field(title = "Phone", value = updatedUser.phone, icon = Icons.Default.Phone)

        Spacer(modifier = Modifier.height(8.dp))
        Heading(title = "Address")

        Field(title = "City", value = updatedUser.city, icon = Icons.Default.LocationCity)
        Field(title = "Street", value = updatedUser.street, icon = Icons.Default.AddRoad)
        Field(title = "Suite", value = updatedUser.suite, icon = Icons.Default.House)
        Field(title = "Zip Code", value = updatedUser.zipcode, icon = Icons.Default.MyLocation)
        Spacer(modifier = Modifier.height(8.dp))
        Location(latitude = updatedUser.lat, longitude = updatedUser.lng)
        Spacer(modifier = Modifier.height(8.dp))
        Field(
            title = "Coordinates",
            value = "[${updatedUser.lat}, ${updatedUser.lng}]",
            icon = Icons.Default.Map
        )

        Spacer(modifier = Modifier.height(8.dp))
        Heading(title = "Company")
        Field(title = "Company Name", value = updatedUser.companyName, icon = Icons.Default.Work)
        Field(
            title = "Catch Phrase",
            value = updatedUser.catchPhrase,
            icon = Icons.Default.PhoneInTalk
        )
        Field(
            title = "Business Strategy",
            value = updatedUser.businessStrategy,
            icon = Icons.Default.Money
        )

    }
}

@Composable
private fun Heading(title: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(vertical = 8.dp)) {
        Text(text = title, style = MaterialTheme.typography.h5)
        Divider(
            modifier = Modifier.background(color = MaterialTheme.colors.primary)
        )
    }
}

@Composable
private fun Field(title: String, value: String, icon: ImageVector, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = "$title:", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = value)

    }
}

@Preview
@Composable
private fun UpdatedUserPreview() {

    val updatedUser = UpdatedUser(
        name = "Test Person",
        userName = "test123",
        email = "Test@gmal.com",
        street = "Big",
        suite = "House",
        city = "Cat",
        zipcode = "69420",
        lat = "90",
        lng = "90",
        phone = "694201337",
        website = "maps.com",
        companyName = "Fish",
        catchPhrase = "Gib fish plz",
        businessStrategy = "buy-fish sell-fish"
    )

    GraphMapsTheme {
        Surface {
            UpdatedUserScreen(updatedUser = updatedUser)
        }
    }
}