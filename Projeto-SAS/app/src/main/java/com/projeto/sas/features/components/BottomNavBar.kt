package com.projeto.sas.features.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.projeto.sas.ui.theme.DarkGreen
import com.projeto.sas.ui.theme.FontStyle.MediumXS
import com.projeto.sas.ui.theme.Green

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector,
)

@Composable
fun BottomNavBar(
    items: List<BottomNavItem>,
    selectedRoute: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    selectedBackgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    selectedContentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = backgroundColor,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                BottomNavItemView(
                    item = item,
                    isSelected = selectedRoute == item.route,
                    onClick = { onItemSelected(item.route) },
                    selectedBackgroundColor = selectedBackgroundColor,
                    selectedContentColor = selectedContentColor
                )
            }
        }
    }
}

@Composable
private fun BottomNavItemView(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    selectedBackgroundColor: Color,
    selectedContentColor: Color
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) selectedBackgroundColor else Color.Transparent,
        animationSpec = tween(durationMillis = 300),
        label = "background_color"
    )

    val contentColor by animateColorAsState(
        targetValue =  selectedContentColor,
        animationSpec = tween(durationMillis = 300),
        label = "content_color"
    )

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Icon
            Icon(
                imageVector = item.icon,
                contentDescription = item.label,
                tint = contentColor,
                modifier = Modifier.size(24.dp)
            )

            // Animated text appearance
            AnimatedVisibility(
                visible = isSelected,
                enter = fadeIn(
                    animationSpec = tween(
                        durationMillis = 250,
                        easing = LinearOutSlowInEasing
                    )
                ) + expandHorizontally(
                    animationSpec = tween(
                        durationMillis = 250,
                        easing = LinearOutSlowInEasing
                    ),
                    expandFrom = Alignment.Start
                ),
                exit = fadeOut(
                    animationSpec = tween(
                        durationMillis = 250,
                        easing = LinearOutSlowInEasing
                    )
                ) + shrinkHorizontally(
                    animationSpec = tween(
                        durationMillis = 250,
                        easing = LinearOutSlowInEasing
                    ),
                    shrinkTowards = Alignment.Start
                )
            ) {
                Text(
                    text = item.label,
                    color = contentColor,
                    style = MediumXS,
                    modifier = Modifier.padding(start = 8.dp),
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun CustomStyledBottomNavExample() {
    var selectedRoute by remember { mutableStateOf("home") }

    val navItems = remember {
        listOf(
            BottomNavItem(
                route = "Home" ,
                label = "Home",
                icon = Icons.Outlined.Home,
            ),
            BottomNavItem(
                route = "Deliveries",
                label = "Deliveries",
                icon = Icons.Outlined.Home,
            ),
            BottomNavItem(
                route = "Products",
                label = "Products",
                icon = Icons.Outlined.Favorite,
            ),
            BottomNavItem(
                route = "Recipients",
                label = "Recipients",
                icon = Icons.Outlined.AccountCircle,
            )
        )
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                items = navItems,
                selectedRoute = selectedRoute,
                onItemSelected = { route ->
                    selectedRoute = route
                },
                backgroundColor = Green,
                selectedBackgroundColor = DarkGreen,
                selectedContentColor = Color.White
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
        ) {
            Text("Hello, world!")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomStyledBottomNavPreview() {
    CustomStyledBottomNavExample()
}

