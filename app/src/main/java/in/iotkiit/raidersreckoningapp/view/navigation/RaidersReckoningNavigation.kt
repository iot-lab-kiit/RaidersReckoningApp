package `in`.iotkiit.raidersreckoningapp.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import `in`.iotkiit.raidersreckoningapp.view.screens.*

@Composable
fun RaidersReckoningNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = RaidersReckoningScreens.SplashScreen.route
    ) {
        composable(RaidersReckoningScreens.SplashScreen.route) {
            SplashScreen(navController = navController)
        }

        composable(RaidersReckoningScreens.DashBoardScreen.route) {
            DashBoardScreen(navController = navController)
        }

        composable(RaidersReckoningScreens.OnBoardingScreen.route) {
            OnBoardingScreen(navController = navController)
        }

        composable(RaidersReckoningScreens.CreateTeamScreen.route) {
            CreateTeamScreen(navController = navController)
        }

        composable(RaidersReckoningScreens.LoginScreen.route) {
            LoginScreen(navController = navController)
        }

        composable(RaidersReckoningScreens.MyTeamScreen.route) {
            MyTeamScreen(navController = navController)
        }

        composable(RaidersReckoningScreens.QuestionScreenChoice.route) {
            QuestionScreenChoice(navController = navController)
        }

        composable(RaidersReckoningScreens.QuestionScreen.route) {
            QuestionScreen(navController = navController)
        }

        composable(RaidersReckoningScreens.LeaderboardScreen.route) {
            LeaderboardScreen(navController = navController)
        }

        composable(RaidersReckoningScreens.JoinTeamScreen.route) {
            JoinTeamScreen()
        }
    }
}

@Preview
@Composable
private fun RaidersReckoningNavigationPrev() {
    RaidersReckoningNavigation()
}