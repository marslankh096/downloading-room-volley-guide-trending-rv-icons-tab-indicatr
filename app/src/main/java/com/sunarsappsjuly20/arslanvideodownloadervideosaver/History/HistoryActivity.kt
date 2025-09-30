package com.sunarsappsjuly20.arslanvideodownloadervideosaver.History

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sunarsappsjuly20.arslanvideodownloadervideosaver.Home
import com.sunarsappsjuly20.arslanvideodownloadervideosaver.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class HistoryActivity : AppCompatActivity(), HistoryAdapter.OnItemOptionClickListener {
    private lateinit var adapter: HistoryAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var clearHistoryButton: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Hide the title bar
        supportActionBar?.hide()

        // Make the activity full screen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_history2)

        val dateHistoryMain: TextView = findViewById(R.id.datehistorymain)
        recyclerView = findViewById(R.id.recyclerViewHistory2)
        searchView = findViewById(R.id.searchViewHistory)
        clearHistoryButton = findViewById(R.id.clearHistoryButton)

        recyclerView.layoutManager = LinearLayoutManager(this)
        // Initialize the adapter before using it

        adapter = HistoryAdapter(emptyList(), this@HistoryActivity)


        // Retrieve data from the Room database using coroutines
        lifecycleScope.launch(Dispatchers.Main) {
            val urls = withContext(Dispatchers.IO) {
                AppDatabase.getDatabase(applicationContext).urlDao().getAllUrls()
            }

            if (urls.isEmpty()) {

                // Show "No data" message layout if there are no items
                val noDataLayout = layoutInflater.inflate(R.layout.layout_no_data_history, null)
                val noDataTextView: TextView = noDataLayout.findViewById(R.id.noDataTextView)
                val container: FrameLayout = findViewById(R.id.container)
                container.removeAllViews()
                container.addView(noDataLayout)

                // Show a toast for "No history found!" as well (optional)
                Toast.makeText(this@HistoryActivity, "No history found!", Toast.LENGTH_SHORT).show()
            } else {
                // Show RecyclerView with data if there are items
                recyclerView.visibility = View.VISIBLE
                recyclerView.adapter = adapter
            }
            // Pass the OnItemOptionClickListener when creating the adapter
            adapter = HistoryAdapter(urls, this@HistoryActivity)
            recyclerView.adapter = adapter
        }

        // Set the current date to dateTextView
        val currentDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
        dateHistoryMain.text = currentDate

        // Handle search view query text change
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Perform search here if needed
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Filter the list based on the search text
                adapter.filter.filter(newText)
                return true
            }
        })

        // Handle clear history button click
        clearHistoryButton.setOnClickListener {
            clearHistory()
        }
    }

    private fun clearHistory() {
        lifecycleScope.launch(Dispatchers.IO) {
            val urlDao = AppDatabase.getDatabase(applicationContext).urlDao()

            // Delete all URLs from the database
            urlDao.deleteAllUrls()

            withContext(Dispatchers.Main) {
                Toast.makeText(this@HistoryActivity, "History cleared!", Toast.LENGTH_SHORT).show()

                // Refresh the RecyclerView after clearing history
                adapter.updateList(emptyList())
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
        finish()
    }

    override fun onOpenClicked(url: String) {
        // Handle Open option here
    }

    override fun onShareClicked(url: String) {
        // Handle Share option here
    }

    override fun onCopyLinkClicked(url: String) {
        // Handle Copy link option here
    }

    override fun onRemoveLinkClicked(url: String) {
        adapter.removeItem(url)

        // Remove the item from the urls list
        val updatedList = adapter.urls.toMutableList()

        lifecycleScope.launch(Dispatchers.IO) {
            val urlDao = AppDatabase.getDatabase(applicationContext).urlDao()

            // Delete the specific URL from the database
            urlDao.deleteUrlByUrl(url)

            withContext(Dispatchers.Main) {
                // Refresh the RecyclerView after removing the URL
                adapter.updateList(updatedList)
            }
        }
    }
}
