package `in`.iotkiit.raidersreckoningapp.view.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import `in`.iotkiit.raidersreckoningapp.view.screens.CreateTeamScreen
import `in`.iotkiit.raidersreckoningapp.view.screens.DashBoardScreen
import `in`.iotkiit.raidersreckoningapp.view.screens.GetQuestionsQR
import `in`.iotkiit.raidersreckoningapp.view.screens.JoinTeamScreen
import `in`.iotkiit.raidersreckoningapp.view.screens.LeaderboardScreen
import `in`.iotkiit.raidersreckoningapp.view.screens.LoginScreen
import `in`.iotkiit.raidersreckoningapp.view.screens.MyTeamScreen
import `in`.iotkiit.raidersreckoningapp.view.screens.OnBoardingScreen
import `in`.iotkiit.raidersreckoningapp.view.screens.ProceedScreen
import `in`.iotkiit.raidersreckoningapp.view.screens.QuestionScreen
import `in`.iotkiit.raidersreckoningapp.view.screens.QuestionScreenChoice
import `in`.iotkiit.raidersreckoningapp.view.screens.QuestionScreenControl
import `in`.iotkiit.raidersreckoningapp.view.screens.ResultsScreen
import `in`.iotkiit.raidersreckoningapp.view.screens.SplashScreen
import java.time.temporal.TemporalQueries.zoneId

@Composable
fun RaidersReckoningNavigation(
    navController: NavHostController = rememberNavController()
) {
    //This variable checks if the user is logged in or not
    val isUserLoggedIn = Firebase.auth.currentUser != null

    val startDestination =
        RaidersReckoningScreens.SplashScreen.route

    NavHost(
        navController = navController,
        startDestination = startDestination
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

        composable(RaidersReckoningScreens.GetQuestionsQR.route) {
            GetQuestionsQR(navController = navController)
        }

        val questionScreenRoute = RaidersReckoningScreens.QuestionScreen.route
        composable("$questionScreenRoute/{zoneId}",
            arguments = listOf(
                navArgument(name = "zoneId") {
                    type = NavType.StringType
                }
            )
        ) {backStackEntry ->
            backStackEntry.arguments?.getString("zoneId").let {
                if(it != null) {
                    Log.d("zoneId", it)
                    QuestionScreenControl(navController = navController, zoneId = it)
                }
            }
        }

        composable(RaidersReckoningScreens.ResultsScreen.route) {
            ResultsScreen(
                navController = navController,
                onFinish = {
                    navController.navigate(RaidersReckoningScreens.DashBoardScreen.route) {
                        popUpTo(RaidersReckoningScreens.QuestionScreen.route) {
                            inclusive = false
                        }
                    }
                }
            )
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