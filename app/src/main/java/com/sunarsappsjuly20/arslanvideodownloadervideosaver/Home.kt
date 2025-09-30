package com.sunarsappsjuly20.arslanvideodownloadervideosaver;

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.rd.PageIndicatorView
import com.sunarsappsjuly20.arslanvideodownloadervideosaver.History.HistoryActivity
import com.sunarsappsjuly20.arslanvideodownloadervideosaver.volley.VideoAdapter
import org.json.JSONArray


class Home : AppCompatActivity(), View.OnClickListener {

    private lateinit var browserManager: BrowserManager
    private var pageIndicatorView: PageIndicatorView? = null
    private var pager: ViewPager? = null

    //trending
    private lateinit var recyclerView: RecyclerView
    private lateinit var videoAdapter: VideoAdapter
    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_sceen)

        //trending

        recyclerView = findViewById(R.id.recyclerview_for_moste_visited)
        recyclerView.layoutManager = LinearLayoutManager(this)

        requestQueue = Volley.newRequestQueue(this)
        videoAdapter = VideoAdapter(emptyList()) { videoUrl ->
            // Handle video playback here
        }
        recyclerView.adapter = videoAdapter

        fetchVideoData()
        //trending

        browserManager = BrowserManager(this)

        val btnSettings: ImageView = findViewById(R.id.home_menu_options)
        btnSettings.setOnClickListener {
            val intent = Intent(applicationContext, HistoryActivity::class.java)
            startActivity(intent)
        }

        //Guide
        val guide: Button = findViewById(R.id.start_guide)

        guide.setOnClickListener {
            val intent = Intent(applicationContext, Guide::class.java)
            startActivity(intent)
        }
        //Guide

        // Initialize ViewPager and PageIndicatorView
        pager = findViewById(R.id.viewpager)
        pageIndicatorView = findViewById(R.id.pageIndicatorView)

        val adapter = MyPagerAdapter(supportFragmentManager)
        pager?.adapter = adapter
        pageIndicatorView?.setViewPager(pager)


    }

    private fun fetchVideoData() {
        val apiUrl = "https://api.dailymotion.com/videos?fields=title,url&sort=trending&limit=100"

        val request = JsonObjectRequest(
            Request.Method.GET, apiUrl, null,
            Response.Listener { response ->
                val videoUrls = parseVideoUrls(response.getJSONArray("list"))
                videoAdapter.updateVideoUrls(videoUrls)
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
            })

        requestQueue.add(request)
    }

    private fun parseVideoUrls(jsonArray: JSONArray): List<String> {
        val videoUrls = mutableListOf<String>()

        for (i in 0 until jsonArray.length()) {
            val videoObject = jsonArray.getJSONObject(i)
            val videoUrl = videoObject.getString("url")
            videoUrls.add(videoUrl)
        }

        return videoUrls
    }

    override fun onClick(view: View?) {
        // Handle click events here if needed
    }
    inner class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> FirstFragment()
                1 -> SecondFragment()
                else -> FirstFragment() // Default to FirstFragment
            }
        }
        override fun getCount(): Int {
            return 2 // Number of fragments in the ViewPager
        }
    }
}