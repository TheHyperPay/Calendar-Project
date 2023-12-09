package TodayEvents

import Date.Date
import TodayEventData.TodayEventData
import EventManager.EventManager

//하루 일정 관련 object
object TodayEvents{
    //오늘 일정을 콘솔창에 출력하는 함수
    public fun printEvents(year:String, month:String, day:String) {

        //입력받은 연도, 월月, 일日을 바탕으로 동일한 일자의 이벤트를 검색 후 시간순으로 정렬 진행
        //이때 시작일정과 종료일정 각각 구분하여서 동시에 값을 넣기
        val todayEventDataList:List<TodayEventData> =
            sortTime(EventManager.searchEvents(Date("${year}/${month}/${day}", "00:00:00"), false))

        //저장된 데이터를 콘솔에 출력 진행
        println("${year}년 ${month}월 ${day}일 하루 일정")
        for(x in todayEventDataList)
        {
            //시작일정을 기준으로 시간을 출력하는지, 종료일정을 기준으로 시간을 출력하는지 결정
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
        //시작일정을 기준으로 시간을 추출하는지, 종료일정을 기준으로 시간을 추출하는지 결정
        if(event.isStart)
            return event.eventName.startDatetime.time
        else
            return event.eventName.endDatetime.time
    }
}