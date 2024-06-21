package com.whiz.mylocations

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.whiz.core.base.BaseActivity
import com.whiz.mylocations.databinding.ActivityLocationBinding

class LocationActivity : BaseActivity() {

    private lateinit var binding: ActivityLocationBinding

    companion object {


        fun newInstance(context: Context, bundle: Bundle? = null): Intent {
            return Intent(context, LocationActivity::class.java).apply {
                bundle?.let { this.putExtras(bundle) }
            }
        }

        private const val TAG = "LocationActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }

}