package com.example.text.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.text.Activity.AdviceActivity
import com.example.text.R
import com.example.text.ViewModel.MineFragmentViewModel

class MineFragment : Fragment() {
    private lateinit var adviceTextView: TextView
    private lateinit var mineFragmentViewModel: MineFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mine, container, false)

        mineFragmentViewModel = ViewModelProvider(this).get(MineFragmentViewModel::class.java)

        adviceTextView = view.findViewById(R.id.question) as TextView
        adviceTextView.setOnClickListener {
            mineFragmentViewModel.navigateToAdviceActivity(requireContext())
        }

        return view
    }
}