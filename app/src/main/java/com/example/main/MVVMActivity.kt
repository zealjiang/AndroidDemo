package com.example.main

import android.R.attr.path
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.main.databinding.ActivityMvvmBinding
import com.example.main.util.LocationUtil
import com.example.main.util.threadpool.AsyncTaskTest
import com.example.main.viewmodel.User
import com.example.main.viewmodel.UserViewModel
import com.example.router.SimpleRouter
import com.example.router.compiler.annotation.MRoute
import com.google.android.material.snackbar.Snackbar


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

            //skipToActionStart()
            showSnackbar()
        }


        //ReferenceTest.weakReferenceTest()
        //JavaReferenceTest.weakReferenceTest()

        binding?.btnTakeout?.setOnClickListener {
            testRoute()
        }


        val test = AsyncTaskTest()
        test.moreSyncTaskDoThen()

        checkAndRequestGpsPermission()
    }

    private fun checkAndRequestGpsPermission() {
        //检查是否有gps权限
        val isAllowed = LocationUtil.checkGPSPermissionIsAllowed(this)
        Log.d("location", "gps permission isAllowed =$isAllowed")
        //未永久拒绝，不弹GPS弹窗
        if (isAllowed) {
            return
        }

        LocationUtil.setOnRequestPermissionCallbackListener(object :
            LocationUtil.OnRequestPermissionCallbackListener {
            override fun onRequestPermissionsResult(
                requestCode: Int,
                permissions: Array<out String>,
                grantResults: IntArray
            ) {
                val state = LocationUtil.checkGPSPermissionIsForeverDenied(this@MVVMActivity, requestCode, permissions, grantResults)
                Log.d("location", "request gps permission callback, IsForeverDenied =$state")
                if (state == LocationUtil.FOREVER_DENIED) {

                } else if (state == LocationUtil.FOREVER_DENIED_NOT) {

                }
            }
        })

        //动态申请GPS权限
        val hasRequested = LocationUtil.requestGPSPermission(this)
        Log.d("location", "request gps permission, hasRequested =$hasRequested")
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d("location", "onRequestPermissionsResult permissions =${permissions.size}, grantResults =${grantResults.size}")
        if (this == null || isFinishing || isDestroyed) return
        LocationUtil.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun testRoute() {
        val clazz = SimpleRouter.navigation("/takeout/MainActivity")
        clazz?.let {
            startActivity(Intent(this, it))
        }
    }

    private fun skipToActionStart() {
        val URI_SCHEME = "action"
        val URI_AUTHORITY = "www.action.com"
        val path = "actionStart"

        var intent = Intent()
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.action = Intent.ACTION_VIEW

        var uriBuilder = Uri.Builder()
        uriBuilder.scheme(URI_SCHEME)
        uriBuilder.authority(URI_AUTHORITY)
        uriBuilder.appendEncodedPath(path)

        intent.setPackage(packageName)
        intent.data = uriBuilder.build()

        startActivity(intent)
    }

    private fun showSnackbar() {
        Snackbar.make(findViewById(android.R.id.content), "消息内容", Snackbar.LENGTH_SHORT).show()
    }
}