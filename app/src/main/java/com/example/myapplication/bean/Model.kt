package com.example.myapplication.bean

class Model {

    fun getData(isMVP: Boolean = false): String {
        if (isMVP) {
            return "MVP Model®"
        } else {
            return "MVC Model®"
        }
    }
}