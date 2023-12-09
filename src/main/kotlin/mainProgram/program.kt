package mainProgram

import CalendarList.CalendarList
import Date.Date
import Event.Event
import EventManager.EventManager
import TodayEvents.TodayEvents
import tools.Tstring
import Horoscope.TodayHoroscope
import TodayEventData.TodayEventData

object programList {
    fun showThisMonth() {
        try {
            print("조회할 달력의 연도를 입력하시오>>")
            val year = readln().toInt()
            if(Tstring.rightYear(year))
                return;

            print("조회할 달력의 월을 입력하시오>>")
            val month = readln().toInt()
            if(Tstring.rightMonth(month))
                return

            //입력한 연도와 월月을 인자로 받는 calendarList 클래스 생성
            val calendarPrint = CalendarList(year, month)
            //달력 출력
            calendarPrint.printCalendar()

        } catch(e : NumberFormatException) {
            println("숫자를 입력하시오")
        }

    }

    fun addEvent() {
        print("이벤트 제목을 입력하시오>>")
        val title = readln()

        print("이벤트 시작 시각을 입력하시오 (yyyy/MM/dd hh:mm:ss)>>")
        val startDate = readln()

        val startDates = startDate.split("/", " ", ":")

        //데이터 결격사항 파악작업
        if ( Tstring.rightYear(startDates[0].toInt()) || Tstring.rightMonth(startDates[1].toInt()) || Tstring.rightDay(startDates[0].toInt(), startDates[1].toInt(), startDates[2].toInt()) ||
            Tstring.rightHour(startDates[3].toInt()) || Tstring.rightMinSec(startDates[4].toInt()) || Tstring.rightMinSec(startDates[5].toInt())) {
            return
        }

        //이상이 없는 데이터를 후가공 처리 (연도를 제외한 각 숫자를 2자리 String 형태로 변경)
        val startDateParts = startDate.split(" ")
        val formattedStartDate = Tstring.formatDate(startDateParts[0])
        val formattedStartTime = Tstring.formatTime(startDateParts[1])

        //입력된 값이 데이터 형식에 맞는지 확인하는 작업 (연도를 제외한 각 숫자를 2자리 String 형태로 변경)
        print("이벤트 종료 시각을 입력하시오 (yyyy/MM/dd hh:mm:ss)>>")
        val endDate = readln()
        val endDates = endDate.split("/", " ", ":")

        //데이터 결격사항 파악작업
        if ( Tstring.rightYear(endDates[0].toInt()) || Tstring.rightMonth(endDates[1].toInt()) || Tstring.rightDay(endDates[0].toInt(), endDates[1].toInt(), endDates[2].toInt()) ||
            Tstring.rightHour(endDates[3].toInt()) || Tstring.rightMinSec(endDates[4].toInt()) || Tstring.rightMinSec(endDates[5].toInt())) {
            return
        }

        //입력된 값이 데이터 형식에 맞는지 확인하는 작업 (연도를 제외한 각 숫자를 2자리 String 형태로 변경)
        val endDateParts = endDate.split(" ")
        val formattedEndDate = Tstring.formatDate(endDateParts[0])
        val formattedEndTime = Tstring.formatTime(endDateParts[1])

        //이벤트 시작 시간과 종료 시간에 모순 발생 여부 판단
        if(!Tstring.compare(formattedStartDate, formattedEndDate)){
            println("종료일자가 시작일자보다 빠릅니다.")
            return
        }
        if(formattedEndDate==formattedStartDate && !Tstring.compare(formattedStartTime, formattedEndTime)){
            println("종료시간이 시작시간보다 빠릅니다.")
            return
        }

        print("이벤트 세부사항을 입력하시오>>")
        val contents = readln()

        //이벤트 데이터 클래스에 넣고 이벤트 매니저에 값을 넣는다.
        EventManager.insertEvent(Event(title, Date(formattedStartDate, formattedStartTime), Date(formattedEndDate, formattedEndTime), contents))
    }

    fun showSchedule() {
        print("조회할 이벤트 날짜를 입력하시오 (yyyy/MM/dd)>>")
        val searchDate = readln()

        //데이터 결격사항 파악작업
        val searchDateParts = searchDate.split("/")
        if ( Tstring.rightYear(searchDateParts[0].toInt()) || Tstring.rightMonth(searchDateParts[1].toInt()) || Tstring.rightDay(searchDateParts[0].toInt(), searchDateParts[1].toInt(), searchDateParts[2].toInt()) ) {
            return
        }

        //이벤트를 저장해둔 리스트에 접근하여 조건에 맞는 일자를 끌어오기
        val event : ArrayList<TodayEventData> = EventManager.searchEvents(Date(Tstring.formatDate(searchDate), "00:00:00"), true)

        // searchSchedule의 tempList가 비었다면 사용자가 쓴 날짜에 일정이 없다는 뜻이므로 이벤트x
        // tempList가 있다면 사용자가 쓴 날짜에 일정이 있으므로 출력
        if (event.isNotEmpty()) {
            for (x in event) {
                println("[제목]: ${x.eventName.title}")
                println("[기간]: ${x.eventName.startDatetime.date} ${x.eventName.startDatetime.time} ~ ${x.eventName.endDatetime.date} ${x.eventName.endDatetime.time}")
                println("[상세]: ${x.eventName.contents}")
                println()
            }
        }
        else
            println("조회한 날짜에 이벤트가 없습니다.")
    }

    fun showTodayList()
    {
        try {
            print("조회할 이벤트의 연도를 입력하시오>>")
            val year = readln().toInt()
            if(Tstring.rightYear(year))
                return

            print("조회할 이벤트의 월을 입력하시오>>")
            val month = readln().toInt()
            if(Tstring.rightMonth(month))
                return

            print("조회할 이벤트의 일을 입력하시오>>")
            val day = readln().toInt()
            if(Tstring.rightDay(year, month, day))
                return

            //TodayEvents에 접근하여 이벤트를 순차적으로 출력
            TodayEvents.printEvents(year.toString(), Tstring.formatNumber(month), Tstring.formatNumber(day))

        } catch(e : NumberFormatException) {
            println("숫자를 입력하시오")
        }

    }
    fun showTodayHoroscope()
    {
        try {
            print("태어난 연도: ")
            val birth = readln().toInt()
            if(Tstring.rightYear(birth))
                return
            println("사용자의 띠 : ${TodayHoroscope.userZodiac(birth)}")
            //해당 탄생일에 맞는 문구를 추출하여 출력
            println(TodayHoroscope.todayHoroscope(birth))
        } catch(e : NumberFormatException) {
            println("숫자를 입력하시오")
        }

    }
}
