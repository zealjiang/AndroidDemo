package com.example.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.main.databinding.ActivityActionStartBinding
import com.example.main.util.ReferenceTest
import com.example.main.util.threadpool.AsyncTaskTest
import com.example.main.viewmodel.User
import com.example.main.viewmodel.UserViewModel
import com.example.main.databinding.ActivityMvvmBinding
import com.example.router.SimpleRouter
import com.example.router.compiler.annotation.MRoute


class ActionStartActivity: AppCompatActivity() {

    private var binding: ActivityActionStartBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActionStartBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
    }

}