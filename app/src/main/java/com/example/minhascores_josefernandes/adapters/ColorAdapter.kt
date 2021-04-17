package com.example.minhascores_josefernandes.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.minhascores_josefernandes.MainActivity
import com.example.minhascores_josefernandes.R
import com.example.minhascores_josefernandes.models.Cor
import kotlin.collections.ArrayList

class ColorAdapter(var mainActivity: MainActivity, var colorList: ArrayList<Cor>) : BaseAdapter() {
    override fun getCount(): Int {
        return colorList.count()
    }

    override fun getItem(position: Int): Any {
        return colorList[position]
    }

    override fun getItemId(position: Int): Long {
        return -1
    }

    fun add (cor: Cor) {
        colorList.add(cor)
        notifyDataSetChanged()
    }

    fun update() {
        notifyDataSetChanged()
    }

    fun remove (cor: Cor) {
        colorList.remove(cor)
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val color = this.colorList.get(position)
        val view: View

        if (convertView == null) {
        view=LayoutInflater.from(mainActivity).inflate(R.layout.color_list_layout, null)
        }else{
            view = convertView
        }
        val colorImage = view.findViewById<ImageView>(R.id.ivColorImage)
        val colorName = view.findViewById<TextView>(R.id.tvColorName)
        val colorCode = view.findViewById<TextView>(R.id.tvColorCode)

        colorImage.setBackgroundColor(Color.parseColor(color.toHex()))
        colorName.text = color.nome
        colorCode.text = String.format("#%06X", (0xFFFFFF and color.codigo))

        return view
    }

}
