package com.example.text.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.text.Activity.ConnectActivity
import com.example.text.Activity.HeartActivity
import com.example.text.Activity.HistoryActivity
import com.example.text.Activity.StarActivity
import com.example.text.R
import com.example.text.ViewModel.MainFragmentViewModel

class MainFragment : Fragment() {
    private lateinit var cardview1: CardView
    private lateinit var cardview2: CardView
    private lateinit var cardview3: CardView
    private lateinit var cardview4: CardView
    private lateinit var mainFragmentViewModel: MainFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        mainFragmentViewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)

        // 初始化 CardView
        cardview1 = view.findViewById(R.id.card_view_connect)
        cardview2 = view.findViewById(R.id.card_view_heart)
        cardview3 = view.findViewById(R.id.card_view_star)
        cardview4 = view.findViewById(R.id.card_view_history)

        // 设置点击监听器
        cardview1.setOnClickListener {
            mainFragmentViewModel.navigateToConnectActivity(requireActivity())
        }
        cardview2.setOnClickListener {
            mainFragmentViewModel.navigateToHeartActivity(requireActivity())
        }
        cardview3.setOnClickListener {
            mainFragmentViewModel.navigateToStarActivity(requireActivity())
        }
        cardview4.setOnClickListener {
            mainFragmentViewModel.navigateToHistoryActivity(requireActivity())
        }

        Log.d("MainFragment", "onCreateView 方法被调用")
        return view
    }
}