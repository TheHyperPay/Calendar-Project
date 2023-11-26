package mainProgram

import calendar.CalendarList
import calendar.Date
import event.Event
import event.EventManager

object programList {

    fun showThisMonth() {
        print("조회할 달력의 연도를 입력하시오>>")
        val year = readln().toInt()
        print("조회할 달력의 월을 입력하시오>>")
        val month = readln().toInt()
        val calendarPrint = CalendarList(year, month)
        calendarPrint.printCalendar()
    }

    fun addEvent() {
        print("이벤트 제목을 입력하시오>>")
        val title = readln()
        print("이벤트 시작 시각을 입력하시오 (yyyy/MM/dd hh:mm:ss)>>")
        val startDate = readln()
        print("이벤트 종료 시각을 입력하시오 (yyyy/MM/dd hh:mm:ss)>>")
        val endDate = readln()
        print("이벤트 세부사항을 입력하시오>>")
        val contents = readln()

        val startDate_date = startDate.split(" ")
        val endDate_date = endDate.split(" ")

        val e = Event(title, Date(startDate_date[0], startDate_date[1]), Date(endDate_date[0], endDate_date[1]), contents)

        EventManager.insertEvent(e)
        EventManager.fileLink()
    }

    fun showSchedule() {
        print("조회할 이벤트 날짜를 입력하시오 (yyyy/MM/dd)>>")
        val searchDate = readln()
        val event : ArrayList<Event> = EventManager.searchSchedule(searchDate)

        // searchSchedule의 tempList가 비었다면 사용자가 쓴 날짜에 일정이 없다는 뜻이므로 이벤트x
        // tempList가 있다면 사용자가 쓴 날짜에 일정이 있으므로 출력
        if (event.isNotEmpty()) {
            for (x in event) {
                println("[제목]: ${x.getTitle()}")
                println("[기간]: ${x.getStartDate().getData()} ${x.getStartDate().getTime()} ~ ${x.getEndDate().getData()} ${x.getEndDate().getTime()}")
                println("[상세]: ${x.getContents()}")
                println()
            }
        }
        else
            println("조회한 날짜에 이벤트가 없습니다.")
    }
}
