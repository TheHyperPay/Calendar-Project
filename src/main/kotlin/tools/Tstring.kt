package tools

object Tstring{
    //한 자리의 숫자(0~9)를 2칸 차지하도록 변환(00~09)하는 함수
    public fun formatNumber(number: Int): String {
        return String.format("%02d", number)
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