package com.fl0w3r.graphmaps.ui.screens.update.model

import android.os.Parcelable
import com.fl0w3r.graphmaps.UpdateUserMutation
import com.fl0w3r.graphmaps.UserInfoQuery
import com.fl0w3r.graphmaps.UserMutation
import com.fl0w3r.graphmaps.ui.screens.update.state.UserFormState
import kotlinx.parcelize.Parcelize


@Parcelize
data class UpdatedUser(
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
) : Parcelable {
    companion object {
        fun fromCreateUser(user: UserMutation.CreateUser): UpdatedUser {
            return UpdatedUser(
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
        fun fromUserUpdate(user: UpdateUserMutation.UpdateUser): UpdatedUser {
            return UpdatedUser(
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
