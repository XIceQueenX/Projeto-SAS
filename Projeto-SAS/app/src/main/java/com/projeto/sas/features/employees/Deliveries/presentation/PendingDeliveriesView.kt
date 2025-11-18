package com.projeto.sas.features.employees.Deliveries.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CardListScreen(
    cards: List<CardItem>,
    onCardClick: (CardItem) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(cards, key = { it.id }) { item ->
            CardItemView(item = item, onClick = onCardClick)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardListScreenPreview() {
    val mockCards = listOf(
        CardItem(1, "Delivery #1001", "Client: John Doe", "3 boxes of apples", CardItem.State.DELIVERED),
        CardItem(2, "Delivery #1002", "Client: Jane Smith", "5 packs of cereal", CardItem.State.TO_PREPARE),
        CardItem(3, "Delivery #1003", "Client: Bob Johnson", "2 cartons of milk", CardItem.State.POSTPONED)
    )

    CardListScreen(cards = mockCards)
}
