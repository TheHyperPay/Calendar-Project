package event

import calendar.Date
import java.io.*
import tools.*
import java.util.*
import kotlin.collections.ArrayList

class Event(private var title: String, private var startDatetime: Date, private var endDatetime: Date, private var contents: String)
{
    public fun getTitle():String {
        return title
    }
    public fun getStartDate():Date {
        return startDatetime
    }
    public fun getEndDate():Date {
        return endDatetime
    }
    public fun getContents():String {
        return contents
    }
    private fun compareTimeGap(dt_start: Date, dt_end: Date): Boolean {
        val result=dt_start.getData().compareTo(dt_end.getData())

        if(result>0)
            return false
        return true
    }
}

object TodayEvents{
    public fun printEvents(year:Int, month:Int, day:Int) {
        var todayEvents:ArrayList<Event> = sortTime(
            EventManager.searchEvents(Date("$year/${Tstring.formatNumber(month)}/${Tstring.formatNumber(day)}", "00:00:00"),
            false)
        )

        var tempEvents:ArrayList<Event> = ArrayList()
        println("${year}년 ${month}월 ${day}일의 하루 일정")
        for(x in todayEvents)
        {
            tempEvents.add(x)
            if(checkEventIsDouble(tempEvents, x))
                println("이벤트명: ${x.getTitle()}\t${x.getEndDate().getTime()}\t종료")
            else
                println("이벤트명: ${x.getTitle()}\t${x.getStartDate().getTime()}\t시작")
        }
    }

    private fun sortTime(eventList: ArrayList<Event>): ArrayList<Event>
    {
        var sortedEventList: ArrayList<Event> = eventList.sortedBy { it.getStartDate().getTime() }
            .toMutableList() as ArrayList<Event>

        //EndTime 삽입
        for (x in eventList) {
            var tempEventList: ArrayList<Event> = ArrayList()
            var shouldAddX = true

            for (y in sortedEventList) {
                tempEventList.add(y)

                if (x == y)
                    continue

                if (checkEventIsDouble(tempEventList, y)) {
                    if (Tstring.compare(x.getEndDate().getTime(), y.getEndDate().getTime())) {
                        sortedEventList.add(tempEventList.count() - 1, x)
                        shouldAddX = false
                        break
                    }
                } else {
                    if (Tstring.compare(x.getEndDate().getTime(), y.getStartDate().getTime())) {
                        sortedEventList.add(tempEventList.count() - 1, x)
                        shouldAddX = false
                        break
                    }
                }
            }

            // 순회가 끝난 후 맨 마지막에 추가
            if (shouldAddX) {
                sortedEventList.add(x)
            }
        }

        return sortedEventList
    }
    private fun checkEventIsDouble(tempEventList: ArrayList<Event>, event:Event):Boolean{
        val count = tempEventList.count{it == event}
        //FIXME 동일 이벤트가 여러 가지 있을 떄 예외 처리 필수
        if(count == 2)
            return true
        return false
    }
    private fun getMaxDayOfMonth(year: Int, month: Int): Int {
        return when (month) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            2 -> if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) 29 else 28
            else -> throw IllegalArgumentException("달 선언 오류: $month")
        }
    }
}

object EventManager {
    var eventList: ArrayList<Event> = ArrayList()
    val file: String="File.txt"

    fun fileLink():Unit{
        val printWriter = PrintWriter(file)

        for (x in eventList) {
            val title = x.getTitle()
            val startDate = x.getStartDate().getData()
            val startTime = x.getStartDate().getTime()
            val endDate = x.getEndDate().getData()
            val endTime = x.getEndDate().getTime()
            val contents = x.getContents()

            printWriter.println("$title $startDate $startTime ~ $endDate $endTime $contents")
        }
        printWriter.close()
        println("이벤트 저장 완료")
    }

    fun insertEvent(event:Event):Unit {
        eventList.add(event)
    }

    fun searchEvents(date:Date, isCheckTime:Boolean): ArrayList<Event> {
        var tempList: ArrayList<Event> = ArrayList()
        for(x in eventList) {
            if(x.getStartDate().getData()==date.getData()) {
                tempList.add(x)
                continue
            }
        }
        return tempList
    }

    fun searchSchedule(searchDate : String) : ArrayList<Event> {
        val tempList: ArrayList<Event> = ArrayList()

        val lines = File(file).readLines()

        for(x in lines) {
            val temp = x.split(" ")

            val title = temp[0]
            val startDate = temp[1]
            val startTime = temp[2]
            val endDate = temp[4]
            val endTime = temp[5]
            val contents = temp[6]

            val event = Event(title, Date(startDate, startTime), Date(endDate, endTime), contents)

            if (event.getStartDate().getData() == searchDate)
                tempList.add(event)
        }
        return tempList
    }
}
