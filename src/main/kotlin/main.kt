import calendar.CalendarList
import calendar.Date
import event.Event
import event.EventManager

fun main() {
    val e1=Event(Date("2023/11/13", "10:00:00"), Date("2023/11/13", "16:00:00"),"ABC")
    val e2=Event(Date("2023/11/17", "10:00:00"), Date("2023/11/17", "16:00:00"),"ABC")
    val e3=Event(Date("2023/11/27", "10:00:00"), Date("2023/11/27", "16:00:00"),"ABC")

    EventManager.insertEvent(e1)
    EventManager.insertEvent(e2)
    EventManager.insertEvent(e3)


    val calendarPrint = CalendarList(2023, 11)
    calendarPrint.printCalendar()
}