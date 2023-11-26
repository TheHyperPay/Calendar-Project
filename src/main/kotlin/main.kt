import mainProgram.programList

fun main() {
    while(true) {
        print("메뉴 번호를 선택하시오 (1:종료, 2:달력 조회, 3:이벤트 추가, 4:이벤트 조회)>>")
        val select = readln().toInt()

            when (select) {
                1 -> {
                    println("프로그램을 종료합니다.")
                    break
                }
                2 -> {
                    programList.showThisMonth()
                }
                3 -> {
                    programList.addEvent()
                }
                4 -> {
                    programList.showSchedule()
                }
                else -> {
                    println("1부터 4의 숫자중 하나를 선택하시오>>")
                }
            }
        println()
    }

}