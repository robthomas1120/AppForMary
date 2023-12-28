package com.example.formary

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SongDatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "SongDatabase"
        private const val TABLE_SONGS = "SongTable"
        private const val KEY_SONG_ID = "id"
        private const val KEY_SONG_TITLE = "title"
        private const val KEY_ARTIST_NAME = "artist"
        private const val KEY_DESCRIPTION = "description"
        private const val KEY_IMAGE_PATH = "imagePath"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_SONG_TABLE = ("CREATE TABLE $TABLE_SONGS("
                + "$KEY_SONG_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$KEY_SONG_TITLE TEXT NOT NULL,"
                + "$KEY_ARTIST_NAME TEXT NOT NULL,"
                + "$KEY_DESCRIPTION TEXT NOT NULL,"
                + "$KEY_IMAGE_PATH TEXT NOT NULL)"
                )

        db?.execSQL(CREATE_SONG_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_SONGS")
        onCreate(db)
    }

    fun addSong(title: String, artist: String, description: String, imagePath: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_SONG_TITLE, title)
        values.put(KEY_ARTIST_NAME, artist)
        values.put(KEY_DESCRIPTION, description)
        values.put(KEY_IMAGE_PATH, imagePath)
        db.insert(TABLE_SONGS, null, values)
        db.close()
    }

    fun getRandomSong(): Song? {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_SONGS ORDER BY RANDOM() LIMIT 1", null)
        return if (cursor.moveToFirst()) {
            val songId = cursor.getInt(cursor.getColumnIndex(KEY_SONG_ID))
            val title = cursor.getString(cursor.getColumnIndex(KEY_SONG_TITLE))
            val artist = cursor.getString(cursor.getColumnIndex(KEY_ARTIST_NAME))
            val description = cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION))
            val imagePath = cursor.getString(cursor.getColumnIndex(KEY_IMAGE_PATH))
            Song(songId, title, artist, description, imagePath)
        } else {
            null
        }
    }

    data class Song(val id: Int, val title: String, val artist: String, val description: String, val imagePath: String)
}