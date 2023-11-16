package calendar

import java.util.*

class CalendarList(val year: Int, val month: Int) {
    fun printCalendar() {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, 1) // 월은 0부터 시작하므로 month - 1

        val lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        println("${this.year}년 ${this.month}월 달력")
        println("Sun\tMon\tTue\tWed\tThu\tFri\tSat")

        for(i in 1 until calendar.get(Calendar.DAY_OF_WEEK))
            print("\t")

        var currentDay = 1
        calendar.set(Calendar.DAY_OF_MONTH, currentDay)

        while (currentDay <= lastDayOfMonth) {
            printDay(calendar)
            currentDay++
            calendar.set(Calendar.DAY_OF_MONTH, currentDay)

            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                println()
            }
        }
    }

    private fun printDay(calendar: Calendar) {
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        print(if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth")

        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            println("")
        } else {
            print("\t")
        }
    }
}