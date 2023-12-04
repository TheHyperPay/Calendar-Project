package mainProgram

import calendar.CalendarList
import calendar.Date
import event.Event
import event.EventManager
import event.TodayEvents
import tools.*
import Horoscope.TodayHoroscope
import java.lang.NumberFormatException
import java.time.YearMonth

object programList {
    private fun formatDate(datePart: String) : String {
        val parts = datePart.split("/")
        return "${parts[0]}/${Tstring.formatNumber(parts[1].toInt())}/${Tstring.formatNumber(parts[2].toInt())}"
    }

    private fun formatTime(timePart: String): String {
        val parts = timePart.split(":")
        return "${Tstring.formatNumber(parts[0].toInt())}:${Tstring.formatNumber(parts[1].toInt())}:${Tstring.formatNumber(parts[2].toInt())}"
    }

    private fun rightYear(year : Int) : Boolean {
        if(year < 1) {
            println("양의 정수를 입력하시오")
            return true
        }
        else
            return false
    }

    private fun rightMonth(month : Int) : Boolean {
        if(month < 1 || month > 12 ) {
            println("1월부터 12월 중 하나를 고르시오")
            return true
        }
        else
            return false
    }

    private fun rightDay(year : Int, month : Int, day : Int) : Boolean {
        val lastDay = YearMonth.of(year, month).lengthOfMonth()
        if(day < 1 || day > lastDay ) {
            println("1일부터 ${lastDay}일 중 하나를 고르시오")
            return true
        }
        else
            return false
    }

    private fun rightHour(hour : Int) : Boolean {
        if(hour < 0 || hour > 23 ) {
            println("0시부터 23시 중 하나를 고르시오")
            return true
        }
        else
            return false
    }

    private fun rightMinSec(time : Int) : Boolean {
        if(time < 0 || time > 59 ) {
            println("0분(초)부터 59분(초) 중 하나를 고르시오")
            return true
        }
        else
            return false
    }

    fun showThisMonth() {
        try {
            print("조회할 달력의 연도를 입력하시오>>")
            val year = readln().toInt()
            if(rightYear(year))
                return;

            print("조회할 달력의 월을 입력하시오>>")
            val month = readln().toInt()
            if(rightMonth(month))
                return

            val calendarPrint = CalendarList(year, month)
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

        if ( rightYear(startDates[0].toInt()) || rightMonth(startDates[1].toInt()) || rightDay(startDates[0].toInt(), startDates[1].toInt(), startDates[2].toInt()) ||
             rightHour(startDates[3].toInt()) || rightMinSec(startDates[4].toInt()) || rightMinSec(startDates[5].toInt())) {
            return
        }

        val startDateParts = startDate.split(" ")
        val formattedStartDate = "${formatDate(startDateParts[0])}"
        val formattedStartTime = "${formatTime(startDateParts[1])}"

        print("이벤트 종료 시각을 입력하시오 (yyyy/MM/dd hh:mm:ss)>>")
        val endDate = readln()
        val endDates = endDate.split("/", " ", ":")

        if ( rightYear(endDates[0].toInt()) || rightMonth(endDates[1].toInt()) || rightDay(endDates[0].toInt(), endDates[1].toInt(), endDates[2].toInt()) ||
             rightHour(endDates[3].toInt()) || rightMinSec(endDates[4].toInt()) || rightMinSec(endDates[5].toInt())) {
            return
        }

        val endDateParts = endDate.split(" ")
        val formattedEndDate = "${formatDate(endDateParts[0])}"
        val formattedEndTime = "${formatTime(endDateParts[1])}"

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

        val e = Event(title, Date(formattedStartDate, formattedStartTime), Date(formattedEndDate, formattedEndTime), contents)

        EventManager.insertEvent(e)
        EventManager.fileInsert()
    }

    fun showSchedule() {
        print("조회할 이벤트 날짜를 입력하시오 (yyyy/MM/dd)>>")
        val searchDate = readln()
        val searchDateParts = searchDate.split("/")

        if ( rightYear(searchDateParts[0].toInt()) || rightMonth(searchDateParts[1].toInt()) || rightDay(searchDateParts[0].toInt(), searchDateParts[1].toInt(), searchDateParts[2].toInt()) ) {
            return
        }

        val event : ArrayList<Event> = EventManager.searchSchedule(formatDate(searchDate))

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

    fun showTodayList()
    {
        try {
            print("조회할 이벤트의 연도를 입력하시오>>")
            val year = readln().toInt()
            if(rightYear(year))
                return

            print("조회할 이벤트의 월을 입력하시오>>")
            val month = readln().toInt()
            if(rightMonth(month))
                return

            print("조회할 이벤트의 일을 입력하시오>>")
            val day = readln().toInt()
            if(rightDay(year, month, day))
                return

            //FIXME 조건 추가
            TodayEvents.printEvents(year, month, day)

        } catch(e : NumberFormatException) {
            println("숫자를 입력하시오")
        }

    }
    fun showTodayHoroscope()
    {
        try {
            print("태어난 연도: ")
            val birth = readln().toInt()
            if(rightYear(birth))
                return
            println("사용자의 띠 : ${TodayHoroscope.userZodiac(birth)}")
            println(TodayHoroscope.todayHoroscope(birth))
        } catch(e : NumberFormatException) {
            println("숫자를 입력하시오")
        }

    }
}
