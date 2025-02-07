package `in`.iotkiit.raidersreckoningapp.view.navigation

sealed class RaidersReckoningScreens(val route: String) {
    object LoginScreen : RaidersReckoningScreens("login_screen")
    object DashBoardScreen : RaidersReckoningScreens("dashBoard_screen")
    object LeaderboardScreen : RaidersReckoningScreens("leaderboard_screen")
    object MyTeamScreen : RaidersReckoningScreens("myTeam_screen")
}