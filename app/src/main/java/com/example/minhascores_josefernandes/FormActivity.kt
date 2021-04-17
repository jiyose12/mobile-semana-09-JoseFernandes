package com.example.minhascores_josefernandes

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.minhascores_josefernandes.models.Cor

class FormActivity : AppCompatActivity() {
    private lateinit var etColorName: EditText
    private lateinit var sbRed: SeekBar
    private lateinit var sbGreen: SeekBar
    private lateinit var sbBlue: SeekBar
    private lateinit var btCode: Button
    private lateinit var btSave: Button
    private lateinit var btCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        this.etColorName = findViewById(R.id.etColorName)
        this.sbRed = findViewById(R.id.sbRedInput)
        this.sbGreen = findViewById(R.id.sbGreenInput)
        this.sbBlue = findViewById(R.id.sbBlueInput)
        this.btCode = findViewById(R.id.btCode)
        this.btSave = findViewById(R.id.btSave)
        this.btCancel = findViewById(R.id.btCancel)

        if (intent.hasExtra("EDIT_COLOR")){
            val color = intent.getSerializableExtra("EDIT_COLOR") as Cor
            this.etColorName.setText(color.nome)
            this.btCode.text = color.toHex()
            this.btCode.setBackgroundColor(color.codigo)
            this.sbRed.progress = Color.red(color.codigo)
            this.sbGreen.progress = Color.green(color.codigo)
            this.sbBlue.progress = Color.blue(color.codigo)
        }

        btSave.setOnClickListener { save(it) }
        btCancel.setOnClickListener { finish() }
        btCode.setOnClickListener { copyToClipBoard() }

        setSeekBar(sbRed)
        setSeekBar(sbGreen)
        setSeekBar(sbBlue)
    }

    private fun copyToClipBoard() {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Color", btCode.text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "${btCode.text} copiado com sucesso", Toast.LENGTH_LONG).show()
    }

    private fun setSeekBar (seekBar: SeekBar) {
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val colorCode = Color.rgb(sbRed.progress, sbGreen.progress, sbBlue.progress)
                btCode.text = String.format("#%06X", (0xFFFFFF and colorCode))
                btCode.setBackgroundColor(colorCode)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                Toast.makeText(applicationContext,"Mova a barra",Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Toast.makeText(applicationContext,"A cor é: ${String.format("#%06X", (0xFFFFFF and Color.rgb(sbRed.progress, sbGreen.progress, sbBlue.progress)))}",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun save(view: View) {
        val colorText = this.etColorName.text.toString()
        val colorCode = Color.rgb(sbRed.progress, sbGreen.progress, sbBlue.progress)
        if (intent.hasExtra("SAVE_COLOR")) {

            val intent = Intent()
            intent.putExtra("SAVE_COLOR", Cor(colorText, colorCode))
            setResult(Activity.RESULT_OK, intent)
            finish()
        }else if (intent.hasExtra("EDIT_COLOR")){

            val color = intent.getSerializableExtra("EDIT_COLOR") as Cor
            color.nome = colorText
            color.codigo = colorCode

            val intent = Intent()
            intent.putExtra("EDIT_COLOR", color)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

}