package com.sunarsappsjuly20.arslanvideodownloadervideosaver.History

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "url_table")
data class UrlEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val url: String
)