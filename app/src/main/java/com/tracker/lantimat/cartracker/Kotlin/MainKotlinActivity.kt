package com.tracker.lantimat.cartracker.Kotlin

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.SeekBar
import com.tracker.lantimat.cartracker.R

import kotlinx.android.synthetic.main.activity_main_kotlin.*
import android.widget.Toast
import android.widget.SeekBar.OnSeekBarChangeListener



class MainKotlinActivity : AppCompatActivity() {

    var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_kotlin)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        textView.setText("TestText")

        btn.setOnClickListener{view ->
            i++
            textView.setText("Click number: " + i)
        }

        seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                textView.setText("progress " + p1)
            }

        })

    }

}
