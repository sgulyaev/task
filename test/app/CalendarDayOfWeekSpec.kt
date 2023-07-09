package app

import app.DayOfWeek.*
import kotlin.test.Test
import kotlin.test.assertEquals

class CalendarDayOfWeekSpec {
    val calendar = Calendar()

    val monday = Date(year = 2023, month = 7, day = 10, hour = 0, minute = 0)
    val mondayWeekBefore = Date(year = 2023, month = 7, day = 3, hour = 0, minute = 0)
    val mondayInPast = Date(year = 1923, month = 2, day = 26, hour = 0, minute = 0)
    val mondayInFuture = Date(year = 3078, month = 3, day = 4, hour = 0, minute = 0)


    @Test
    fun `should be able to determine mondays`() {
        assertEquals(Monday, calendar.dayOfWeek(monday))
        assertEquals(Monday, calendar.dayOfWeek(calendar.advance(monday, 7)))
        assertEquals(Monday, calendar.dayOfWeek(calendar.advance(monday, 7 * 5)))
        assertEquals(Monday, calendar.dayOfWeek(calendar.advance(monday, 7 * 100)))
        assertEquals(Monday, calendar.dayOfWeek(mondayWeekBefore))
        assertEquals(Monday, calendar.dayOfWeek(mondayInPast))
        assertEquals(Monday, calendar.dayOfWeek(mondayInFuture))
    }

    @Test
    fun `should be able to determine other days`() {
        assertEquals(Tuesday, calendar.dayOfWeek(calendar.advance(monday, 1)))

        assertEquals(Tuesday, calendar.dayOfWeek(calendar.advance(mondayInFuture, 1)))
        assertEquals(Wednesday, calendar.dayOfWeek(calendar.advance(mondayInFuture, 2)))
        assertEquals(Thursday, calendar.dayOfWeek(calendar.advance(mondayInFuture, 3)))
        assertEquals(Friday, calendar.dayOfWeek(calendar.advance(mondayInFuture, 4)))
        assertEquals(Saturday, calendar.dayOfWeek(calendar.advance(mondayInFuture, 5)))
        assertEquals(Sunday, calendar.dayOfWeek(calendar.advance(mondayInFuture, 6)))

        assertEquals(Tuesday, calendar.dayOfWeek(calendar.advance(mondayInPast, 1)))
        assertEquals(Wednesday, calendar.dayOfWeek(calendar.advance(mondayInPast, 2)))
        assertEquals(Thursday, calendar.dayOfWeek(calendar.advance(mondayInPast, 3)))
        assertEquals(Friday, calendar.dayOfWeek(calendar.advance(mondayInPast, 4)))
        assertEquals(Saturday, calendar.dayOfWeek(calendar.advance(mondayInPast, 5)))
        assertEquals(Sunday, calendar.dayOfWeek(calendar.advance(mondayInPast, 6)))
    }
}