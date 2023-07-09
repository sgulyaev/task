package app

import kotlin.test.Test
import kotlin.test.assertEquals

private val monday = Date(year = 2023, month = 7, day = 10, hour = 9, minute = 0)

class IssueTrackerSpec {
    val tracker = IssueTracker()

    @Test
    fun `should just add turnaround time in minutes to the start date if no overflows`() {
        test(
            startDate = monday,
            turnaroundTime = 1,
            expectedDueDate = monday.copy(minute = 1)
        )
    }


    @Test
    fun `should add hours if there is overflow in minutes`() {
        test(
            startDate = monday.copy(minute = 59),
            turnaroundTime = 1,
            expectedDueDate = monday.copy(hour = 10, minute = 0)
        )

        test(
            startDate = monday,
            turnaroundTime = 60,
            expectedDueDate = monday.copy(hour = 10, minute = 0)
        )

        test(
            startDate = monday.copy(minute = 55),
            turnaroundTime = 67,
            expectedDueDate = monday.copy(hour = 11, minute = 2)
        )
    }

    private fun test(startDate: Date, turnaroundTime: Int, expectedDueDate: Date) {
        assertEquals(
            actual = tracker.calculateDueDate(startDate, turnaroundTime),
            expected = expectedDueDate
        )
    }


}