package app

class IssueTracker {
    fun calculateDueDate(date: Date, turnaroundTime: Int): Date {
        return date.copy(minute = date.minute + turnaroundTime)
    }
}
