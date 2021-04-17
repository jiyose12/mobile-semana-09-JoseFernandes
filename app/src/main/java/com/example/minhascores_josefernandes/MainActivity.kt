package com.example.minhascores_josefernandes

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import com.example.minhascores_josefernandes.adapters.ColorAdapter
import com.example.minhascores_josefernandes.dao.ColorDAO
import com.example.minhascores_josefernandes.models.Cor
private const val ADDCOLOR = 1
private const val EDITCOLOR = 2
class MainActivity : AppCompatActivity() {
    private lateinit var btAdd: FloatingActionButton
    private lateinit var lvColors: ListView
    private lateinit var colorList: ArrayList<Cor>

    private lateinit var dao: ColorDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.dao = ColorDAO(this)
        this.colorList = dao.select()

        this.btAdd = findViewById(R.id.floatingActionButton)
        this.lvColors = findViewById(R.id.lvColors)

        this.lvColors.adapter = ColorAdapter(this, this.colorList)

        btAdd.setOnClickListener {
            val intent = Intent(this, FormActivity::class.java)
            intent.putExtra("SAVE_COLOR", 1)
            startActivityForResult(intent, ADDCOLOR)
        }

        lvColors.onItemClickListener = Edit()
        lvColors.onItemLongClickListener = Remove()
    }

    inner class Edit: AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val color = this@MainActivity.colorList[position]
            val intent = Intent(this@MainActivity, FormActivity::class.java)
            intent.putExtra("EDIT_COLOR", color)
            startActivityForResult(intent, EDITCOLOR)
        }
    }

    inner class Remove: AdapterView.OnItemLongClickListener {
        override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {
            val color = this@MainActivity.colorList[position]
            this@MainActivity.dao.delete(color.id)
            (this@MainActivity.lvColors.adapter as ColorAdapter).remove(color)
            return true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK){
            if (requestCode == ADDCOLOR){
                val color = data?.getSerializableExtra("SAVE_COLOR") as Cor
                this.dao.insert(color)
                (this.lvColors.adapter as ColorAdapter).add(color)
            } else if (requestCode == EDITCOLOR) {
                val color = data?.getSerializableExtra("EDIT_COLOR") as Cor
                for (c in this.colorList) {
                    if (c.id == color.id) {
                        c.nome = color.nome
                        c.codigo = color.codigo
                        this.dao.update(c)
                        (this.lvColors.adapter as ColorAdapter).update()
                        break
                    }
                }
            }
        }
    }

}