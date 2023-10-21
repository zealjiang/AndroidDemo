// IBookManager.aidl
package com.example.myapplication.aidl;

// Declare any non-default types here with import statements
import com.example.myapplication.aidl.Book;
interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    //String getDeviceId();
}