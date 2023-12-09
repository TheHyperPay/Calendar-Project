package Horoscope

import java.io.File
import java.text.SimpleDateFormat
import java.util.*


//오늘의 운세를 출력하기 위한 클래스
object TodayHoroscope {
    private val file: String = "horoscope.txt"

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
        //오늘 날짜 받기
        val dateType = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val dateString = dateType.format(Date())
        //파일의 총 라인 수 파악 후 데이터를 저장
        val lines = File(file).readLines()
        //띠 구분
        val zodiac = birth % 12
        //랜덤하게 값을 뽑도록 hash를 통한 셔플 부여
        //단 운세는 하루마다 전환되어야 하기에 오늘 날짜를 셔플에 추가하여 구현
        val randomMessage = lines[((zodiac * birth + if (dateString.hashCode() > 0) dateString.hashCode() else -1*dateString.hashCode()) % lines.size)]

        return "오늘의 ${birth}년생 ${userZodiac(birth)} 랜덤 운세: $randomMessage"
    }
}