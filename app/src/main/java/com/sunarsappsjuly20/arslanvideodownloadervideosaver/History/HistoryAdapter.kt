package com.sunarsappsjuly20.arslanvideodownloadervideosaver.History

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.sunarsappsjuly20.arslanvideodownloadervideosaver.R
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter(var urls: List<UrlEntity>, private val onItemOptionClickListener: OnItemOptionClickListener) :
    RecyclerView.Adapter<HistoryAdapter.UrlViewHolder>(), Filterable {

    private var urlListFull: List<UrlEntity> = ArrayList(urls)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UrlViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.farm_equipment_row, parent, false)
        return UrlViewHolder(view)
    }

    override fun onBindViewHolder(holder: UrlViewHolder, position: Int) {
      /*  if (urls.isEmpty()) {
            // Show a "No Data" toast if the list is empty
            Toast.makeText(holder.itemView.context, "No Data", Toast.LENGTH_SHORT).show()
            // Make all items invisible
            holder.itemView.visibility = View.GONE
        } else {
            holder.itemView.visibility = View.VISIBLE
            val urlEntity = urls[position]
            holder.bind(urlEntity)
        }*/
        val urlEntity = urls[position]
        holder.bind(urlEntity)
    }

    override fun getItemCount(): Int {
        return urls.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList: MutableList<UrlEntity> = ArrayList()
                if (constraint == null || constraint.isEmpty()) {
                    filteredList.addAll(urlListFull)
                } else {
                    val filterPattern = constraint.toString().toLowerCase(Locale.ROOT).trim()
                    for (urlEntity in urlListFull) {
                        if (urlEntity.url.contains(filterPattern)) {
                            filteredList.add(urlEntity)
                        }
                    }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                urls = results?.values as? List<UrlEntity> ?: emptyList()
                notifyDataSetChanged()
            }
        }
    }

    fun updateList(newUrls: List<UrlEntity>) {
        urls = newUrls
        urlListFull = ArrayList(newUrls)
        notifyDataSetChanged()
    }

    inner class UrlViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.row_history_title)
        private val subtitleTextView: TextView = itemView.findViewById(R.id.row_history_subtitle)
        private val imageView: ImageView = itemView.findViewById(R.id.row_history_imdb_image)
     //   private val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)

        init {
            // Handle click on dots icon
            val dotsIcon = itemView.findViewById<ImageView>(R.id.dots_b)
            dotsIcon.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val urlEntity = urls[position]
                    showOptionsDialog(urlEntity.url)
                }
            }
        }

        fun bind(urlEntity: UrlEntity) {
            titleTextView.text = urlEntity.url

            // Set the subtitle and image based on the URL
            when {
                urlEntity.url.contains("imdb", ignoreCase = true) -> {
                    subtitleTextView.text = "Imdb"
                    imageView.setImageResource(R.drawable.immb)
                    imageView.visibility = View.VISIBLE
                }
                urlEntity.url.contains("facebook", ignoreCase = true) -> {
                    subtitleTextView.text = "Facebook"
                    imageView.setImageResource(R.drawable.facebook1)
                    imageView.visibility = View.VISIBLE
                }
                urlEntity.url.contains("reddit", ignoreCase = true) -> {
                    subtitleTextView.text = "Reddit"
                    imageView.setImageResource(R.drawable.ic_reddit)
                    imageView.visibility = View.VISIBLE
                }
                urlEntity.url.contains("twitter", ignoreCase = true) -> {
                    subtitleTextView.text = "Twitter"
                    imageView.setImageResource(R.drawable.ic_twitter)
                    imageView.visibility = View.VISIBLE
                }
                urlEntity.url.contains("ted", ignoreCase = true) -> {
                    subtitleTextView.text = "Ted"
                    imageView.setImageResource(R.drawable.ted)
                    imageView.visibility = View.VISIBLE
                }
                else -> {
                    subtitleTextView.text = urlEntity.url
                    imageView.visibility = View.GONE
                }
            }

            // Set the current date to dateTextView
            val currentDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
          //  dateTextView.text = currentDate
        }

        private fun showOptionsDialog(url: String) {
            val dialogView = LayoutInflater.from(itemView.context).inflate(R.layout.dialog_savedroom_items_custom, null)
           // val alertDialogBuilder = AlertDialog.Builder(itemView.context)
            val alertDialogBuilder = AlertDialog.Builder(itemView.context, R.style.EndAlignedDialog)

            alertDialogBuilder.setView(dialogView)



// Set up the dialog here (e.g., setTitle, setMessage, etc.)

            val alertDialog = alertDialogBuilder.create()

// Calculate the position of the button on the screen
            val buttonPosition = IntArray(2)
            val dotsIcon = itemView.findViewById<ImageView>(R.id.dots_b)
            dotsIcon.getLocationOnScreen(buttonPosition)
            val buttonX = buttonPosition[0]
            val buttonY = buttonPosition[1]

// Get the width of the dialog
            val dialogWidth = (itemView.context.resources.displayMetrics.widthPixels * 0.5).toInt()

// Calculate the x-coordinate for the dialog to be aligned with the button
            val dialogX = buttonX + dotsIcon.width - dialogWidth

// Set the position of the dialog window
            alertDialog.window?.apply {
                setGravity(Gravity.TOP or Gravity.START)
                attributes.x = dialogX
                attributes.y = buttonY - dotsIcon.height // Adjust Y position to position it just above the button
            }


            val openOption = dialogView.findViewById<TextView>(R.id.openOption)
            val shareOption = dialogView.findViewById<TextView>(R.id.shareOption)
            val copyLinkOption = dialogView.findViewById<TextView>(R.id.copyLinkOption)
            val removeLinkOption = dialogView.findViewById<TextView>(R.id.removeLinkOption)

            openOption.setOnClickListener {
                onItemOptionClickListener.onOpenClicked(url)
                alertDialog.dismiss()
            }

            shareOption.setOnClickListener {
                onItemOptionClickListener.onShareClicked(url)
                alertDialog.dismiss()
            }

            copyLinkOption.setOnClickListener {
                onItemOptionClickListener.onCopyLinkClicked(url)
                alertDialog.dismiss()
            }

            removeLinkOption.setOnClickListener {
                onItemOptionClickListener.onRemoveLinkClicked(url)
                alertDialog.dismiss()
            }

            alertDialog.show()
        }
    }

    interface OnItemOptionClickListener {
        fun onOpenClicked(url: String)
        fun onShareClicked(url: String)
        fun onCopyLinkClicked(url: String)
        fun onRemoveLinkClicked(url: String)
    }
    fun removeItem(url: String) {
        urls = urls.filterNot { it.url == url }
        notifyDataSetChanged()
    }
}
