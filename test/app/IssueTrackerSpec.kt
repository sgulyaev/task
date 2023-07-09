package app

import kotlin.test.Test
import kotlin.test.assertEquals

private val monday = Date(year = 2023, month = 7, day = 10, hour = 9, minute = 0)
private val tuesday = Date(year = 2023, month = 7, day = 11, hour = 9, minute = 0)
private val wednesday = Date(year = 2023, month = 7, day = 12, hour = 9, minute = 0)

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

    private fun test(startDate: Date, turnaroundTime: Int, expectedDueDate: Date) {
        assertEquals(
            actual = tracker.calculateDueDate(startDate, turnaroundTime),
            expected = expectedDueDate
        )
    }


    private val Int.workingDay: Int get() = this * 8
    private val Int.hour: Int get() = this
}