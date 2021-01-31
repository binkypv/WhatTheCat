package com.binkypv.presentation.view.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.binkypv.presentation.R
import com.binkypv.presentation.databinding.ActivityCatFactsBinding


class CatFactsActivity : AppCompatActivity() {
    private var binding: ActivityCatFactsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_cat_facts
        )
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_share -> {
            false
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}