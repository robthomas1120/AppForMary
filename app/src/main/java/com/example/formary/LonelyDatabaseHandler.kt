package com.example.formary

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class LonelyDatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "LonelyDatabase"
        private const val TABLE_LONELY = "LonelyTable"
        private const val KEY_ID = "id"
        private const val KEY_TITLE = "title"
        private const val KEY_LONELY = "lonelyText"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_LONELY_TABLE = ("CREATE TABLE $TABLE_LONELY("
                + "$KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$KEY_TITLE TEXT NOT NULL,"
                + "$KEY_LONELY TEXT NOT NULL)"
                )

        db?.execSQL(CREATE_LONELY_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_LONELY")
        onCreate(db)
    }

    fun addLonely(title: String, lonely: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_TITLE, title)
        values.put(KEY_LONELY, lonely)
        db.insert(TABLE_LONELY, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun getLonelyById(lonelyId: Int): Lonely? {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_LONELY WHERE $KEY_ID=?", arrayOf(lonelyId.toString()))
        return if (cursor.moveToFirst()) {
            val title = cursor.getString(cursor.getColumnIndex(KEY_TITLE))
            val lonelyText = cursor.getString(cursor.getColumnIndex(KEY_LONELY))
            Lonely(lonelyId, title, lonelyText)
        } else {
            null
        }
    }

    @SuppressLint("Range")
    fun getRandomLonely(): Lonely? {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_LONELY ORDER BY RANDOM() LIMIT 1", null)
        return if (cursor.moveToFirst()) {
            val lonelyId = cursor.getInt(cursor.getColumnIndex(KEY_ID))
            val title = cursor.getString(cursor.getColumnIndex(KEY_TITLE))
            val lonelyText = cursor.getString(cursor.getColumnIndex(KEY_LONELY))
            Lonely(lonelyId, title, lonelyText)
        } else {
            null
        }
    }

    data class Lonely(val id: Int, val title: String, val lonely: String)
}