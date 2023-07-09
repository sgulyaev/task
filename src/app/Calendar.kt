package app

class Calendar {
    fun dayOfWeek(date: Date): DayOfWeek {
        return DayOfWeek.entries[(date.day - 10) % 7]
    }

    fun advance(date: Date, days: Int): Date {
        return date.copy(day = date.day + days)
    }
}

enum class DayOfWeek {
    Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday
}