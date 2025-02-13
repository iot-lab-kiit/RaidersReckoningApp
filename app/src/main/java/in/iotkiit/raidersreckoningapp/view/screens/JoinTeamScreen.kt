package `in`.iotkiit.raidersreckoningapp.view.screens

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.CompoundBarcodeView
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import `in`.iotkiit.raidersreckoningapp.data.model.JoinTeamBody
import `in`.iotkiit.raidersreckoningapp.state.UiState
import `in`.iotkiit.raidersreckoningapp.view.navigation.RaidersReckoningScreens
import `in`.iotkiit.raidersreckoningapp.vm.DashBoardViewModel
import `in`.iotkiit.raidersreckoningapp.vm.TeamViewModel

@Composable
fun JoinTeamScreen(
    navController: NavController,
    teamViewModel: TeamViewModel = hiltViewModel()
) {
    val joinTeamState = teamViewModel.joinTeamState.collectAsState().value

    val context = LocalContext.current

    LaunchedEffect(joinTeamState) {
        when(joinTeamState){
            is UiState.Success -> {
                Toast.makeText(context, "Team joined", Toast.LENGTH_SHORT).show()
                navController.navigate(RaidersReckoningScreens.DashBoardScreen.route) {
                    popUpTo(RaidersReckoningScreens.JoinTeamScreen.route) {
                        saveState = true
                        inclusive = true
                    }
                }
            }
            is UiState.Failed -> {
                Toast.makeText(context, joinTeamState.message, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    val auth = FirebaseAuth.getInstance()

    val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    var isPermissionGranted by remember {
        mutableStateOf(sharedPreferences.getBoolean("isPermissionGranted", false))
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                isPermissionGranted = true
                sharedPreferences.edit().putBoolean("isPermissionGranted", true).apply()
            } else {
                Toast.makeText(context, "Camera permission denied.", Toast.LENGTH_SHORT).show()
            }
        }
    )

    LaunchedEffect(isPermissionGranted) {
        if (!isPermissionGranted) {
            cameraPermissionLauncher.launch("android.permission.CAMERA")
        }
    }


    if (isPermissionGranted) {
        val compoundBarcodeView = remember {
            CompoundBarcodeView(context).apply {
                val capture = CaptureManager(context as Activity, this)
                capture.initializeFromIntent(context.intent, null)
                this.setStatusText("Scan a Team QR")

                // Start the scanner preview and decoding
                this.resume()
                capture.decode()

                this.decodeSingle { result ->
                    if (result.text != null)
                        teamViewModel.joinTeam(
                            JoinTeamBody(
                                result.text
                            )
                        )
                    else
                        Toast.makeText(context, "No Result", Toast.LENGTH_SHORT).show()
                }
            }
        }

        AndroidView(factory = { compoundBarcodeView })

        DisposableEffect(Unit) {
            onDispose {
                try {
                    auth.signOut()
                    navController.navigate(RaidersReckoningScreens.LoginScreen.route) {
                        popUpTo(RaidersReckoningScreens.JoinTeamScreen.route) { inclusive = true }
                    }
                } catch (e: Exception) {
                    Log.e("Logout", "Logout failed: ${e.message}")
                }
                compoundBarcodeView.pause()
            }
        }
    } else {
        Toast.makeText(context, "Grant camera permission to scan.", Toast.LENGTH_SHORT).show()
    }
}