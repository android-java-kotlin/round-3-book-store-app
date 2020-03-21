package com.m7amdelbana.bookstore.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.m7amdelbana.bookstore.network.models.Book;

@Database(entities = {Book.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract BookDao bookDao();
}
