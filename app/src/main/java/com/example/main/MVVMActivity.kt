package com.example.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.main.util.ReferenceTest
import com.example.main.util.threadpool.AsyncTaskTest
import com.example.main.viewmodel.User
import com.example.main.viewmodel.UserViewModel
import com.example.main.databinding.ActivityMvvmBinding
import com.example.router.SimpleRouter
import com.example.router.compiler.annotation.MRoute


@MRoute("/main/MVVMActivity")
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


        //ReferenceTest.weakReferenceTest()
        //JavaReferenceTest.weakReferenceTest()

        binding?.btnTakeout?.setOnClickListener {
            testRoute()
        }


        val test = AsyncTaskTest()
        test.moreSyncTaskDoThen()
    }

    private fun testRoute() {
        val clazz = SimpleRouter.navigation("/takeout/MainActivity")
        clazz?.let {
            startActivity(Intent(this, it))
        }
    }
}