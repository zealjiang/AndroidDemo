package com.example.main.inf

interface MVPContract {

    interface View {
        fun updateUI(data: String)
    }

    interface Presenter {
        fun getData()
    }

/*    interface Model {
        fun getData(): String
    }*/
}