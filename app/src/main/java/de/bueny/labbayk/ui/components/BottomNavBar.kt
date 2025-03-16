package de.bueny.labbayk.ui.components

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import de.bueny.labbayk.ui.navigation.NavigationItem

@Composable
fun BottomNavigationBar(
    navHostController: NavHostController,
    selectedNavItem: NavigationItem,
    onItemSelected: (NavigationItem) -> Unit
) {
    BottomAppBar(
        containerColor = Color(0xFFA1B57D)
    ) {
        NavigationItem.entries.forEach {
            NavigationBarItem(
                selected = selectedNavItem == it,
                onClick = { onItemSelected(it) },
                icon = { Icon(imageVector = it.icon, contentDescription = it.name) },
                label = { Text(text = stringResource(id = it.label)) }
            )
        }
    }
}

//Color(0xFFA1B57D)