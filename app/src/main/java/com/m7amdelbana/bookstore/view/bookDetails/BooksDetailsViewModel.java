package com.m7amdelbana.bookstore.view.bookDetails;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.m7amdelbana.bookstore.network.models.Book;

public class BooksDetailsViewModel extends ViewModel {

    public MutableLiveData<Book> book = new MutableLiveData<>();
}
