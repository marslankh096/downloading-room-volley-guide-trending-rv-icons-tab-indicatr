package com.sunarsappsjuly20.arslanvideodownloadervideosaver

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.sunarsappsjuly20.arslanvideodownloadervideosaver.History.AppDatabase
import com.sunarsappsjuly20.arslanvideodownloadervideosaver.History.UrlEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BrowserManager(private val activity: FragmentActivity) {

    fun newWindow(url: String) {

        val fragment = BrowserWindow.newInstance(url)

        // Save the URL to the Room database using coroutines
        GlobalScope.launch(Dispatchers.IO) {
            val urlEntity = UrlEntity(url = url)
            AppDatabase.getDatabase(activity.applicationContext).urlDao().insert(urlEntity)
        }

        activity.supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, fragment, "BROWSER_WINDOW")
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    fun unhideCurrentWindow() {
        val fragment = activity.supportFragmentManager.findFragmentByTag("BROWSER_WINDOW") as? BrowserWindow
        fragment?.unhideWindow()
    }
}
