package `in`.iotkiit.raidersreckoningapp.view.screens

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.CompoundBarcodeView
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import `in`.iotkiit.raidersreckoningapp.view.navigation.RaidersReckoningScreens
import `in`.iotkiit.raidersreckoningapp.vm.DashBoardViewModel
import `in`.iotkiit.raidersreckoningapp.vm.LeaderboardViewModel

@Composable
fun JoinTeamScreen(
    navController: NavController,
    dashBoardViewModel: DashBoardViewModel = hiltViewModel()
) {
    val context = LocalContext.current

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
//                        navController.navigate(RaidersReckoningScreens.DashBoardScreen.route + "/${result.text}")
                        Toast.makeText(context, result.text, Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(context, "No Result", Toast.LENGTH_SHORT).show()
                }
            }
        }

        AndroidView(factory = { compoundBarcodeView })

        DisposableEffect(Unit) {
            onDispose {
                compoundBarcodeView.pause()
            }
        }
    } else {
        Toast.makeText(context, "Grant camera permission to scan.", Toast.LENGTH_SHORT).show()
    }
}