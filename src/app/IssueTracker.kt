package app

class IssueTracker(private val calendar: Calendar = Calendar()) {
    private val DAY_START_HOUR = 9
    private val DAY_LENGTH = 8

    private val DAYS_PER_WEEK = 7
    private val WORKING_DAYS_PER_WEEK = 5

    fun calculateDueDate(date: Date, turnaroundTimeInHours: Int): Date {
        val workingHoursFromDayStart = (date.hour - DAY_START_HOUR) + turnaroundTimeInHours
        val extraWorkingDays = workingHoursFromDayStart / DAY_LENGTH
        val dueDate = calendar.advance(date, extraCalendarDays(date, extraWorkingDays))

        return dueDate.copy(
            hour = DAY_START_HOUR + workingHoursFromDayStart % DAY_LENGTH,
        )
    }

    private fun extraCalendarDays(date: Date, needWorkingDays: Int): Int {
        val dayOfWeek = calendar.dayOfWeek(date)
        val workingDaysFromWeekStart = dayOfWeek.ordinal + needWorkingDays
        val extraWeeks = workingDaysFromWeekStart / WORKING_DAYS_PER_WEEK
        return extraWeeks * DAYS_PER_WEEK + workingDaysFromWeekStart % WORKING_DAYS_PER_WEEK - dayOfWeek.ordinal
    }
}
