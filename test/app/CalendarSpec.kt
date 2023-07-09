package app

import kotlin.test.Test
import kotlin.test.assertEquals

class CalendarSpec {
    val calendar = Calendar()
    val date = Date(year = 2023, month = 1, day = 1, hour = 0, minute = 0)

    @Test
    fun `should add amount of days to the given date`() {
        test(date, 1, date.copy(day = 2))
        test(date, 10, date.copy(day = 11))
        test(date, 30, date.copy(day = 31))
    }

    @Test
    fun `should change month if overflow in days`() {
        test(date.copy(month = 1, day = 31), 1, date.copy(month = 2, day = 1))
        test(date.copy(month = 2, day = 28), 1, date.copy(month = 3, day = 1))
        test(date.copy(month = 3, day = 31), 1, date.copy(month = 4, day = 1))
        test(date.copy(month = 4, day = 30), 1, date.copy(month = 5, day = 1))
        test(date.copy(month = 5, day = 31), 1, date.copy(month = 6, day = 1))
        test(date.copy(month = 6, day = 30), 1, date.copy(month = 7, day = 1))
        test(date.copy(month = 7, day = 31), 1, date.copy(month = 8, day = 1))
        test(date.copy(month = 8, day = 31), 1, date.copy(month = 9, day = 1))
        test(date.copy(month = 9, day = 30), 1, date.copy(month = 10, day = 1))
        test(date.copy(month = 10, day = 31), 1, date.copy(month = 11, day = 1))
        test(date.copy(month = 11, day = 30), 1, date.copy(month = 12, day = 1))
    }

    @Test
    fun `should change year if overflow in months`() {
        test(date.copy(month = 12, day = 31), 1, date.copy(year = 2024, month = 1, day = 1))
    }

    @Test
    fun `should work properly in Febuary in leap years`() {
        test(date.copy(year = 2024, month = 2, day = 28), 1, date.copy(year = 2024, month = 2, day = 29))
        test(date.copy(year = 2024, month = 2, day = 29), 1, date.copy(year = 2024, month = 3, day = 1))
        test(date.copy(year = 2028, month = 2, day = 29), 1, date.copy(year = 2028, month = 3, day = 1))
        test(date.copy(year = 2400, month = 2, day = 29), 1, date.copy(year = 2400, month = 3, day = 1))
        test(date.copy(year = 2800, month = 2, day = 29), 1, date.copy(year = 2800, month = 3, day = 1))
    }

    @Test
    fun `should work properly in Febuary in not leap years`() {
        test(date.copy(year = 2001, month = 2, day = 28), 1, date.copy(year = 2001, month = 3, day = 1))
        test(date.copy(year = 2100, month = 2, day = 28), 1, date.copy(year = 2100, month = 3, day = 1))
        test(date.copy(year = 2200, month = 2, day = 28), 1, date.copy(year = 2200, month = 3, day = 1))
        test(date.copy(year = 2300, month = 2, day = 28), 1, date.copy(year = 2300, month = 3, day = 1))
    }


    fun test(startDate: Date, advanceAmountInDays: Int, expectedEndDate: Date) {
        assertEquals(expectedEndDate, calendar.advance(startDate, advanceAmountInDays))
    }
}