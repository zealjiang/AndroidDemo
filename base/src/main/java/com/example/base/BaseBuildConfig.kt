package com.example.base

object BaseBuildConfig {

    private var isDebug: Boolean = true
    fun isDebug(): Boolean{
        return isDebug
    }

    fun setDebug(isDebug: Boolean) {
        this.isDebug = isDebug
    }
}