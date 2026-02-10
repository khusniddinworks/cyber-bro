package com.test.greedy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.view.Gravity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tv = TextView(this)
        tv.text = "This is a Security Test App.\nCyber Brother should flag this as HIGH RISK."
        tv.gravity = Gravity.CENTER
        setContentView(tv)
    }
}
