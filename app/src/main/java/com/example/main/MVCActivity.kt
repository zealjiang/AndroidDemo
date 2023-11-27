package com.example.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.main.bean.Model
import com.example.main.databinding.ActivityMvcBinding
import com.example.router.compiler.annotation.MRoute
import com.google.auto.service.AutoService

@MRoute("/main/MVCActivity")
class MVCActivity: AppCompatActivity() {

    private var model: Model? = null
    private var binding: ActivityMvcBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMvcBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.btnSetUser.setOnClickListener {
            val data = model?.getData()
            binding!!.tvName.text = data
        }
        model = Model()
    }
}