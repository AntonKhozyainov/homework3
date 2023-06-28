package ru.khozyainov.homework3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var countryAdapter: CountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initList()
    }

    private fun initList() {
        countryAdapter = CountryAdapter()

        val recyclerView: RecyclerView = findViewById(R.id.countryRecyclerView)

        recyclerView.run {
            adapter = countryAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

    }


}