package Horoscope

import java.io.File
import java.text.SimpleDateFormat
import java.util.*

//오늘의 운세를 출력하기 위한 클래스
object TodayHoroscope {
    //태어난 연도의 띠를 출력하는 함수
    fun userZodiac(birth: Int): String {
        val zodiac = birth % 12
        return when (zodiac) {
            0 -> "원숭이띠"
            1 -> "닭띠"
            2 -> "개띠"
            3 -> "돼지띠"
            4 -> "쥐띠"
            5 -> "소띠"
            6 -> "호랑이띠"
            7 -> "토끼띠"
            8 -> "용띠"
            9 -> "뱀띠"
            10 -> "말띠"
            else -> "양띠"
        }
    }

    //오늘의 운세를 알 수 있는 함수
    fun todayHoroscope(birth: Int): String {
        val dateType = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val dateString = dateType.format(Date())
        val file: String = "horoscope.txt"
        val lines = File(file).readLines()
        val zodiac = birth % 12
        val randomMessage = lines[((zodiac * birth + if (dateString.hashCode() > 0) dateString.hashCode() else -1*dateString.hashCode()) % lines.size)]
        
        return "오늘의 ${birth}년생 ${userZodiac(birth)} 랜덤 운세: $randomMessage"
    }
}