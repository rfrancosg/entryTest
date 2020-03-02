package com.example.entrytest.View.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.entrytest.View.Fragment.EntryTestListFragment
import com.example.myapplication.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val newFragment = EntryTestListFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.entryTestMainFrameLayout, newFragment)
        transaction.addToBackStack(null)
        transaction.commit()
        // ...

        actionBar?.hide()
    }

    override fun onBackPressed() {
    }
}
