package com.vr.room_demo_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.vr.room_demo_app.databinding.ActivityEditDataBinding
import com.vr.room_demo_app.viewModel.EditVm

class EditDataActivity : AppCompatActivity() {
    private lateinit var binder: ActivityEditDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityEditDataBinding.inflate(layoutInflater).apply {
            viewModel = ViewModelProvider(this@EditDataActivity)[EditVm::class.java]
            lifecycleOwner = this@EditDataActivity
        }
        setContentView(binder.root)

        supportActionBar?.title = "Edit info"

        if (intent.hasExtra("room_data")){
            binder.viewModel?.getDetails(intent.getSerializableExtra("room_data") as OrganizationData)
        }

    }
}