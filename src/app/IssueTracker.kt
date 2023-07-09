package app

class IssueTracker {
    private val DAY_START_HOUR = 9
    private val DAY_LENGTH = 8

    fun calculateDueDate(date: Date, turnaroundTimeInHours: Int): Date {
        val workingHoursFromDayStart = (date.hour - DAY_START_HOUR) + turnaroundTimeInHours
        val extraDays = workingHoursFromDayStart / DAY_LENGTH

        return date.copy(
            hour = DAY_START_HOUR + workingHoursFromDayStart % DAY_LENGTH,
            day = date.day + extraDays
        )
    }
}
