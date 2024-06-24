package com.example.testproject.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.testproject.R
import com.example.testproject.presentation.adapter.PersonAdapter
import com.example.testproject.presentation.viewmodel.PersonViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val personViewModel: PersonViewModel by viewModels()
    private lateinit var personAdapter: PersonAdapter
    private var isLoading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        personAdapter = PersonAdapter(arrayListOf()) { person ->
            val intent = Intent(this, PersonDetailsActivity::class.java)
            intent.putExtra("person", person)
            startActivity(intent)
        }

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val swipeRefreshLayout: SwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener {
            personViewModel.refreshPersons()
        }

        recyclerView.adapter = personAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = recyclerView.adapter?.itemCount ?: 0

                //perform http call whenever the recyclerview reach the bottom of the list
                if (lastVisibleItemPosition == totalItemCount - 1 && !isLoading) {
                    isLoading = true
                    personViewModel.addMorePersons()
                }
            }
        })

        //update the recyclerview whenever the view model notify changes
        personViewModel.persons.observe(this) { persons ->
            personAdapter.updateData(persons)
            swipeRefreshLayout.isRefreshing = false
            Handler().postDelayed({
                isLoading = false
            }, 2000) //
        }

        personViewModel.fetchPersons()
    }
}