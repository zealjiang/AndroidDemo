package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityViewmodelBinding
import com.example.myapplication.util.QuickSort
import com.example.myapplication.util.SelectSort
import com.example.myapplication.viewmodel.User
import com.example.myapplication.viewmodel.UserViewModel

class ViewModelActivity : AppCompatActivity(){
    private var binding: ActivityViewmodelBinding? = null
    private var userViewModel: UserViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewmodelBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel?.setUser(User("小明", 18))

        userViewModel?.getUserLiveData()?.observe(this, Observer<User> { user ->
            Log.d("User", "Name: " + user.name + " Age: " + user.age)
            binding?.tvName?.text = user.name
            binding?.tvAge?.text = user.age.toString()
        })

        binding?.btnSetUser?.setOnClickListener {
            userViewModel?.updateUserAge(30, "小强")
        }

        //SelectSort.test()
        //test()

    }

    private fun test() {
        val str = "abcabcabc"

        // 1. 生成所有子串
        val list = ArrayList<String>()
        for (i in 0..str.length) {
            for (j in i+1..str.length) {
                //添加所有字串
                //list.add(str.substring(i, j))

                //添加不重复字串
/*                var sutString = str.substring(i, j)
                if (!list.contains(sutString)) {
                    list.add(sutString)
                }*/

                //添加不重重字串，且字串的中字母不能有重复
                var subString = str.substring(i, j)
                var hasRepeatLetter = false
                subString.toCharArray().forEach {
                    if (subString.indexOf(it) != subString.lastIndexOf(it)) {
                        hasRepeatLetter = true
                        return@forEach
                    }
                }

                if (!hasRepeatLetter && !list.contains(subString)) {
                    list.add(subString)
                }

            }
        }
        Log.d("test", list.toString())
        Log.d("test", "length ="+list.size)

    }
}