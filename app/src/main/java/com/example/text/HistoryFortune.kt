import com.example.text.HistoryData

data class HistoryFortune(
    val request_id: String,
    val success: Boolean,
    val message: String,
    val code: Int,
    val data: List<HistoryData>,
    val time: Long,
    val usage: Int
)

