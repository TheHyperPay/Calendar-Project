package EventManager

import Date.Date
import Event.Event
import EventFile.EventFile
import TodayEventData.TodayEventData


//이벤트 클래스를 종합적으로 다루는 클래스
object EventManager {
    private var eventList: ArrayList<Event> = ArrayList()

    //eventList의 값에 접근
    fun getEventList(): ArrayList<Event>{
        return eventList
    }

    //이벤트 정보를 이벤트 매니저 리스트에 저장하는 함수
    fun insertEvent(event:Event):Unit {
        eventList.add(event)
        //eventList의 모든 정보를 덮어쓰기
        EventFile.fileInsert()
    }

    //이벤트를 Date 클래스의 일자를 모두 찾는 함수
    fun searchEvents(date:Date, onlyFindStart:Boolean): ArrayList<TodayEventData> {
        var tempList: ArrayList<TodayEventData> = ArrayList()

        for(x in eventList) {
            //시작일자가 동일하면 값을 넣기
            if(x.startDatetime.date==date.date){
                tempList.add(TodayEventData(x, true))
            }
            //종료일자가 동일하면 값을 넣기. 단 Bool 파라미터가 true면 접근 불가
            if(x.endDatetime.date==date.date && !onlyFindStart){
                tempList.add(TodayEventData(x, false))
            }
        }
        return tempList
    }
}
