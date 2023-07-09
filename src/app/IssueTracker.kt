package app

class IssueTracker {
    private val MINUTES_PER_HOUR = 60

    private val DAY_START_HOUR = 9
    private val DAY_LENGTH = 8

    fun calculateDueDate(date: Date, turnaroundTime: Int): Date {
        val minutesFromDayStart = date.minute + turnaroundTime
        val workingHoursFromDayStart = (date.hour - DAY_START_HOUR) + minutesFromDayStart / MINUTES_PER_HOUR
        val extraDays = workingHoursFromDayStart / DAY_LENGTH

        return date.copy(
            minute = minutesFromDayStart % MINUTES_PER_HOUR,
            hour = DAY_START_HOUR + workingHoursFromDayStart % DAY_LENGTH,
            day = date.day + extraDays
        )
    }
}
