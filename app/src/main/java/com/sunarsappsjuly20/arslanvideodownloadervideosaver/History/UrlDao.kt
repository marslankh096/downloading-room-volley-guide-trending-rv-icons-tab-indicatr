package com.sunarsappsjuly20.arslanvideodownloadervideosaver.History

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UrlDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(url: UrlEntity)

    @Query("SELECT * FROM url_table")
    suspend fun getAllUrls(): List<UrlEntity>

    @Query("DELETE FROM url_table")
    suspend fun deleteAllUrls()


    @Query("DELETE FROM url_table WHERE url = :urlToDelete")
    suspend fun deleteUrlByUrl(urlToDelete: String)
}