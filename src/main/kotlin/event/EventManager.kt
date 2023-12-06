package event

import com.google.gson.Gson
import calendar.Date
import java.io.*
import tools.*
import java.util.*
import kotlin.collections.ArrayList


//이벤트 관련 정보를 담는 데이터 클래스
data class Event(val title: String, val startDatetime: Date, val endDatetime: Date, val contents: String)



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
                println("${x.eventName.title}\t${x.eventName.startDatetime.time}\t시작")
            else
                println("${x.eventName.title}\t${x.eventName.endDatetime.time}\t종료")
        }
    }

    //오늘 일정을 시간이 빠른 순서대로 정렬하는 함수
    private fun sortTime(eventList: List<TodayEventData>): List<TodayEventData> {
        return eventList.sortedBy { pickupTimeFromEvent(it) }
    }

    //지금 이벤트가 종료 기준인지 시작 기준인지 구분하기 위한 함수
    private fun pickupTimeFromEvent(event:TodayEventData):String{
        if(event.isStart)
            return event.eventName.startDatetime.time
        else
            return event.eventName.endDatetime.time
    }
}





//이벤트 클래스를 종합적으로 다루는 클래스
object EventManager {
    var eventList: ArrayList<Event> = ArrayList()
    val file: String="File.txt"

    //파일에 연결하여 데이터를 모두 가져오는 함수
    fun fileLink():Unit{
        val jsonString = File(file).readText()
        val events = Gson().fromJson(jsonString, Array<Event>::class.java)
        eventList.clear()
        eventList.addAll(events)
    }

    //파일에 데이터를 저장하는 함수
    fun fileInsert() : Unit {
        val jsonString = Gson().toJson(eventList)
        File(file).writeText(jsonString)
        println("이벤트 저장 완료")
    }

    //이벤트 정보를 이벤트 매니저 리스트에 저장하는 함수
    fun insertEvent(event:Event):Unit {
        eventList.add(event)
    }

    //이벤트를 Date 클래스의 일자를 모두 찾는 함수
    //해당 클래스는 TodayEvent에서만 사용한다.
    fun searchEvents(date:Date): ArrayList<TodayEventData> {
        var tempList: ArrayList<TodayEventData> = ArrayList()
        for(x in eventList) {
            if(x.startDatetime.date==date.date){
                tempList.add(TodayEventData(x, true))
            }
            if(x.endDatetime.date==date.date){
                tempList.add(TodayEventData(x, false))
            }
        }
        return tempList
    }

    //파일에 직접 접근하여 입력 일자의 이벤트 목록을 찾는 함수
    fun searchSchedule(searchDate : String) : ArrayList<Event> {
        val tempList: ArrayList<Event> = ArrayList()

        val jsonString = File(file).readText()

        val events = Gson().fromJson(jsonString, Array<Event>::class.java)

        for (x in events) {
            if (x.startDatetime.date == searchDate) {
                tempList.add(x)
            }
        }

        return tempList
    }
}
