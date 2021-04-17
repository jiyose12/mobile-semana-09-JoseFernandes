package com.example.minhascores_josefernandes.dao

import android.content.ContentValues
import android.content.Context
import com.example.minhascores_josefernandes.migrations.ColorHelper
import com.example.minhascores_josefernandes.models.Cor

class ColorDAO {

    var database: ColorHelper

    constructor(context: Context){
        this.database = ColorHelper(context)
    }

    fun select(): ArrayList<Cor> {
        val colors = ArrayList<Cor>()
        val columns = arrayOf("id", "nome", "codigo")
        val cursor = this.database.readableDatabase.query("colors", columns, null, null, null, null, "nome")

        cursor.moveToFirst()

        for (i in 1..cursor.count){
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val name = cursor.getString(cursor.getColumnIndex("nome"))
            val code = cursor.getInt(cursor.getColumnIndex("codigo"))
            colors.add(Cor(id, name, code))
            cursor.moveToNext()
        }
        return colors
    }

    fun insert (cor: Cor){
        val cv = ContentValues()
        cv.put("nome", cor.nome)
        cv.put("codigo", cor.codigo)
        this.database.writableDatabase.insert("colors", null, cv)
    }

    fun update (cor: Cor) {
        val where = "id = ?"
        val pWhere = arrayOf(cor.id.toString())
        val cv = ContentValues()
        cv.put("nome", cor.nome)
        cv.put("codigo", cor.codigo)
        this.database.writableDatabase.update("colors", cv, where, pWhere)
    }

    fun delete (id: Int) {
        val where = "id = ?"
        val pWhere = arrayOf(id.toString())
        this.database.writableDatabase.delete("colors", where, pWhere)
    }
}