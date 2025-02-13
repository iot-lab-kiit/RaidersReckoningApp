package `in`.iotkiit.raidersreckoningapp.view.navigation

sealed class RaidersReckoningScreens(val route: String) {
    object SplashScreen : RaidersReckoningScreens("splash_screen")
    object LoginScreen : RaidersReckoningScreens("login_screen")
    object DashBoardScreen : RaidersReckoningScreens("dashBoard_screen")
    object LeaderboardScreen : RaidersReckoningScreens("leaderboard_screen")
    object MyTeamScreen : RaidersReckoningScreens("myTeam_screen")
    object CreateTeamScreen : RaidersReckoningScreens("createTeam_screen")
    object OnBoardingScreen : RaidersReckoningScreens("onBoarding_screen")
    object QuestionScreen: RaidersReckoningScreens("question_screen")
    object JoinTeamScreen: RaidersReckoningScreens("join_team_screen")
    object ProceedScreen: RaidersReckoningScreens("proceed_screen")
    object ResultsScreen: RaidersReckoningScreens("results_screen")
    object GetQuestionsQR: RaidersReckoningScreens("get_questions_qr")
}