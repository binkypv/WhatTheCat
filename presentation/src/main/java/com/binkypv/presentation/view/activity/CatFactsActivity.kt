package com.binkypv.presentation.view.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.binkypv.presentation.R
import com.binkypv.presentation.databinding.ActivityCatFactsBinding

class CatFactsActivity: AppCompatActivity(){
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
}