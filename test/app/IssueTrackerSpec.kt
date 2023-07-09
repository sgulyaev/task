package app

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.days

private val monday = Date(year = 2023, month = 7, day = 10, hour = 9, minute = 0)
private val tuesday = Date(year = 2023, month = 7, day = 11, hour = 9, minute = 0)
private val wednesday = Date(year = 2023, month = 7, day = 12, hour = 9, minute = 0)
private val thursday = Date(year = 2023, month = 7, day = 13, hour = 9, minute = 0)
private val friday = Date(year = 2023, month = 7, day = 14, hour = 9, minute = 0)
private val saturday = Date(year = 2023, month = 7, day = 15, hour = 9, minute = 0)
private val sunday = Date(year = 2023, month = 7, day = 16, hour = 9, minute = 0)

private val Date.nextWeek: Date get() = this.copy(day = day + 7)


class IssueTrackerSpec {
    val tracker = IssueTracker()

    @Test
    fun `should just add turnaround time in hours to the start date if no overflows`() {
        test(
            startDate = monday,
            turnaroundTime = 1.hour,
            expectedDueDate = monday.copy(hour = 10)
        )
    }

    @Test
    fun `should add day if overflow in hours, working day starts at 9-00 and ends at 17-00`() {
        test(
            startDate = monday.copy(hour = 16, minute = 59),
            turnaroundTime = 1.hour,
            expectedDueDate = tuesday.copy(hour = 9, minute = 59)
        )

        test(
            startDate = monday.copy(hour = 9, minute = 7),
            turnaroundTime = 1.workingDay + 1.hour,
            expectedDueDate = tuesday.copy(hour = 10, minute = 7)
        )

        test(
            startDate = monday.copy(hour = 9, minute = 7),
            turnaroundTime = 2.workingDay,
            expectedDueDate = wednesday.copy(hour = 9, minute = 7)
        )
    }

    @Test
    fun `should skip saturdays and sundays as they are not working days`() {
        test(
            startDate = friday.copy(hour = 16),
            turnaroundTime = 1.hour,
            expectedDueDate = monday.nextWeek
        )

        test(
            startDate = monday,
            turnaroundTime = 7.workingDay,
            expectedDueDate = wednesday.nextWeek
        )

        test(
            startDate = thursday.copy(hour = 10),
            turnaroundTime = 3.workingDay + 2.hour,
            expectedDueDate = tuesday.nextWeek.copy(hour = 12)
        )
    }

    @Test
    fun `big test from monday, 260 working days which will resualt in 364 calendar days`() {
        test(
            startDate = Date(year = 2023, month = 1, day = 2, hour = 12, minute = 12),
            turnaroundTime = 260.workingDay,
            expectedDueDate = Date(year = 2024, month = 1, day = 1, hour = 12, minute = 12)
        )
    }

    @Test
    fun `big test from monday, 520 working days which will resualt in (364 times 2) calendar days, also one of the year is leap year`() {
        test(
            startDate = Date(year = 2023, month = 1, day = 2, hour = 12, minute = 12),
            turnaroundTime = 520.workingDay,
            expectedDueDate = Date(year = 2024, month = 12, day = 30, hour = 12, minute = 12)
        )
    }


    private fun test(startDate: Date, turnaroundTime: Int, expectedDueDate: Date) {
        assertEquals(
            actual = tracker.calculateDueDate(startDate, turnaroundTime),
            expected = expectedDueDate
        )
    }


    private val Int.workingDay: Int get() = this * 8
    private val Int.hour: Int get() = this
}