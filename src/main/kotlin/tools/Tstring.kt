package tools

object Tstring{
    public fun formatNumber(number: Int): String {
        return String.format("%02d", number)
    }

    public fun compare(str1: String, str2: String): Boolean{
        val comparisonResult = str1.compareTo(str2)

        return when{
            comparisonResult>0 -> false
            comparisonResult<0 -> true
            else -> true
        }
    }
}