package Horoscope

import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object TodayHoroscope {
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

    fun todayHoroscope(birth: Int): String {
        val dateType = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val dateString = dateType.format(Date())
        val file: String = "horoscope.txt"
        val lines = File(file).readLines()
        val zodiac = birth % 12
        val randomMessage = lines[(zodiac + birth + dateString.toInt()) % lines.size]

        return "오늘의 ${birth}년생 ${userZodiac(birth)} 랜덤 운세: $randomMessage"
    }
}