package com.projeto.sas.features.employees.Deliveries.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.projeto.sas.ui.theme.CardBackground
import com.projeto.sas.ui.theme.DarkGreen
import com.projeto.sas.ui.theme.FontStyle.BoldSM
import com.projeto.sas.ui.theme.FontStyle.MediumXS
import com.projeto.sas.ui.theme.Gray
import com.projeto.sas.ui.theme.Hint
import com.projeto.sas.ui.theme.LightGreen
import com.projeto.sas.ui.theme.Yellow

data class CardItem(
    val id: Int,
    val title: String,
    val subtitle: String,
    val description: String,
    val state: State
) {
    enum class State {
        DELIVERED,
        TO_PREPARE,
        POSTPONED
    }
}

@Composable
fun CardItemView(
    item: CardItem,
    onClick: (CardItem) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(item) },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(10.dp)
                    .fillMaxHeight()
                    .background(
                        when (item.state) {
                            CardItem.State.DELIVERED -> {
                                DarkGreen
                            }

                            CardItem.State.TO_PREPARE -> {
                                LightGreen
                            }

                            CardItem.State.POSTPONED -> {
                              Yellow
                            }
                        }
                    )
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 12.dp, horizontal = 16.dp)
            ) {
                Text(
                    text = item.title,
                    style = BoldSM,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = item.subtitle,
                    style = MediumXS,
                    color = Hint
                )
            }

            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Favorite",
                    tint = Color.Red
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CardItemViewPreview() {
    val mockItemDelivered = CardItem(
        id = 1,
        title = "Order #001",
        subtitle = "Client: John Doe",
        description = "2 boxes of product X",
        state = CardItem.State.DELIVERED
    )

    val mockItemToPrepare = CardItem(
        id = 2,
        title = "Order #002",
        subtitle = "Client: Jane Smith",
        description = "5 units of product Y",
        state = CardItem.State.TO_PREPARE
    )

    val mockItemPostponed = CardItem(
        id = 3,
        title = "Order #003",
        subtitle = "Client: Bob Johnson",
        description = "1 package of product Z",
        state = CardItem.State.POSTPONED
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        CardItemView(item = mockItemDelivered)
        CardItemView(item = mockItemToPrepare)
        CardItemView(item = mockItemPostponed)
    }
}

