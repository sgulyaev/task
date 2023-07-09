package app

import app.Month.*
import kotlin.test.Test
import kotlin.test.assertEquals

class CalendarAdvanceDateSpec {
    val calendar = Calendar()
    val date = Date(year = 2023, month = January, day = 1, hour = 0, minute = 0)

    @Test
    fun `should add amount of days to the given date`() {
        test(date, 1, date.copy(day = 2))
        test(date, 10, date.copy(day = 11))
        test(date, 30, date.copy(day = 31))
    }

    @Test
    fun `should change month if overflow in days`() {
        test(date.copy(month = January, day = 31), 1, date.copy(month = February, day = 1))
        test(date.copy(month = February, day = 28), 1, date.copy(month = March, day = 1))
        test(date.copy(month = March, day = 31), 1, date.copy(month = April, day = 1))
        test(date.copy(month = April, day = 30), 1, date.copy(month = May, day = 1))
        test(date.copy(month = May, day = 31), 1, date.copy(month = June, day = 1))
        test(date.copy(month = June, day = 30), 1, date.copy(month = July, day = 1))
        test(date.copy(month = July, day = 31), 1, date.copy(month = August, day = 1))
        test(date.copy(month = August, day = 31), 1, date.copy(month = September, day = 1))
        test(date.copy(month = September, day = 30), 1, date.copy(month = October, day = 1))
        test(date.copy(month = October, day = 31), 1, date.copy(month = November, day = 1))
        test(date.copy(month = November, day = 30), 1, date.copy(month = December, day = 1))
    }

    @Test
    fun `should change year if overflow in months`() {
        test(date.copy(month = December, day = 31), 1, date.copy(year = 2024, month = January, day = 1))
    }

    @Test
    fun `should work properly in Febuary in leap years`() {
        test(date.copy(year = 2024, month = February, day = 28), 1, date.copy(year = 2024, month = February, day = 29))
        test(date.copy(year = 2024, month = February, day = 29), 1, date.copy(year = 2024, month = March, day = 1))
        test(date.copy(year = 2028, month = February, day = 29), 1, date.copy(year = 2028, month = March, day = 1))
        test(date.copy(year = 2400, month = February, day = 29), 1, date.copy(year = 2400, month = March, day = 1))
        test(date.copy(year = 2800, month = February, day = 29), 1, date.copy(year = 2800, month = March, day = 1))
    }

    @Test
    fun `should work properly in Febuary in not leap years`() {
        test(date.copy(year = 2001, month = February, day = 28), 1, date.copy(year = 2001, month = March, day = 1))
        test(date.copy(year = 2100, month = February, day = 28), 1, date.copy(year = 2100, month = March, day = 1))
        test(date.copy(year = 2200, month = February, day = 28), 1, date.copy(year = 2200, month = March, day = 1))
        test(date.copy(year = 2300, month = February, day = 28), 1, date.copy(year = 2300, month = March, day = 1))
    }

    @Test
    fun `should work properly with multiple months and years changes`() {
        test(
            date.copy(year = 2023, month = January, day = 5),
            31 + 28 + 31 + 30 + 7,
            date.copy(year = 2023, month = May, day = 12)
        )

        test(
            date.copy(year = 2023, month = January, day = 5),
            365 + 366 + 365 + 365 + 365 + 366 + 2,
            date.copy(year = 2029, month = January, day = 7)
        )
    }


    fun test(startDate: Date, advanceAmountInDays: Int, expectedEndDate: Date) {
        assertEquals(expectedEndDate, calendar.advance(startDate, advanceAmountInDays))
    }
}