package Event

import Date.Date

//이벤트 관련 정보를 담는 데이터 클래스
//일정명: title /일정 세부 설명: contents
//시작일자: startDatetime/ 종료일자: endDatetime
data class Event(val title: String, val startDatetime: Date, val endDatetime: Date, val contents: String)