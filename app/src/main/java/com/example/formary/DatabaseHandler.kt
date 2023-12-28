package com.example.formary

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Database"

        private const val TABLE_POEMS = "PoemTable"
        private const val KEY_ID = "id"
        private const val KEY_TITLE = "title"
        private const val KEY_POEM = "poem"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_POEMS_TABLE = ("CREATE TABLE $TABLE_POEMS("
                + "$KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$KEY_TITLE TEXT NOT NULL,"
                + "$KEY_POEM TEXT NOT NULL)"
                )

        db?.execSQL(CREATE_POEMS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_POEMS")
        onCreate(db)
    }

    fun addPoem(title: String, poem: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_TITLE, title)
        values.put(KEY_POEM, poem)
        db.insert(TABLE_POEMS, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun getPoemById(poemId: Int): Poem? {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_POEMS WHERE $KEY_ID=?", arrayOf(poemId.toString()))
        return if (cursor.moveToFirst()) {
            val title = cursor.getString(cursor.getColumnIndex(KEY_TITLE))
            val poemText = cursor.getString(cursor.getColumnIndex(KEY_POEM))
            Poem(poemId, title, poemText)
        } else {
            null
        }
    }

    @SuppressLint("Range")
    fun getRandomPoem(): Poem? {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_POEMS ORDER BY RANDOM() LIMIT 1", null)
        return if (cursor.moveToFirst()) {
            val poemId = cursor.getInt(cursor.getColumnIndex(KEY_ID))
            val title = cursor.getString(cursor.getColumnIndex(KEY_TITLE))
            val poemText = cursor.getString(cursor.getColumnIndex(KEY_POEM))
            Poem(poemId, title, poemText)
        } else {
            null
        }
    }

    data class Poem(val id: Int, val title: String, val poem: String)
}

