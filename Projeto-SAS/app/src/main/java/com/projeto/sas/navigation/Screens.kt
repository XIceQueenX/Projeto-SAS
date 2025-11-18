package com.projeto.sas.navigation

import kotlinx.serialization.Serializable

/*sealed interface Screen {
    @Serializable
    data object Home : Screen

    @Serializable
    data object Products : Screen

    @Serializable
    data object Donations : Screen

    @Serializable
    data class Detail(
        val itemId: String,
    ) : Screen

    @Serializable
    data object Profile: Screen
}*/

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Donations : Screen("donations")
    data object Products : Screen("products")
    data object Profile : Screen("profile")
}
