package `in`.iotkiit.raidersreckoningapp.view.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController

sealed class BottomNavOptions(
    val route: String,
    val labelOfIcon: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    val onOptionClicked: (NavController) -> Unit,
) {

    data object DashBoardOption: BottomNavOptions(
        route = RaidersReckoningScreens.DashBoardScreen.route,
        labelOfIcon = "Dashboard",
        unselectedIcon = Icons.Outlined.Home,
        selectedIcon = Icons.Filled.Home,
        onOptionClicked = {
            it.navigate(RaidersReckoningScreens.DashBoardScreen.route) {
                popUpTo(it.graph.startDestinationId)
                launchSingleTop = true
            }
        })

    data object LeaderboardOption: BottomNavOptions(
        route = RaidersReckoningScreens.LeaderboardScreen.route,
        labelOfIcon = "Leaderboard",
        selectedIcon = Icons.Filled.Menu,
        unselectedIcon = Icons.Outlined.Menu,
        onOptionClicked = {
            it.navigate(RaidersReckoningScreens.LeaderboardScreen.route) {
                popUpTo(it.graph.startDestinationId)
                launchSingleTop = true
            }
        })

    data object MyTeamOption: BottomNavOptions(
        route = RaidersReckoningScreens.MyTeamScreen.route,
        labelOfIcon = "My team",
        selectedIcon = Icons.Filled.Face,
        unselectedIcon = Icons.Outlined.Face,
        onOptionClicked = {
            it.navigate(RaidersReckoningScreens.MyTeamScreen.route) {
                popUpTo(it.graph.startDestinationId)
                launchSingleTop = true
            }
        })

    companion object {
        val bottomNavOptions = listOf(
            LeaderboardOption,
            DashBoardOption,
            MyTeamOption
        )
    }

}