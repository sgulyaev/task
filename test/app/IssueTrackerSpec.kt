package app

import kotlin.test.Test
import kotlin.test.assertEquals

private val monday = Date(year = 2023, month = 7, day = 10, hour = 9, minute = 0)

class IssueTrackerSpec {
    val tracker = IssueTracker()

    @Test
    fun `should just add turnaround time in minutes to the start date if no overflows`() {
        assertEquals(
            actual = tracker.calculateDueDate(monday, 1),
            expected = monday.copy(minute = 1)
        )
    }


    @Test
    fun `should add hours if there is overflow in minutes`() {
        assertEquals(
            actual = tracker.calculateDueDate(monday.copy(minute = 59), 1),
            expected = monday.copy(hour = 10, minute = 0)
        )

        assertEquals(
            actual = tracker.calculateDueDate(monday, 60),
            expected = monday.copy(hour = 10, minute = 0)
        )

        assertEquals(
            actual = tracker.calculateDueDate(monday.copy(minute = 55), 67),
            expected = monday.copy(hour = 11, minute = 2)
        )
    }

}