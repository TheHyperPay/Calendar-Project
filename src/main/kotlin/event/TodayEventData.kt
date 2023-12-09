package TodayEventData

import Event.Event

//오늘 일정을 판단하기 위해 사용하는 데이터 클래스.
//이벤트를 저장하되 isStart로 이벤트의 시작에 접근할지 종료에 접근할지 지정
data class TodayEventData(val eventName:Event, val isStart:Boolean)