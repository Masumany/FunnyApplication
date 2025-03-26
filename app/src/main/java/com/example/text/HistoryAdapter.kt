import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.text.HistoryData
import com.example.text.R

class HistoryAdapter(private val historyDataList: List<HistoryData>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.history_item_layout,
            parent,
            false
        )
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val historyData = historyDataList[position]
        holder.titleTextView.text = historyData.title
        holder.timeTextView.text = "时间：${historyData.date}"
        holder.descTextView.text = "内容：${historyData.desc}"
        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent = Intent(holder.itemView.context, com.example.text.HistoryContentActivity::class.java)

            intent.putExtra("title", historyData.title)
            intent.putExtra("date", historyData.date)
            intent.putExtra("desc", historyData.desc)
            holder.itemView.context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return historyDataList.size
    }

    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.title)
        val timeTextView: TextView = view.findViewById(R.id.date)
        val descTextView: TextView = view.findViewById(R.id.desc)
    }
}