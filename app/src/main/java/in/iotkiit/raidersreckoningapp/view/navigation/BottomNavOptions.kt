package `in`.iotkiit.raidersreckoningapp.view.navigation

import androidx.navigation.NavController
import `in`.iotkiit.raidersreckoningapp.R

sealed class BottomNavOptions(
    val route: String,
    val labelOfIcon: String,
    val unselectedIcon: Int,
    val selectedIcon: Int,
    val onOptionClicked: (NavController) -> Unit,
) {

    data object DashBoardOption: BottomNavOptions(
        route = RaidersReckoningScreens.DashBoardScreen.route,
        labelOfIcon = "Dashboard",
        unselectedIcon = R.drawable.nav_home,
        selectedIcon = R.drawable.nav_home_filled,
        onOptionClicked = {
            it.navigate(RaidersReckoningScreens.DashBoardScreen.route) {
                popUpTo(it.graph.startDestinationId)
                launchSingleTop = true
            }
        })

    data object LeaderboardOption: BottomNavOptions(
        route = RaidersReckoningScreens.LeaderboardScreen.route,
        labelOfIcon = "Leaderboard",
        selectedIcon = R.drawable.nav_leaderboard_filled,
        unselectedIcon = R.drawable.nav_leaderboard,
        onOptionClicked = {
            it.navigate(RaidersReckoningScreens.LeaderboardScreen.route) {
                popUpTo(it.graph.startDestinationId)
                launchSingleTop = true
            }
        })

    data object MyTeamOption: BottomNavOptions(
        route = RaidersReckoningScreens.MyTeamScreen.route,
        labelOfIcon = "My team",
        selectedIcon = R.drawable.nav_my_team_filled,
        unselectedIcon = R.drawable.nav_my_team,
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