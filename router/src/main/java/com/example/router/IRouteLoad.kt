package com.example.router

import android.app.Activity

interface IRouteLoad {
    fun loadInto(routers: HashMap<String, Class<out Activity>>)
}