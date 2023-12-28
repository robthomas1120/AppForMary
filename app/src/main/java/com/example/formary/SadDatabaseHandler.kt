package com.example.formary

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SadDatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "SadDatabase"
        private const val TABLE_SAD = "SadTable"
        private const val KEY_SAD_ID = "id"
        private const val KEY_SAD_TITLE = "title"
        private const val KEY_SAD = "sadText"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_SAD_TABLE = ("CREATE TABLE $TABLE_SAD("
                + "$KEY_SAD_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$KEY_SAD_TITLE TEXT NOT NULL,"
                + "$KEY_SAD TEXT NOT NULL)"
                )

        db?.execSQL(CREATE_SAD_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_SAD")
        onCreate(db)
    }

    fun addSad(title: String, sad: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_SAD_TITLE, title)
        values.put(KEY_SAD, sad)
        db.insert(TABLE_SAD, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun getSadById(sadId: Int): Sad? {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_SAD WHERE $KEY_SAD_ID=?", arrayOf(sadId.toString()))
        return if (cursor.moveToFirst()) {
            val title = cursor.getString(cursor.getColumnIndex(KEY_SAD_TITLE))
            val sadText = cursor.getString(cursor.getColumnIndex(KEY_SAD))
            Sad(sadId, title, sadText)
        } else {
            null
        }
    }

    @SuppressLint("Range")
    fun getRandomSad(): Sad? {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_SAD ORDER BY RANDOM() LIMIT 1", null)
        return if (cursor.moveToFirst()) {
            val sadId = cursor.getInt(cursor.getColumnIndex(KEY_SAD_ID))
            val title = cursor.getString(cursor.getColumnIndex(KEY_SAD_TITLE))
            val sadText = cursor.getString(cursor.getColumnIndex(KEY_SAD))
            Sad(sadId, title, sadText)
        } else {
            null
        }
    }

    data class Sad(val id: Int, val title: String, val sad: String)
}