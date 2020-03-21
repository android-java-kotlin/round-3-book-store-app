package com.m7amdelbana.bookstore.view.bookDetails;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.m7amdelbana.bookstore.R;
import com.m7amdelbana.bookstore.databinding.FragmentBookDetailsBinding;
import com.m7amdelbana.bookstore.network.api.APIClient;
import com.m7amdelbana.bookstore.network.models.Book;
import com.m7amdelbana.bookstore.network.services.APIService;
import com.m7amdelbana.bookstore.util.PrefManager;
import com.m7amdelbana.bookstore.view.home.book.BookType;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDetailsFragment extends Fragment {

    private BooksDetailsViewModel booksDetailsViewModel;
    private NavController navController;
    private Book book;

    public BookDetailsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_book_details, container, false);
//        return view;

        FragmentBookDetailsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_details, container, false);

        booksDetailsViewModel = ViewModelProviders.of(getActivity()).get(BooksDetailsViewModel.class);

        binding.setLifecycleOwner(getActivity());

        binding.setViewModel(booksDetailsViewModel);

        assert getArguments() != null;
        book = (Book) getArguments().getSerializable("BOOK");

        booksDetailsViewModel.book.setValue(book);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    private void buyBook(String id) {
        APIService apiService = APIClient.getClient().create(APIService.class);
        apiService.buyBook(PrefManager.retrieveAccessToken(Objects.requireNonNull(getActivity())), id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Order placed successfully!", Toast.LENGTH_SHORT).show();
                    navController.popBackStack();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
