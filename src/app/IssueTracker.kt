package app

import app.DayOfWeek.*

class IssueTracker(private val calendar: Calendar = Calendar()) {
    private val DAY_START_HOUR = 9
    private val DAY_END_HOUR = 17
    private val DAY_LENGTH = DAY_END_HOUR - DAY_START_HOUR

    private val DAYS_PER_WEEK = 7
    private val WORKING_DAYS_PER_WEEK = 5

    fun calculateDueDate(submitDate: Date, turnaroundTimeInHours: Int): Date {
        checkArguments(submitDate, turnaroundTimeInHours)

        val workingHoursFromDayStart = (submitDate.hour - DAY_START_HOUR) + turnaroundTimeInHours
        val extraWorkingDays = workingHoursFromDayStart / DAY_LENGTH
        val dueDate = calendar.advance(submitDate, extraCalendarDays(submitDate, extraWorkingDays))

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

    private fun checkArguments(submitDate: Date, turnaroundTimeInHours: Int) {
        if (turnaroundTimeInHours <= 0) throw IllegalArgumentException("turnaround time should be positive, but was $turnaroundTimeInHours")
        if (setOf(Saturday, Sunday).contains(calendar.dayOfWeek(submitDate))) {
            throw IllegalArgumentException("submit date should be working day but was ${calendar.dayOfWeek(submitDate)}")
        }
        if (submitDate.hour !in DAY_START_HOUR until DAY_END_HOUR) {
            throw IllegalArgumentException("submit date should be in working hours but was ${submitDate.hour}")
        }
    }
}
