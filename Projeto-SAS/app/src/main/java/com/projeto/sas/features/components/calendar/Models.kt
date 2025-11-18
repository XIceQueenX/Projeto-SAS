package com.projeto.sas.features.components.calendar

import java.time.LocalDate
import java.time.YearMonth

data class CalendarDay(
    val date: LocalDate,
    val isCurrentMonth: Boolean,
    val isToday: Boolean,
    val hasEvent: Boolean = false
)

data class CalendarMonth(
    val yearMonth: YearMonth,
    val weeks: List<List<CalendarDay>>
)