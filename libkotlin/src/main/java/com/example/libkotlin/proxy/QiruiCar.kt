package com.example.libkotlin.proxy

class QiruiCar: ICar{
    override fun brand() {
        println("奇瑞汽车")
    }

    override fun engine() {
        super.engine()
        println("汽油发动机")
    }
}