package com.projeto.sas.features.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.YearMonth
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.projeto.sas.ui.theme.DarkGreen
import com.projeto.sas.ui.theme.FontStyle
import com.projeto.sas.ui.theme.Hint
import com.projeto.sas.ui.theme.LightGreen
import com.projeto.sas.ui.theme.Yellow

@Composable
fun Calendar(
    modifier: Modifier = Modifier,
    initialMonth: YearMonth = YearMonth.now(),
    datesWithEvents: Set<LocalDate> = emptySet(),
    onDateClick: (LocalDate) -> Unit = {},
    onMonthChange: (YearMonth) -> Unit = {}
) {
    var currentMonth by remember { mutableStateOf(initialMonth) }
    val calendarMonth by remember(currentMonth, datesWithEvents) {
        derivedStateOf {
            CalendarUtils.generateCalendarMonth(currentMonth, datesWithEvents)
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        CalendarHeader(
            monthYearText = CalendarUtils.getMonthYearText(currentMonth),
            onPreviousMonth = {
                currentMonth = currentMonth.minusMonths(1)
                onMonthChange(currentMonth)
            },
            onNextMonth = {
                currentMonth = currentMonth.plusMonths(1)
                onMonthChange(currentMonth)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        DaysOfWeekHeader()

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider()

        CalendarGrid(
            calendarMonth = calendarMonth,
            onDateClick = onDateClick
        )
    }
}

@Composable
private fun CalendarHeader(
    monthYearText: String,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousMonth) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Previous Month"
            )
        }

        Text(
            text = monthYearText,
            style = FontStyle.BoldLG,
            color = DarkGreen

        )

        IconButton(onClick = onNextMonth) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Next Month"
            )
        }
    }
}

@Composable
private fun DaysOfWeekHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        //TODO: Get short days name dynamically
        listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach { day ->
            Text(
                text = day,
                modifier = Modifier.width(40.dp),
                textAlign = TextAlign.Center,
                style = FontStyle.RegularXS,
                fontWeight = FontWeight.Bold,
                color = Hint
            )
        }
    }
}

@Composable
private fun CalendarGrid(
    calendarMonth: CalendarMonth,
    onDateClick: (LocalDate) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        calendarMonth.weeks.forEach { week ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                week.forEach { day ->
                    if (day.isCurrentMonth) {
                        CalendarDayCell(
                            day = day,
                            onClick = { onDateClick(day.date) }
                        )
                    } else {
                        Box(modifier = Modifier.size(40.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun CalendarDayCell(
    day: CalendarDay,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .background(Color.Transparent)
            .then(
                if (day.hasEvent) {
                    Modifier
                        .background(LightGreen)
                        .border(
                            width = 2.dp,
                            color = DarkGreen,
                            shape = CircleShape
                        )
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            style = FontStyle.MediumSM,
            fontSize = 14.sp,
            color = if (day.isToday) {
                Yellow
            } else {
                Color.Black
            },
            fontWeight =  FontWeight.Bold
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen() {
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    val datesWithEvents = remember {
        setOf(
            LocalDate.now().plusDays(3),
            LocalDate.now().plusDays(7),
            LocalDate.now().plusDays(10),
            LocalDate.now().minusDays(2),
            LocalDate.now()
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calendar") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Calendar(
                modifier = Modifier.fillMaxWidth(),
                initialMonth = YearMonth.now(),
                datesWithEvents = datesWithEvents,
                onDateClick = { date ->
                    selectedDate = date
                },
                onMonthChange = { yearMonth ->
                    println("Month changed to: $yearMonth")
                }
            )

            // Display selected date
            selectedDate?.let { date ->
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Selected Date",
                            style = FontStyle.BoldLG
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = date.toString(),
                            style =  FontStyle.bodyLarge
                        )
                        if (datesWithEvents.contains(date)) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Has Event",
                                style = FontStyle.MediumSM,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CalendarScreenPreview() {
    CalendarScreen()
}
