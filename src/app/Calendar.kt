package app

import app.Month.*

class Calendar {
    fun dayOfWeek(date: Date): DayOfWeek {
        return DayOfWeek.entries[(date.day - 10) % 7]
    }

    fun advance(date: Date, days: Int): Date {
        var month = Month.fromInt(date.month)
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
        return date.copy(year = year, month = month.toInt(), day = day)
    }

    private fun daysInMonth(month: Month, year: Int): Int {
        if (month == February && isLeapYear(year)) return month.days + 1
        else return month.days
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

    fun toInt(): Int = this.ordinal + 1

    companion object {
        fun fromInt(num: Int): Month = entries[num - 1]
    }
}

data class Date(val year: Int, val month: Int, val day: Int, val hour: Int, val minute: Int)