package CalendarList

import Date.Date
import EventManager.EventManager
import tools.Tstring
import java.util.*


//캘린더 출력을 위한 클래스
class CalendarList(private val year: Int, private val month: Int) {
    private val calendar: Calendar = Calendar.getInstance()

    //현재 달의 캘린더를 콘솔창에 출력합니다
    public fun printCalendar(): Unit {
        //캘린더 세팅을 입력한 연도와 월(月)을 넣은 뒤 1일부터 시작
        calendar.set(year, month - 1, 1) // 월은 0부터 시작하므로 month - 1

        //해당 연도의 월에서의 마지막 일(日)을 lastDayOfMonth 변수에 저장
        val lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        //해당 달력의 년(年)과 월(月)을 출력하며 요일을 하단에 출력
        println("${this.year}년 ${this.month}월 달력")
        println("Sun\t\tMon\t\tTue\t\tWed\t\tThu\t\tFri\t\tSat")

        //해당 월(月)에서 1일(日)이 시작하는 요일을 찾을때까지 for loop를 통해 공백을 부여
        for(i in 1 until calendar.get(Calendar.DAY_OF_WEEK))
            print("\t\t")

        //1부터 순차적으로 currentDay를 늘려나가 lastDayOfMonth까지 일자를 출력한다.
        var currentDay = 1
        calendar.set(Calendar.DAY_OF_MONTH, currentDay) //calendar를 currentDay가 증가할 때마다 갱신을 시킨다.
        while (currentDay <= lastDayOfMonth) {
            printDay() //Calendar의 정보를 받아 해당 일(日)의 정보를 출력하는 함수
            currentDay++
            calendar.set(Calendar.DAY_OF_MONTH, currentDay) //calendar를 currentDay가 증가할 때마다 갱신
        }
    }

    //캘린더에 작성할 일에 관한 정보를 출력하는 함수
    //각 일당 이벤트 정보를 각각 출력하고자 함
    private fun printDay(): Unit {
        //dayOfMonth로 현재 calendar에 지정된 일(日)의 값을 가져오고 이를 출력
        val dayOfMonth = Tstring.formatNumber(calendar.get(Calendar.DAY_OF_MONTH))
        print(dayOfMonth)

        //해당 일(日)의 이벤트를 EventManager에서 검색해 가져와 이벤트의 개수를 출력
        val dayOfEvents=EventManager.searchEvents(Date("$year/${Tstring.formatNumber(month)}/${dayOfMonth}","00:00:00"), true)
        if(dayOfEvents.isNotEmpty()) {
            print("(${dayOfEvents.count()})")
        }
        else
            print("\t")

        //만약 해당 일(日)이 토요일이라면 단내림을 진행
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            println("")
        } else {
            print("\t")
        }
    }
}