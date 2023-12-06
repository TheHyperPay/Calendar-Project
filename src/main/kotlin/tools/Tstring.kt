package tools

import java.time.YearMonth

object Tstring{
    //한 자리의 숫자(0~9)를 2칸 차지하도록 변환(00~09)하는 함수
    public fun formatNumber(number: Int): String {
        return String.format("%02d", number)
    }

    //날짜 형식을 입력할 때 한 자리수의 공백을 제거하는 함수
    public fun formatDate(datePart: String) : String {
        val parts = datePart.split("/")
        return "${parts[0]}/${formatNumber(parts[1].toInt())}/${formatNumber(parts[2].toInt())}"
    }

    //시간 형식을 입력할 때 한 자리수의 공백을 제거하는 함수
    public fun formatTime(timePart: String): String {
        val parts = timePart.split(":")
        return "${formatNumber(parts[0].toInt())}:${formatNumber(parts[1].toInt())}:${formatNumber(parts[2].toInt())}"
    }

    //연도를 정확하게 입력했는지 판단하는 함수
    public fun rightYear(year : Int) : Boolean {
        if(year < 1) {
            println("양의 정수를 입력하시오")
            return true
        }
        else
            return false
    }

    //달을 정확하게 입력했는지 판단하는 함수
    public fun rightMonth(month : Int) : Boolean {
        if(month < 1 || month > 12 ) {
            println("1월부터 12월 중 하나를 고르시오")
            return true
        }
        else
            return false
    }

    //일을 정확하게 입력했는지 판단하는 함수
    public fun rightDay(year : Int, month : Int, day : Int) : Boolean {
        val lastDay = YearMonth.of(year, month).lengthOfMonth()
        if(day < 1 || day > lastDay ) {
            println("1일부터 ${lastDay}일 중 하나를 고르시오")
            return true
        }
        else
            return false
    }

    //시를 정확하게 입력했는지 판단하는 함수
    public fun rightHour(hour : Int) : Boolean {
        if(hour < 0 || hour > 23 ) {
            println("0시부터 23시 중 하나를 고르시오")
            return true
        }
        else
            return false
    }

    //분 혹은 초를 정확하게 입력했는지 판단하는 함수
    public fun rightMinSec(time : Int) : Boolean {
        if(time < 0 || time > 59 ) {
            println("0분(초)부터 59분(초) 중 하나를 고르시오")
            return true
        }
        else
            return false
    }

    //두 시간의 대소를 비교하여 Boolean 타입으로 바꿔버리는 함수
    //str1=큰 시간(19:00:00), str2=작은 시간(04:00:00)을 입력하시면 false가 나옵니다
    public fun compare(str1: String, str2: String): Boolean{
        val comparisonResult = str1.compareTo(str2)

        return when{
            comparisonResult>0 -> false
            comparisonResult<0 -> true
            else -> true
        }
    }
}