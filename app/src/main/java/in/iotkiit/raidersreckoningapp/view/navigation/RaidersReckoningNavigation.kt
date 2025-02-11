package `in`.iotkiit.raidersreckoningapp.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import `in`.iotkiit.raidersreckoningapp.view.screens.CreateTeamScreen
import `in`.iotkiit.raidersreckoningapp.view.screens.DashBoardScreen
import `in`.iotkiit.raidersreckoningapp.view.screens.JoinTeamScreen
import `in`.iotkiit.raidersreckoningapp.view.screens.LeaderboardScreen
import `in`.iotkiit.raidersreckoningapp.view.screens.LoginScreen
import `in`.iotkiit.raidersreckoningapp.view.screens.MyTeamScreen
import `in`.iotkiit.raidersreckoningapp.view.screens.OnBoardingScreen
import `in`.iotkiit.raidersreckoningapp.view.screens.ProceedScreen
import `in`.iotkiit.raidersreckoningapp.view.screens.QuestionScreen
import `in`.iotkiit.raidersreckoningapp.view.screens.QuestionScreenChoice

@Composable
fun RaidersReckoningNavigation(
    navController: NavHostController = rememberNavController()
) {
    //This variable checks if the user is logged in or not
    val isUserLoggedIn = Firebase.auth.currentUser != null

    val startDestination =
        if (isUserLoggedIn)
            RaidersReckoningScreens.DashBoardScreen.route
        else
            RaidersReckoningScreens.LoginScreen.route

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
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

        composable(RaidersReckoningScreens.ProceedScreen.route) {
            ProceedScreen(navController = navController)
        }

        composable(RaidersReckoningScreens.JoinTeamScreen.route) {
            JoinTeamScreen(navController = navController)
        }
    }
}