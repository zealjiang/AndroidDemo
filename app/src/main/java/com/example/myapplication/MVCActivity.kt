package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.bean.Model
import com.example.myapplication.databinding.ActivityMvcBinding

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