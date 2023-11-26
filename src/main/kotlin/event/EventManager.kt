package event

import calendar.Date
import java.io.*
import java.util.*

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
