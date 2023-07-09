package app

import app.Month.*

class Calendar {
    fun dayOfWeek(date: Date): DayOfWeek {
        return DayOfWeek.entries[(date.day - 10) % 7]
    }

    fun advance(date: Date, days: Int): Date {
        val daysFromMonthStart = date.day + days

        val month = entries[date.month - 1]
        if (daysFromMonthStart - 1 == month.days) {
            if (month != December) return date.copy(month = date.month + 1, day = 1)
            else return date.copy(year = date.year + 1, month = 1, day = 1)
        }

        return date.copy(day = date.day + days)
    }
}

enum class DayOfWeek {
    Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday
}

enum class Month(val days: Int) {
    January(31), February(28), March(31),
    April(30), May(31), June(30),
    July(31), August(31), September(30),
    October(31), November(30), December(31)
}