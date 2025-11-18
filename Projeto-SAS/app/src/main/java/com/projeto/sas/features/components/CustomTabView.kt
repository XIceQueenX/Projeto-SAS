package com.projeto.sas.features.components

import androidx.compose.material3.Scaffold
import androidx.compose.ui.layout.positionInParent

import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.projeto.sas.ui.theme.DarkGreen
import com.projeto.sas.ui.theme.FontStyle.BoldMD

data class TabItem(
    val title: String,
    val content: @Composable () -> Unit
)

@Composable
fun CustomTabView(
    tabs: List<TabItem>,
    modifier: Modifier = Modifier,
    selectedTabIndex: Int = 0,
    onTabSelected: (Int) -> Unit = {},
    tabHeight: Dp = 48.dp,
    underlineHeight: Dp = 2.dp
) {
    require(tabs.isNotEmpty()) { "Tabs list cannot be empty" }

    var currentTab by remember { mutableIntStateOf(selectedTabIndex.coerceIn(0, tabs.lastIndex)) }

    // Store tab positions for underline animation
    val tabPositions = remember { mutableStateMapOf<Int, Float>() }
    val tabWidths = remember { mutableStateMapOf<Int, Float>() }

    val density = LocalDensity.current

    // Animated values for underline
    val animatedOffset by animateFloatAsState(
        targetValue = tabPositions[currentTab] ?: 0f,
        animationSpec = tween(
            durationMillis = 250,
            easing = FastOutSlowInEasing
        ),
        label = "underline_offset"
    )

    val animatedWidth by animateFloatAsState(
        targetValue = tabWidths[currentTab] ?: 0f,
        animationSpec = tween(
            durationMillis = 250,
            easing = FastOutSlowInEasing
        ),
        label = "underline_width"
    )


    Column(modifier = modifier.fillMaxWidth()) {
        // Tab Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(tabHeight)
                .drawBehind {
                    // Draw underline under the selected tab
                    val offsetX = animatedOffset
                    val width = animatedWidth
                    drawLine(
                        color = DarkGreen,
                        start = Offset(offsetX, size.height - underlineHeight.toPx()),
                        end = Offset(offsetX + width, size.height - underlineHeight.toPx()),
                        strokeWidth = underlineHeight.toPx()
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            var rowOffsetX by remember { mutableStateOf(0f) }

            Row(
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        rowOffsetX = coordinates.positionInParent().x
                    }
            ) {
                tabs.forEachIndexed { index, tab ->
                    TabHeader(
                        title = tab.title,
                        onClick = { currentTab = index },
                        modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                with(density) {
                                    val extraWidth = 24.dp.toPx()
                                    val width = coordinates.size.width.toFloat() + extraWidth
                                    val offset = rowOffsetX + coordinates.positionInParent().x - extraWidth / 2f
                                    tabPositions[index] = offset
                                    tabWidths[index] = width
                                }
                            }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            tabs[currentTab].content()
        }
    }

}

@Composable
private fun TabHeader(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = BoldMD,
            color = Color.Black
        )
    }
}

@Preview
@Composable
fun TwoTabsExample() {
    val tabs = remember {
        listOf(
            TabItem(
                title = "Tab One",
                content = {  }
            ),
            TabItem(
                title = "Tab Two",
                content = { }
            )
        )
    }

    Scaffold { paddingValues ->
        CustomTabView(
            tabs = tabs,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}