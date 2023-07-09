package app

import app.Month.*
import kotlin.math.min

class Calendar {
    private val DAYS_PER_WEEK = 7
    private val MONDAY = Date(year = 2023, month = July, day = 10, hour = 0, minute = 0)

    fun dayOfWeek(date: Date): DayOfWeek {
        val days = daysBetween(MONDAY, date)
        val dayOfWeekIndex = if (days % DAYS_PER_WEEK >= 0) days % DAYS_PER_WEEK else days % DAYS_PER_WEEK + DAYS_PER_WEEK
        return DayOfWeek.entries[dayOfWeekIndex]
    }

    fun advance(date: Date, days: Int): Date {
        var needDays = days
        var trackDate = date
        while (needDays != 0) {
            val availableDaysInMonth = daysInMonth(trackDate.month, trackDate.year) - trackDate.day + 1
            if (needDays >= availableDaysInMonth) {
                trackDate = advanceToStartOfNextMonth(trackDate)
                needDays -= availableDaysInMonth
            } else {
                trackDate = advanceInsideOneMonth(trackDate, needDays)
                needDays = 0
            }
        }
        return trackDate.copy(hour = date.hour, minute = date.minute)
    }

    private fun advanceInsideOneMonth(trackDate: Date, days: Int): Date {
        return trackDate.copy(day = trackDate.day + days)
    }

    private fun advanceToStartOfNextMonth(date: Date): Date {
        return Date(
            year = if (date.month == December) date.year + 1 else date.year,
            month = date.month.next(),
            day = 1,
            hour = 0,
            minute = 0
        )
    }

    private fun advanceToStartOfNextYear(date: Date): Date {
        return Date(year = date.year + 1, month = January, day = 1, hour = 0, minute = 0)
    }

    private fun daysBetween(startDate: Date, endDate: Date): Int {
        val minYear = min(startDate.year, endDate.year)
        return daysFromYearStart(endDate, minYear) - daysFromYearStart(startDate, minYear)
    }

    private fun daysFromYearStart(date: Date, year: Int): Int {
        var countDays = 0
        var trackDate = Date(year = year, month = January, day = 1, hour = 0, minute = 0)
        while (trackDate.year < date.year) {
            countDays += daysInYear(trackDate.year)
            trackDate = advanceToStartOfNextYear(trackDate)
        }
        while (trackDate.month < date.month) {
            countDays += daysInMonth(trackDate.month, trackDate.year)
            trackDate = advanceToStartOfNextMonth(trackDate)
        }
        countDays += date.day - 1
        return countDays
    }


    private fun daysInMonth(month: Month, year: Int): Int {
        if (month == February && isLeapYear(year)) return month.days + 1
        else return month.days
    }

    private fun daysInYear(year: Int): Int {
        if (isLeapYear(year)) return 366 else return 365
    }

    private fun isLeapYear(year: Int): Boolean {
        if (year % 400 == 0) return true
        if (year % 100 == 0) return false
        return year % 4 == 0
    }
}

enum class DayOfWeek {
    Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday
}

enum class Month(val days: Int) {
    January(31), February(28), March(31),
    April(30), May(31), June(30),
    July(31), August(31), September(30),
    October(31), November(30), December(31);

    fun next(): Month = entries[(this.ordinal + 1) % entries.size]
}

data class Date(val year: Int, val month: Month, val day: Int, val hour: Int, val minute: Int)