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
}