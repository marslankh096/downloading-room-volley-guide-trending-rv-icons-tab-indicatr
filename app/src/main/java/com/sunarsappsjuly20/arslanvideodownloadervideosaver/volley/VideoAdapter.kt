package com.sunarsappsjuly20.arslanvideodownloadervideosaver.volley

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.recyclerview.widget.RecyclerView
import com.sunarsappsjuly20.arslanvideodownloadervideosaver.R

class VideoAdapter(
    private var videoUrls: List<String>,
    private val playVideoCallback: (String) -> Unit
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val webView: WebView = itemView.findViewById(R.id.webView2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val videoUrl = videoUrls[position]

        holder.webView.settings.javaScriptEnabled = true
        holder.webView.webChromeClient = WebChromeClient()

        holder.itemView.setOnClickListener {
            playVideoCallback(videoUrl)
        }
        holder.webView.loadUrl(videoUrl)
    }

    override fun getItemCount(): Int {
        return videoUrls.size
    }

    fun updateVideoUrls(newVideoUrls: List<String>) {
        videoUrls = newVideoUrls
        notifyDataSetChanged()
    }
}
