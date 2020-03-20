package com.m7amdelbana.bookstore.view.home.book;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.m7amdelbana.bookstore.R;
import com.m7amdelbana.bookstore.network.models.Book;
import com.m7amdelbana.bookstore.util.OnBookButtonClick;
import com.m7amdelbana.bookstore.util.OnItemClick;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookHolder> {

    private List<Book> books;
    private BookType bookType;
    private OnItemClick onItemClick;
    private OnBookButtonClick onBookButtonClick;

    public BookAdapter(List<Book> books, BookType bookType, OnItemClick onItemClick, OnBookButtonClick onBookButtonClick) {
        this.books = books;
        this.bookType = bookType;
        this.onItemClick = onItemClick;
        this.onBookButtonClick = onBookButtonClick;
    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = 0;
        switch (bookType) {
            case HOME:
                layout = R.layout.item_book;
                break;
            case FEATURE:
            case MY_BOOKS:
                layout = R.layout.item_book_landscape;
                break;
        }

        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new BookHolder(v, bookType);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder holder, final int position) {
        holder.bind(books.get(position));
        if (onBookButtonClick != null) {
            holder.btnOpen.setOnClickListener(v -> {
                String url = books.get(position).getUrl();
                onBookButtonClick.onBookButtonClick(url);
            });
        }
        holder.itemView.setOnClickListener(v -> onItemClick.onItemClick(position));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}
