package com.m7amdelbana.bookstore.view.bookDetails;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.m7amdelbana.bookstore.R;
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

public class BookDetailsFragment extends Fragment implements View.OnClickListener {

    private ImageView imageView;
    private TextView tvBookName;
    private TextView tvPrice;
    private TextView tvAuthor;
    private RatingBar ratingBar;
    private TextView tvBookInfo;
    private TextView tvAuthorInfo;
    private Button btnPlaceOrder;
    private NavController navController;
    private Book book;

    public BookDetailsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_details, container, false);

        assert getArguments() != null;
        book = (Book) getArguments().getSerializable("BOOK");

        initView(view);
        setupData();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    private void initView(View view) {
        imageView = view.findViewById(R.id.book_imageView);
        tvBookName = view.findViewById(R.id.book_name_textView);
        tvPrice = view.findViewById(R.id.book_price_textView);
        tvAuthor = view.findViewById(R.id.book_authorName_textView);
        ratingBar = view.findViewById(R.id.book_ratingBar);
        tvBookInfo = view.findViewById(R.id.book_info_textView);
        tvAuthorInfo = view.findViewById(R.id.book_author_info_textView);
        btnPlaceOrder = view.findViewById(R.id.book_place_order_button);
        btnPlaceOrder.setOnClickListener(this);

        ImageView imgBack = view.findViewById(R.id.back_imageView);
        imgBack.setOnClickListener(v -> navController.popBackStack());
    }

    private void setupData() {
        Picasso.get()
                .load(book.getImage())
                .placeholder(R.drawable.img_placeholder)
                .into(imageView);

        tvBookName.setText(book.getName());
        tvAuthor.setText(book.getAuthor());
        tvPrice.setText(String.valueOf(book.getPrice()));
        ratingBar.setRating(book.getRating());
        tvBookInfo.setText(book.getBookDescription());
        tvAuthorInfo.setText(book.getAboutAuthor());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.book_place_order_button:
                if (PrefManager.retrieveAccessToken(Objects.requireNonNull(getActivity())) != null) {
                    buyBook(book.getId());
                } else {
                    Toast.makeText(getActivity(), "You should login first.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
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
