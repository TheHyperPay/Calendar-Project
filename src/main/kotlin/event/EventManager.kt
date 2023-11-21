package event

import calendar.Date

class Event(private var startDatetime: Date, private var endDatetime: Date, private var contents: String)
{
    public fun getStartDate():Date {
        return startDatetime
    }
    public fun getEndDate():Date {
        return endDatetime
    }
    private fun compareTimeGap(dt_start: Date, dt_end: Date): Boolean {
        val result=dt_start.getData().compareTo(dt_end.getData())

        if(result>0)
            return false
        return true
    }
}
object EventManager {
    var eventList: ArrayList<Event> = ArrayList()
    val file: String="File.txt"

    fun fileLink():Unit{
        //파일 연동
    }

    fun insertEvent(event:Event):Unit {
        eventList.add(event)
    }

    fun searchEvents(date:Date, isCheckTime:Boolean): ArrayList<Event> {
        var tempList: ArrayList<Event> = ArrayList()
        for(x in eventList) {
            if(x.getStartDate().getData()==date.getData()) {
                tempList.add(x)
                continue
            }
        }
        return tempList
    }
}