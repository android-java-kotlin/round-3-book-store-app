package com.m7amdelbana.bookstore.view.books;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.m7amdelbana.bookstore.R;
import com.m7amdelbana.bookstore.network.api.APIClient;
import com.m7amdelbana.bookstore.network.models.Book;
import com.m7amdelbana.bookstore.network.services.APIService;
import com.m7amdelbana.bookstore.util.OnBookButtonClick;
import com.m7amdelbana.bookstore.util.OnItemClick;
import com.m7amdelbana.bookstore.util.PrefManager;
import com.m7amdelbana.bookstore.view.home.book.BookAdapter;
import com.m7amdelbana.bookstore.view.home.book.BookType;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksFragment extends Fragment implements OnItemClick, OnBookButtonClick {

    private RecyclerView recyclerView;
    private List<Book> books;

    public BooksFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books, container, false);
        initView(view);
        getMyBooks();
        return view;
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    private void getMyBooks() {
        APIService apiService = APIClient.getClient().create(APIService.class);
        apiService.getMyBooks(PrefManager.retrieveAccessToken(Objects.requireNonNull(getActivity()))).enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(@NotNull Call<List<Book>> call, @NotNull Response<List<Book>> response) {
                if (response.isSuccessful()) {
                    books = response.body();
                    setupBooks();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Book>> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupBooks() {
        BookAdapter bookAdapter = new BookAdapter(books, BookType.MY_BOOKS, this, this);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(Objects.requireNonNull(getActivity()).getApplicationContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(bookAdapter);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onBookButtonClick(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        Objects.requireNonNull(getActivity()).startActivity(browserIntent);
    }
}
