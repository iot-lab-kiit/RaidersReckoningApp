package `in`.iotkiit.raidersreckoningapp.view.components.home


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun GameCard(imageUrl: String, title: String, code: String, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Image(painter = painterResource(R.drawable.nuketown), contentDescription = "Game Image")
            Spacer(modifier = Modifier.height(150.dp))
            Text("Nuketown", fontSize = 20.sp, color = Color.White)
            Text("C001", fontSize = 14.sp, color = Color.Gray)
        }
    }
}
