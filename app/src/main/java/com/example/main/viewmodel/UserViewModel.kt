package com.example.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel的作用是将数据与UI分离，让数据可以在屏幕旋转时不会丢失
 * 在 ViewModel 中，我们使用 LiveData 来包装数据，从而将数据转换为可观察的数据。当数据发生变化时，LiveData 会通知它的观察者
 */
class UserViewModel: ViewModel(){
    private var userLiveData = MutableLiveData<User>()

    public fun getUserLiveData(): LiveData<User> {
        return userLiveData;
    }

    public fun setUser(user: User) {
        userLiveData.setValue(user);
    }

    public fun updateUserAge(age: Int, name: String) {
        var user = userLiveData.value
        user?.age = age
        user!!.name = name
        userLiveData.setValue(user)
    }
}