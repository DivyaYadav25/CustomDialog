package com.example.customdialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // string to be sent to the fragment when dialog fragment opens
    var sendString : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sendString="Welcome!"

        button_open?.setOnClickListener {
            val mainFragment = MainFragment.newInstance(sendString!!)
            mainFragment.show(supportFragmentManager, "dialog")
        }
    }
}
