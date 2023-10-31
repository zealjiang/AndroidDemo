package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.bean.Model
import com.example.myapplication.databinding.ActivityMvpBinding
import com.example.myapplication.inf.MVPContract
import com.example.myapplication.presenter.MVPresenter

class MVPActivity: AppCompatActivity(), MVPContract.View {

    private var presenter: MVPresenter? = null
    private var binding: ActivityMvpBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMvpBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.btnSetUser.setOnClickListener {
            presenter?.getData()
        }
        presenter = MVPresenter(this, Model())
    }

    override fun updateUI(data: String) {
        binding?.tvName?.text = data
    }
}