package `in`.iotkiit.raidersreckoningapp.view.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD
import `in`.iotkiit.raidersreckoningapp.view.navigation.BottomNavOptions.Companion.bottomNavOptions

/**
 * This is the Bottom Navigation Bar Composable function.
 *
 * @param modifier Default Modifier so that the Parent Function can send something if needed
 * @param navController This is the NavController which we will be using to navigate the Screens
 * @param bottomMenu This Menu Contains the Data about the various routes in the Bottom Route
 */

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    bottomMenu: List<BottomNavOptions>
) {
    NavigationBar(
        containerColor = GreenCOD.copy(.1f),
        modifier = Modifier
            .clip(CircleShape)
    ) {
        val backStackEntry = navController.currentBackStackEntryAsState()

        bottomMenu.forEach { menuItem ->
            val selected =
                (menuItem.route == backStackEntry.value?.destination?.parent?.route) || (menuItem.route == backStackEntry.value?.destination?.route)

            NavigationBarItem(
                selected = selected,
                onClick = {
                    menuItem.onOptionClicked(navController)
                },
                icon = {
                    Image(
                        painter = painterResource(
                            id = if (selected) menuItem.selectedIcon else menuItem.unselectedIcon
                        ),
                        contentDescription = menuItem.labelOfIcon,
                        modifier = Modifier.size(24.dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}


@Preview()
@Composable
private fun DefaultPreview() {
    BottomNavBar(
        navController = rememberNavController(), bottomMenu = bottomNavOptions
    )
}