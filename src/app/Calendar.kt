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
        var month = date.month
        var year = date.year
        var day = date.day
        var needDays = days
        while (needDays != 0) {
            val availableDaysInMonth = daysInMonth(month, year) - day + 1
            if (needDays >= availableDaysInMonth) {
                needDays -= availableDaysInMonth
                day = 1
                month = month.next()
                if (month == January) year += 1
            } else {
                day += needDays
                needDays = 0
            }
        }
        return date.copy(year = year, month = month, day = day)
    }

    private fun daysBetween(startDate: Date, endDate: Date): Int {
        val minYear = min(startDate.year, endDate.year)
        if (startDate < endDate) return daysFromYearStart(endDate, minYear) - daysFromYearStart(startDate, minYear)
        else return daysFromYearStart(startDate, minYear) - daysFromYearStart(endDate, minYear)
    }

    private fun daysFromYearStart(date: Date, year: Int): Int {
        var year = year
        var month = January
        var countDays = 0
        while (year < date.year) {
            countDays += daysInYear(year)
            year += 1
        }
        while (month < date.month) {
            countDays += daysInMonth(month, year)
            month = month.next()
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

    companion object {
        private val dateComparator =
            compareBy<Date> { it.year }.thenBy { it.month }.thenBy { it.day }.thenBy { it.hour }.thenBy { it.minute }
    }

    private operator fun Date.compareTo(other: Date): Int = dateComparator.compare(this, other)
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