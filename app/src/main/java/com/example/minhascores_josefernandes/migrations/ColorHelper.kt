package com.example.minhascores_josefernandes.migrations

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ColorHelper (context: Context): SQLiteOpenHelper(context, "colors.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {

        val sql = "create table colors(" +
                "id integer primary key autoincrement, " +
                "nome text, " +
                "codigo integer)"
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table colors")
        this.onCreate(db)
    }
}