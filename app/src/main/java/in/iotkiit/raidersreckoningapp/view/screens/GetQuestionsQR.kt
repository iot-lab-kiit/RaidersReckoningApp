package `in`.iotkiit.raidersreckoningapp.view.screens

import android.app.Activity
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.CompoundBarcodeView
import `in`.iotkiit.raidersreckoningapp.view.navigation.RaidersReckoningScreens

@Composable
fun GetQuestionsQR(
    navController: NavController
) {
    val context = LocalContext.current

    // Initialising the Scanner Module
    val compoundBarcodeView = remember {
        CompoundBarcodeView(context).apply {

            val capture = CaptureManager(context as Activity, this)
            capture.initializeFromIntent(context.intent, null)
            this.setStatusText("Scan a Product")

            // Starting the  Scanner Preview and decoding
            this.resume()
            capture.decode()

            this.decodeSingle { result ->
                if (result.text != null)
                    navController.navigate(RaidersReckoningScreens.QuestionScreen.route + "/${result.text}")
                else {
                    navController.navigate(RaidersReckoningScreens.DashBoardScreen.route)
                    Toast.makeText(context, "Invalid QR Code", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Parent Composable Function
    AndroidView(factory = { compoundBarcodeView })

    // This is used to pause or stop the Scanner after the Scanner finishes Scanning
    DisposableEffect(Unit) {
        onDispose {
            compoundBarcodeView.pause()
        }
    }
}