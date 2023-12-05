package event

import com.google.gson.Gson
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



//오늘 일정을 판단하기 위해 사용하는 데이터 클래스.
//이벤트를 저장하되 isStart로 이벤트의 시작에 접근할지 종료에 접근할지 지정
data class TodayEventData(val eventName:Event, val isStart:Boolean)

//하루 일정 관련 object
object TodayEvents{
    //오늘 일정을 콘솔창에 출력하는 함수
    public fun printEvents(year:Int, month:Int, day:Int) {

        val todayEventDataList:List<TodayEventData> =
            sortTime(EventManager.searchEvents(Date("${year}/${month}/${day}", "00:00:00")))

        println("${year}년 ${month}월 ${day}일 하루 일정")
        for(x in todayEventDataList)
        {
            if(x.isStart)
                println("${x.eventName.getTitle()}\t${x.eventName.getStartDate().getTime()}\t시작")
            else
                println("${x.eventName.getTitle()}\t${x.eventName.getEndDate().getTime()}\t종료")
        }
    }

    //오늘 일정을 시간이 빠른 순서대로 정렬하는 함수
    private fun sortTime(eventList: List<TodayEventData>): List<TodayEventData> {
        return eventList.sortedBy { pickupTimeFromEvent(it) }
    }

    //지금 이벤트가 종료 기준인지 시작 기준인지 구분하기 위한 함수
    private fun pickupTimeFromEvent(event:TodayEventData):String{
        if(event.isStart)
            return event.eventName.getStartDate().getTime()
        else
            return event.eventName.getEndDate().getTime()
    }
}






object EventManager {
    var eventList: ArrayList<Event> = ArrayList()
    val file: String="File.txt"

    fun fileLink():Unit{
        val jsonString = File(file).readText()
        val events = Gson().fromJson(jsonString, Array<Event>::class.java)
        eventList.clear()
        eventList.addAll(events)
    }

    fun fileInsert() : Unit {
        val jsonString = Gson().toJson(eventList)
        File(file).writeText(jsonString)
        println("이벤트 저장 완료")
    }

    fun insertEvent(event:Event):Unit {
        eventList.add(event)
    }

    fun searchEvents(date:Date): ArrayList<TodayEventData> {
        var tempList: ArrayList<TodayEventData> = ArrayList()
        for(x in eventList) {
            if(x.getStartDate().getData()==date.getData()){
                tempList.add(TodayEventData(x, true))
            }
            if(x.getEndDate().getData()==date.getData()){
                tempList.add(TodayEventData(x, false))
            }
        }
        return tempList
    }

    fun searchSchedule(searchDate : String) : ArrayList<Event> {
        val tempList: ArrayList<Event> = ArrayList()

        val jsonString = File(file).readText()

        val events = Gson().fromJson(jsonString, Array<Event>::class.java)

        for (x in events) {
            if (x.getStartDate().getData() == searchDate) {
                tempList.add(x)
            }
        }

        return tempList
    }

}
