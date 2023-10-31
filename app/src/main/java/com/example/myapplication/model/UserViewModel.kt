package com.example.myapplication.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.viewmodel.User

/**
 * ViewModel的作用是将数据与UI分离，让数据可以在屏幕旋转时不会丢失
 * 在 ViewModel 中，我们使用 LiveData 来包装数据，从而将数据转换为可观察的数据。当数据发生变化时，LiveData 会通知它的观察者
 */
class UserViewModel : ViewModel(){
    private var userLiveData: MutableLiveData<User>? = null

    fun getUserLiveData(): MutableLiveData<User>? {
        if (userLiveData == null) {
            userLiveData = MutableLiveData<User>()
            loadUser()
        }
        return userLiveData
    }

    private fun loadUser() {
        val user = User("小强", 28)

        // 将数据加载到LiveData中
        userLiveData?.postValue(user)
    }
}