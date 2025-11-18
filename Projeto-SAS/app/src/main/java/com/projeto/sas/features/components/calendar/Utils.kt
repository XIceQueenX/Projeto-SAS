package com.projeto.sas.features.components.calendar

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.TemporalAdjusters

object CalendarUtils {

    fun generateCalendarMonth(
        yearMonth: YearMonth,
        datesWithEvents: Set<LocalDate> = emptySet()
    ): CalendarMonth {
        val firstDayOfMonth = yearMonth.atDay(1)
        val lastDayOfMonth = yearMonth.atEndOfMonth()
        val today = LocalDate.now()

        val startDate = firstDayOfMonth.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))

        val endDate = lastDayOfMonth.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY))

        val weeks = mutableListOf<List<CalendarDay>>()
        var currentWeek = mutableListOf<CalendarDay>()
        var currentDate = startDate

        while (!currentDate.isAfter(endDate)) {
            val day = CalendarDay(
                date = currentDate,
                isCurrentMonth = currentDate.month == yearMonth.month,
                isToday = currentDate == today,
                hasEvent = datesWithEvents.contains(currentDate)
            )

            currentWeek.add(day)

            if (currentWeek.size == 7) {
                weeks.add(currentWeek.toList())
                currentWeek = mutableListOf()
            }

            currentDate = currentDate.plusDays(1)
        }

        return CalendarMonth(yearMonth = yearMonth, weeks = weeks)
    }

    fun getMonthYearText(yearMonth: YearMonth): String {
        return "${yearMonth.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${yearMonth.year}"
    }
}