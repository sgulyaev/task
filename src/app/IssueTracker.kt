package app

class IssueTracker {
    fun calculateDueDate(date: Date, turnaroundTime: Int): Date {
        val minutes = date.minute + turnaroundTime
        return date.copy(
            minute = minutes % 60,
            hour = date.hour + minutes / 60
        )
    }
}
