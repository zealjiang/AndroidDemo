package com.example.main.presenter

import com.example.main.bean.Model
import com.example.main.inf.MVPContract

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