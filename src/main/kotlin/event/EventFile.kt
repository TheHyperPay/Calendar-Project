package EventFile

import Event.Event
import EventManager.EventManager
import com.google.gson.Gson
import java.io.File

//파일 관련 클래스
object EventFile
{
    private val file: String="File.txt"

    //파일에 연결하여 데이터를 모두 가져오는 함수
    fun fileLink():Unit{
        //파일을 읽어들여 Array형태로 파일 내 JSON 정보를 저장
        val jsonString = File(file).readText()
        val events = Gson().fromJson(jsonString, Array<Event>::class.java)

        //EventManager에 접근하여 List를 새로 갱신
        EventManager.getEventList().clear()
        EventManager.getEventList().addAll(events)
    }

    //파일에 데이터를 저장하는 함수
    fun fileInsert() : Unit {
        //JSON 형태로 파일에 덮어쓰기
        val jsonString = Gson().toJson(EventManager.getEventList())
        File(file).writeText(jsonString)
        println("이벤트 저장 완료")
    }
}