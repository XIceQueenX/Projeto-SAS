package com.projeto.sas.features.employees.Deliveries.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.projeto.sas.R
import com.projeto.sas.features.components.CustomTabView
import com.projeto.sas.features.components.TabItem
import com.projeto.sas.features.components.calendar.Calendar
import com.projeto.sas.ui.theme.Background
import com.projeto.sas.ui.theme.DarkGreen
import com.projeto.sas.ui.theme.FontStyle.ExtraBoldXL
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun DonationsScreen() {

    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    val mockDeliveries = remember {
        setOf(
            LocalDate.now().plusDays(3),
            LocalDate.now().plusDays(7),
            LocalDate.now().plusDays(10),
            LocalDate.now().minusDays(2)
        )
    }

    val tabs = remember {
        listOf(
            TabItem(
                title = "Pendentes",
                content = { val mockCards = listOf(
                    CardItem(1, "Delivery #1001", "Client: John Doe", "3 boxes of apples", CardItem.State.DELIVERED),
                    CardItem(2, "Delivery #1002", "Client: Jane Smith", "5 packs of cereal", CardItem.State.TO_PREPARE),
                    CardItem(3, "Delivery #1003", "Client: Bob Johnson", "2 cartons of milk", CardItem.State.POSTPONED)
                )
                    CardListScreen(cards = mockCards) }
            ),
            TabItem(
                title = "Concluídas",
                content = { }
            )
        )
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ){
        Text(
            text = stringResource(R.string.products),
            style = ExtraBoldXL,
            color = DarkGreen,
            modifier = Modifier.fillMaxWidth()
        )

        Calendar(
            modifier = Modifier.fillMaxWidth(),
            initialMonth = YearMonth.now(),
            datesWithEvents = mockDeliveries,
            onDateClick = { date ->
                selectedDate = date
            },
            onMonthChange = { yearMonth ->
                println("Month changed to: $yearMonth")
            }
        )

        CustomTabView(
            tabs = tabs,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}


@Preview
@Composable
fun DonationsScreenPreview(){
    DonationsScreen()
}