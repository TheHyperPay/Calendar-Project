package calendar

import event.EventManager
import tools.*
import java.util.*

//캘린더 출력을 위한 클래스
class CalendarList(private val year: Int, private val month: Int) {
    private val calendar: Calendar = Calendar.getInstance()

    //현재 달의 캘린더를 콘솔창에 출력합니다
    public fun printCalendar(): Unit {
        calendar.set(year, month - 1, 1) // 월은 0부터 시작하므로 month - 1

        val lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        println("${this.year}년 ${this.month}월 달력")
        println("Sun\t\tMon\t\tTue\t\tWed\t\tThu\t\tFri\t\tSat")

        for(i in 1 until calendar.get(Calendar.DAY_OF_WEEK))
            print("\t\t")

        var currentDay = 1
        calendar.set(Calendar.DAY_OF_MONTH, currentDay)

        while (currentDay <= lastDayOfMonth) {
            printDay()
            currentDay++
            calendar.set(Calendar.DAY_OF_MONTH, currentDay)
        }
    }

    //캘린더에 작성할 일에 관한 정보를 출력하는 함수
    //각 일당 이벤트 정보를 각각 출력하고자 함
    private fun printDay(): Unit {
        val dayOfMonth = Tstring.formatNumber(calendar.get(Calendar.DAY_OF_MONTH))
        print(dayOfMonth)

        val dayOfEvents=EventManager.searchEvents(Date("$year/${Tstring.formatNumber(month)}/${dayOfMonth}","00:00:00"))
        if(dayOfEvents.isNotEmpty()) {
            print("(${dayOfEvents.count()})")
        }
        else
            print("\t")


        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            println("")
        } else {
            print("\t")
        }
    }
}

//시간 관련 정보를 담은 데이터 클래스
data class Date (val date:String, val time:String)
