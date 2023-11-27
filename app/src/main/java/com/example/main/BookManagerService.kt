package com.example.main

import android.app.Service
import android.content.Intent
import android.os.Binder

import android.os.IBinder
import android.os.RemoteException

import java.util.concurrent.CopyOnWriteArrayList


/*class BookManagerService : Service() {
    //支持并发读写
    private val mBookList: CopyOnWriteArrayList<Book> = CopyOnWriteArrayList<Book>()

    //服务端定义Binder类（IBookManager.Stub）
    private val mBinder: Binder = IBookManager.Stub() {
        @Throws(RemoteException::class)
        fun getBookList(): List<Book> {
            return mBookList
        }

        @Throws(RemoteException::class)
        fun addBook(book: Book) {
            mBookList.add(book)
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return mBinder
    }
}*/
