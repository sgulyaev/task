package app

import kotlin.test.Test
import kotlin.test.assertEquals

class IssueTrackerSpec {
    @Test
    fun `should add turnaround time in minutes to the start date`() {
        val tracker = IssueTracker()
        assertEquals(
            actual = tracker.calculateDueDate(Date(year = 2023, month = 7, day = 10, hour = 9, minute = 0), 1),
            expected = Date(year = 2023, month = 7, day = 10, hour = 9, minute = 1)
        )
    }
}