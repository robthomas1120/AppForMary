package com.example.formary

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MovieDatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "MovieDatabase"
        private const val TABLE_MOVIE = "MovieTable"
        private const val KEY_MOVIE_ID = "id"
        private const val KEY_MOVIE_TITLE = "title"
        private const val KEY_MOVIE_DESCRIPTION = "description"
        private const val KEY_MOVIE_BANNER = "banner"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_MOVIE_TABLE = ("CREATE TABLE $TABLE_MOVIE("
                + "$KEY_MOVIE_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$KEY_MOVIE_TITLE TEXT NOT NULL,"
                + "$KEY_MOVIE_DESCRIPTION TEXT NOT NULL,"
                + "$KEY_MOVIE_BANNER TEXT NOT NULL)"
                )

        db?.execSQL(CREATE_MOVIE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_MOVIE")
        onCreate(db)
    }

    fun addMovie(title: String, description: String, bannerPath: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_MOVIE_TITLE, title)
        values.put(KEY_MOVIE_DESCRIPTION, description)
        values.put(KEY_MOVIE_BANNER, bannerPath)
        db.insert(TABLE_MOVIE, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun getMovieById(movieId: Int): Movie? {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_MOVIE WHERE $KEY_MOVIE_ID=?", arrayOf(movieId.toString()))
        return if (cursor.moveToFirst()) {
            val title = cursor.getString(cursor.getColumnIndex(KEY_MOVIE_TITLE))
            val description = cursor.getString(cursor.getColumnIndex(KEY_MOVIE_DESCRIPTION))
            val bannerPath = cursor.getString(cursor.getColumnIndex(KEY_MOVIE_BANNER))
            Movie(movieId, title, description, bannerPath)
        } else {
            null
        }
    }

    @SuppressLint("Range")
    fun getRandomMovie(): Movie? {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_MOVIE ORDER BY RANDOM() LIMIT 1", null)
        return if (cursor.moveToFirst()) {
            val movieId = cursor.getInt(cursor.getColumnIndex(KEY_MOVIE_ID))
            val title = cursor.getString(cursor.getColumnIndex(KEY_MOVIE_TITLE))
            val description = cursor.getString(cursor.getColumnIndex(KEY_MOVIE_DESCRIPTION))
            val bannerPath = cursor.getString(cursor.getColumnIndex(KEY_MOVIE_BANNER))
            Movie(movieId, title, description, bannerPath)
        } else {
            null
        }
    }

    data class Movie(val id: Int, val title: String, val description: String, val bannerPath: String)
}