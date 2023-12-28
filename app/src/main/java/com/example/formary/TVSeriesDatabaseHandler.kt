package com.example.formary

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TVSeriesDatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "TVSeriesDatabase"
        private const val TABLE_SERIES = "TVSeriesTable"
        private const val KEY_SERIES_ID = "id"
        private const val KEY_SERIES_TITLE = "title"
        private const val KEY_SERIES_DESCRIPTION = "description"
        private const val KEY_SERIES_BANNER_PATH = "bannerPath"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_SERIES_TABLE = ("CREATE TABLE $TABLE_SERIES("
                + "$KEY_SERIES_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$KEY_SERIES_TITLE TEXT NOT NULL,"
                + "$KEY_SERIES_DESCRIPTION TEXT NOT NULL,"
                + "$KEY_SERIES_BANNER_PATH TEXT NOT NULL)"
                )
        db?.execSQL(CREATE_SERIES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_SERIES")
        onCreate(db)
    }

    fun addSeries(title: String, description: String, bannerPath: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_SERIES_TITLE, title)
        values.put(KEY_SERIES_DESCRIPTION, description)
        values.put(KEY_SERIES_BANNER_PATH, bannerPath)
        db.insert(TABLE_SERIES, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun getRandomSeries(): TVSeries? {
        val db = this.readableDatabase
        val cursor: Cursor =
            db.rawQuery("SELECT * FROM $TABLE_SERIES ORDER BY RANDOM() LIMIT 1", null)
        return if (cursor.moveToFirst()) {
            val seriesId = cursor.getInt(cursor.getColumnIndex(KEY_SERIES_ID))
            val title = cursor.getString(cursor.getColumnIndex(KEY_SERIES_TITLE))
            val description = cursor.getString(cursor.getColumnIndex(KEY_SERIES_DESCRIPTION))
            val bannerPath = cursor.getString(cursor.getColumnIndex(KEY_SERIES_BANNER_PATH))
            TVSeries(seriesId, title, description, bannerPath)
        } else {
            null
        }
    }

    data class TVSeries(
        val id: Int,
        val title: String,
        val description: String,
        val bannerPath: String
    )
}