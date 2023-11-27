// IBookManager.aidl
package com.example.main.aidl;

// Declare any non-default types here with import statements
import com.example.main.aidl.Book;
interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    //String getDeviceId();
}