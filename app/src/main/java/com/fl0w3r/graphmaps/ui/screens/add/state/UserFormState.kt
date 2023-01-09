package com.fl0w3r.graphmaps.ui.screens.add.state

import com.apollographql.apollo3.api.Optional
import com.fl0w3r.graphmaps.type.AddressInput
import com.fl0w3r.graphmaps.type.CompanyInput
import com.fl0w3r.graphmaps.type.CreateUserInput
import com.fl0w3r.graphmaps.type.GeoInput

data class UserFormState(
    val name: String = "",
    val userName: String = "",
    val email: String = "",

    val street: String = "",
    val suite: String = "",
    val city: String = "",
    val zipcode: String = "",
    val lat: String = "",
    val lng: String = "",

    val phone: String = "",
    val website: String = "",
    val companyName: String = "",
    val catchPhrase: String = "",
    val businessStrategy: String = ""
) {
    fun toCreateUserInput(): CreateUserInput {
        return CreateUserInput(
            name = this.name,
            username = this.userName,
            email = this.email,
            address = Optional.present(
                AddressInput(
                    street = Optional.present(this.street),
                    zipcode = Optional.present(this.zipcode),
                    suite = Optional.present(this.suite),
                    city = Optional.present(this.city),
                    geo = Optional.present(
                        GeoInput(
                            lat = Optional.present(this.lat.toDouble()),
                            lng = Optional.present(this.lng.toDouble())
                        )
                    )
                )
            ),
            phone = Optional.present(this.phone),
            website = Optional.present(this.website),
            company = Optional.present(
                CompanyInput(
                    name = Optional.present(this.companyName),
                    catchPhrase = Optional.present(this.catchPhrase),
                    bs = Optional.present(this.businessStrategy)
                )
            )
        )
    }
}


data class UserErrorState(
    var nameError: String = "",
    var userNameError: String = "",
    var emailError: String = "",
    var streetError: String = "",
    var suiteError: String = "",
    var cityError: String = "",
    var zipcodeError: String = "",
    var latError: String = "",
    var lngError: String = "",
    var companyNameError: String = "",
    var catchPhraseError: String = "",
    var businessStrategyError: String = "",
)