package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityMvvmBinding
import com.example.myapplication.viewmodel.User
import com.example.myapplication.viewmodel.UserViewModel
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue


class MVVMActivity: AppCompatActivity() {

    private var binding: ActivityMvvmBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMvvmBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.setUser(User("小明", 18))
        userViewModel.getUserLiveData().observe(this, {
            binding?.tvName?.text = it.name
            binding?.tvAge?.text = it.age.toString()
        })

/*        userViewModel.getUserLiveData().observe(this, object : Observer<User> {
            override fun onChanged(it: User) {
                binding?.tvName?.text = it.name
                binding?.tvAge?.text = it.age.toString()
            }
        })*/

        binding?.btnSetUser?.setOnClickListener {
            userViewModel.updateUserAge(30, "小李")
        }
    }
}