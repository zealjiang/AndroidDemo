package com.example.myapplication.presenter

import com.example.myapplication.bean.Model
import com.example.myapplication.inf.MVPContract

class MVPresenter : MVPContract.Presenter {

    private var view: MVPContract.View? = null
    private var model: Model? = null

    constructor(view: MVPContract.View, model: Model) {
        this.view = view
        this.model = model
    }

    override fun getData() {
        val data = model?.getData(true)
        view?.updateUI(data!!)
    }
}