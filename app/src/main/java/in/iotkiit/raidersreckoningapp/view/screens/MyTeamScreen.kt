package `in`.iotkiit.raidersreckoningapp.view.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import `in`.iotkiit.raidersreckoningapp.view.navigation.BottomNavBar
import `in`.iotkiit.raidersreckoningapp.view.navigation.BottomNavOptions.Companion.bottomNavOptions
import `in`.iotkiit.raidersreckoningapp.vm.DashBoardViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyTeamScreen(
    navController: NavController,
    viewModel: DashBoardViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavBar(navController = navController, bottomMenu = bottomNavOptions)
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize().safeContentPadding()
        ) {
            Text("Kunal Saha")
        }
    }
}