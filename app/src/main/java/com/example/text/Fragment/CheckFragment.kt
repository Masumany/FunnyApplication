package com.example.text.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.text.ContentActivity.CheckContentActivity
import com.example.text.Data.CheckFortune
import com.example.text.R
import com.example.text.ViewModel.CheckViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CheckFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var floatButton: FloatingActionButton
    private lateinit var checkViewModel: CheckViewModel
    private lateinit var adapter: CheckAdapter
    private var isScrollingUp = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_check, container, false)
        initViews(view)
        setupViewModel()
        setupRefreshListener()
        setupScrollListener()
        return view
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        swipeRefresh = view.findViewById(R.id.swipe_refresh)
        floatButton = view.findViewById(R.id.floatButton)
        floatButton.hide()
        adapter = CheckAdapter(mutableListOf())
        recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        checkViewModel = ViewModelProvider(this).get(CheckViewModel::class.java)
        observeViewModel()
        checkViewModel.fetchData()
    }

    private fun setupRefreshListener() {
        swipeRefresh.setOnRefreshListener {
            checkViewModel.fetchData()
        }
    }

    private fun setupScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                if (dy > 0) {
                    isScrollingUp = false
                    if (firstVisibleItemPosition > 0) {
                        floatButton.hide()
                    }
                } else if (dy < 0) {
                    isScrollingUp = true
                    floatButton.show()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && isScrollingUp) {
                    floatButton.show()
                }
            }
        })
        floatButton.setOnClickListener {
            recyclerView.smoothScrollToPosition(0)
        }
    }

    private fun observeViewModel() {
        checkViewModel.checkFortune.observe(viewLifecycleOwner, Observer { checkFortune ->
            if (checkFortune != null && checkFortune.data?.isNotEmpty() == true) {
                adapter.updateData(checkFortune.data)
                swipeRefresh.isRefreshing = false
                recyclerView.scrollToPosition(0)
            }
        })

        checkViewModel.errorMessage.observe(viewLifecycleOwner, Observer { error ->
            if (error.isNotEmpty()) {
                swipeRefresh.isRefreshing = false
                // 可以添加更多错误处理逻辑，如显示 Toast
            }
        })
    }
}

class CheckAdapter(private var dataList: MutableList<CheckFortune.CheckData>) :
    RecyclerView.Adapter<CheckAdapter.CheckViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheckViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.check_item, parent, false
        )
        return CheckViewHolder(view)
    }

    override fun onBindViewHolder(holder: CheckViewHolder, position: Int) {
        val data = dataList[position]
        holder.titleTextView.text = data.hot_word
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, CheckContentActivity::class.java)
            intent.putExtra("url", data.url)
            holder.itemView.context.startActivity(intent)
        }
    }

    class CheckViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.desc)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateData(newData: List<CheckFortune.CheckData>) {
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }
}