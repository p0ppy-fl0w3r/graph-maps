package com.fl0w3r.graphmaps.ui.screens.update.state

import com.apollographql.apollo3.api.Optional
import com.fl0w3r.graphmaps.UserInfoQuery
import com.fl0w3r.graphmaps.type.AddressInput
import com.fl0w3r.graphmaps.type.CompanyInput
import com.fl0w3r.graphmaps.type.CreateUserInput
import com.fl0w3r.graphmaps.type.GeoInput
import com.fl0w3r.graphmaps.type.UpdateUserInput

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

    fun toUpdateUserInput(): UpdateUserInput {
        return UpdateUserInput(
            name = Optional.present(this.name),
            username = Optional.present(this.userName),
            email = Optional.present(this.email),
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

    companion object {
        fun fromUser(user: UserInfoQuery.User): UserFormState {
            return UserFormState(
                name = user.name ?: "",
                userName = user.username ?: "",
                email = user.email ?: "",
                street = user.address?.street ?: "",
                suite = user.address?.suite ?: "",
                city = user.address?.city ?: "",
                zipcode = user.address?.zipcode ?: "",
                lat = user.address?.geo?.lat?.toString() ?: "",
                lng = user.address?.geo?.lng?.toString() ?: "",
                phone = user.phone ?: "",
                website = user.website ?: "",
                companyName = user.company?.name ?: "",
                catchPhrase = user.company?.catchPhrase ?: "",
                businessStrategy = user.company?.bs ?: ""
            )
        }
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