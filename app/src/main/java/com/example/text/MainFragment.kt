package com.example.text
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment

open class MainFragment : Fragment() {
    private lateinit var cardview1: CardView
    private lateinit var cardview2: CardView
    private lateinit var cardview3: CardView
    private lateinit var cardview4: CardView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        // 初始化 TextView
        cardview1 = view.findViewById(R.id.card_view_connect)
        cardview2 = view.findViewById(R.id.card_view_heart)
        cardview3 = view.findViewById(R.id.card_view_star)
        cardview4 = view.findViewById(R.id.card_view_history)

        // 设置点击监听器
        cardview1.setOnClickListener {
            val intent = Intent(activity, ConnectActivity::class.java)
            startActivity(intent)
        }
        cardview2.setOnClickListener {
            val intent = Intent(activity, HeartActivity::class.java)
            startActivity(intent)
        }
        cardview3.setOnClickListener {
            val intent = Intent(activity, StarActivity::class.java)
            startActivity(intent)
        }
        cardview4.setOnClickListener {
            val intent = Intent(activity, HistoryActivity::class.java)
            startActivity(intent)
        }

        Log.d("MainFragment", "onCreateView 方法被调用")
        return view
    }
}