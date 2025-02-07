package `in`.iotkiit.raidersreckoningapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import `in`.iotkiit.raidersreckoningapp.ui.theme.RaidersReckoningAppTheme
import `in`.iotkiit.raidersreckoningapp.view.navigation.RaidersReckoningNavigation

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RaidersReckoningAppTheme {
                RaidersReckoningNavigation()
            }
        }
    }
}
