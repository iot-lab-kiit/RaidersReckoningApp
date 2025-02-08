package `in`.iotkiit.raidersreckoningapp.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import `in`.iotkiit.raidersreckoningapp.view.screens.CreateTeamScreen
import `in`.iotkiit.raidersreckoningapp.view.screens.DashBoardScreen
import `in`.iotkiit.raidersreckoningapp.view.screens.LoginScreenControl
import `in`.iotkiit.raidersreckoningapp.view.screens.OnBoardingScreen

@Composable
fun RaidersReckoningNavigation(
    navController: NavHostController = rememberNavController()
) {

    //This variable checks if the user is logged in or not
    val isUserLoggedIn = Firebase.auth.currentUser != null

    //This variable updates the start destination as per the user's login status
    val startDestination = if (isUserLoggedIn) {
        RaidersReckoningScreens.DashBoardScreen.route
    } else {
        RaidersReckoningScreens.LoginScreen.route
    }

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
        composable(RaidersReckoningScreens.CreateTeamScreen.route){
            CreateTeamScreen(navController = navController)
        }
        composable(RaidersReckoningScreens.LoginScreen.route) {
            LoginScreenControl(
                onLoginSuccess = {
                    navController.navigate(RaidersReckoningScreens.DashBoardScreen.route) {
                        popUpTo(RaidersReckoningScreens.LoginScreen.route) {
                            inclusive = true
                            saveState = true
                        }
                    }
                }
            )
        }
        composable(RaidersReckoningScreens.MyTeamScreen.route) {
            //TODO
        }
        composable(RaidersReckoningScreens.LeaderboardScreen.route) {
            //TODO
        }
    }
}

@Preview
@Composable
private fun RaidersReckoningNavigationPrev() {
    RaidersReckoningNavigation()
}